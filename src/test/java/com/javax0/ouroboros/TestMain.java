package com.javax0.ouroboros;

import com.javax0.ouroboros.cmd.App;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;

public class TestMain {


    @DisplayName("main include")
    @Test
    void testMainInlcude() throws IOException {
        final var save = System.out;
        try (final var baos = new ByteArrayOutputStream();
             final var out = new PrintStream(baos)) {
            System.setOut(out);
            final var root = DebugUtils.projectRoot();
            final var target = root.resolve("target");
            Files.createDirectories(target);
            Files.writeString(target.resolve("include.ur"), """
                    puts "This is an included file!"
                    """);
            final var main = target.resolve("main.ur");
            Files.writeString(main, """
                    puts "this is the main file\\n" include "include.ur"
                    """);
            final var args = new String[]{"--include=" + target.toAbsolutePath(), main.toAbsolutePath().toString()};
            App.main(args);
            Assertions.assertEquals("this is the main file\nThis is an included file!", baos.toString().replaceAll("\r", ""));
        } finally {
            System.setOut(save);
        }
    }

    @DisplayName("main print version")
    @Test
    void testMainVersion() throws IOException {
        final var save = System.out;
        try (final var baos = new ByteArrayOutputStream();
             final var out = new PrintStream(baos)) {
            System.setOut(out);
            App.main(new String[]{"--version"});
            Assertions.assertEquals("Ouroboros [ur] version", baos.toString().replaceAll("\r", "").substring(0, 22));
        } finally {
            System.setOut(save);
        }
    }

    @DisplayName("main print help")
    @Test
    void testMainHelp() throws IOException {
        final var save = System.out;
        try (final var baos = new ByteArrayOutputStream();
             final var out = new PrintStream(baos)) {
            System.setOut(out);
            App.main(new String[]{"--help"});
            Assertions.assertEquals("Usage ouroboros [options] [file]Options" +
                            "  --version      Print version information and exit." +
                            "  --output FILE  Write output to file." +
                            "  --include PATH the list of include paths separated by" +
                            "   --help         Display this help message and exit."
                    , baos.toString().replaceAll("[\r\n:]", ""));
        } finally {
            System.setOut(save);
        }
    }

}
