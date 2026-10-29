package com.pedromolon.parsaagent.domain;

import lombok.Getter;

@Getter
public enum ObsidianFolder {

    INBOX("00. Inbox"),
    MAP_OF_CONTENTS("01. Map of Contents"),
    KNOWLEDGE("02. Knowledge"),
    SYSTEM("03. System");

    private final String folderName;

    ObsidianFolder(String folderName) {
        this.folderName = folderName;
    }
}
