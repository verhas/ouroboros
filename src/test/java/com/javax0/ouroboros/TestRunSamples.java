package com.javax0.ouroboros;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import static com.javax0.ouroboros.AssertUtils.output;

public class TestRunSamples {
    public static void deleteDirectory(Path path) throws IOException {
        Files.walkFileTree(path, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    public static final String SNIPPET = "snippet ";
    public static final String END_SNIPPET = "end snippet";

    @Test
    @DisplayName("Run the samples")
    void runSamples() throws Exception {
        var root = Paths.get(".").toAbsolutePath();
        while (root.getParent() != null && !Files.exists(root.resolve("README.adoc.jam"))) {
            root = root.getParent();
        }
        final var outputDir = root.resolve("target/generated-output");
        if (Files.exists(outputDir)) {
            deleteDirectory(outputDir);
        }
        Files.createDirectories(outputDir);

        Files.list(root.resolve("src/test/resources/samples")).forEach(path -> {
            try {
                var source = Files.readString(path);
                for (int index = source.indexOf(SNIPPET); index >= 0; index = source.indexOf(SNIPPET)) {
                    source = source.substring(index + SNIPPET.length());
                    final var eol = source.indexOf("\n");
                    final var fn = source.substring(0, eol).trim();
                    source = source.substring(eol + 1);
                    final var end = source.indexOf(END_SNIPPET);
                    final var s = end == -1 ? source : source.substring(0, end);
                    source = end == -1 ? "" : source.substring(end + END_SNIPPET.length());
                    String result = null;
                    try {
                        result = output(s);
                    } catch (Exception e) {
                        result = e.getMessage();
                    }
                    Files.writeString(outputDir.resolve(fn), result);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }


}
