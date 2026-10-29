package com.pedromolon.parsaagent.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Files;
import java.nio.file.Path;

@Configuration
@Getter
@Setter
public class ObsidianProperties {

    @Value("${obsidian.vault-path}")
    private String obsidianVaultPath;

    public void validadeVaultPath() {
        if (obsidianVaultPath == null || obsidianVaultPath.isBlank()) {
            throw new IllegalArgumentException("Obsidian vault path cannot be null or blank");
        }

        Path path = Path.of(obsidianVaultPath);

        if (!Files.exists(path) || !Files.isDirectory(path)) {
            throw new IllegalArgumentException("Obsidian vault path must be a valid directory");
        }
    }

}
