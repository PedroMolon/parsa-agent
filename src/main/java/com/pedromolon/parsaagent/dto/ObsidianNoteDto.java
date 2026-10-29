package com.pedromolon.parsaagent.dto;

import java.nio.file.Path;
import java.util.Map;

public record ObsidianNoteDto(
        String title,
        Path relativePath,
        Map<String, Object> properties,
        String bodyContent
) {
}
