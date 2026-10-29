package com.pedromolon.parsaagent.service;

import com.pedromolon.parsaagent.configuration.ObsidianProperties;
import com.pedromolon.parsaagent.domain.ObsidianFolder;
import com.pedromolon.parsaagent.dto.ObsidianNoteDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

@Slf4j
@Service
public class ObsidianService {

    private final ObsidianProperties obsidianProperties;

    public ObsidianService(ObsidianProperties obsidianProperties) {
        this.obsidianProperties = obsidianProperties;
    }

    // Listas arquivos (.md) detro de uma pasta específica
    public List<Path> listNotesFromFolder(ObsidianFolder folder) {
        Path targetPath = Path.of(obsidianProperties.getObsidianVaultPath(), folder.getFolderName());

        if (!Files.exists(targetPath)) {
            log.warn("A pasta {} não foi encontrada no Vault.", folder.getFolderName());
            return Collections.emptyList();
        }

        try (Stream<Path> stream = Files.walk(targetPath)) {
            return stream
                    .filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(".md"))
                    .toList();
        } catch (IOException e) {
            log.error("Erro ao listar notas da pasta {}", folder.getFolderName(), e);
            throw new RuntimeException("Falha ao ler pasta no Obsidian Vault", e);
        }
    }

    // Ler uma nota a partir de um caminho e realizar o parsing do FrontMatter e do corpo
    public Optional<ObsidianNoteDto> parseNote(Path filePath) {
        if (!Files.exists(filePath) || !Files.isRegularFile(filePath)) {
            return Optional.empty();
        }

        try {
            String rawContent = Files.readString(filePath);
            Path rootPath = Path.of(obsidianProperties.getObsidianVaultPath());
            Path relativePath = rootPath.relativize(filePath);
            String title = filePath.getFileName().toString().replace(".md", "");

            Map<String, Object> properties = new HashMap<>();
            String bodyContent = rawContent;

            if (rawContent.startsWith("---")) {
                int secondDelimiter = rawContent.indexOf("---", 3);
                if (secondDelimiter != -1) {
                    String yamlHeader = rawContent.substring(3, secondDelimiter).trim();
                    bodyContent = rawContent.substring(secondDelimiter + 3).trim();

                    for (String line : yamlHeader.split("\n")) {
                        if (line.contains(":")) {
                            String[] parts = line.split(":", 2);
                            properties.put(parts[0].trim(), parts[1].trim());
                        }
                    }
                }
            }

            return Optional.of(new ObsidianNoteDto(title, relativePath, properties, bodyContent));

        } catch (IOException e) {
            log.error("Erro ao realizar o parsing do arquivo Markdown: {}", filePath, e);
            return Optional.empty();
        }
    }

}
