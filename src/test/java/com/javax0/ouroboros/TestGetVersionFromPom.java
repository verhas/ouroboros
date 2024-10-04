package com.javax0.ouroboros;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class TestGetVersionFromPom {

    @Test
    void testGetVersionFromPom() throws Exception {
        final var version = Files.readString(Paths.get("pom.xml")).lines()
                .filter(line -> line.contains("<version>"))
                .map(line -> line.replaceAll(".*<version>(.*)</version>.*", "$1"))
                .limit(1)
                .collect(Collectors.joining(""));
        Files.writeString(Paths.get("VERSIONS"), version);
        Files.writeString(Paths.get("VERSION"), version.replaceAll("-SNAPSHOT", ""));
    }
}
