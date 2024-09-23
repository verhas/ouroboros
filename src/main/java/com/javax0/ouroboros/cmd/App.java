package com.javax0.ouroboros.cmd;

import com.javax0.ouroboros.interpreter.SimpleExecutor;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

public class App {

    final static Set<String> parameters = Set.of(
            // snippet command_options
            "version",
            "output",
            "include",
            "help"
            // end snippet
    );

    public static void main(String[] args) {
        final var params = CmdParser.parse(args, parameters);
        if (params.get("help").isPresent()) {
            System.out.println("""
                    
                    Usage: ouroboros [options] [file]
                    
                    Options:
                      --version      Print version information and exit.
                      --output FILE  Write output to file.
                      --help         Display this help message and exit.
                    """);
            return;
        }
        if (params.get("version").isPresent()) {
            System.out.println("Ouroboros [ur] version 0.1");
            return;
        }

        final PrintStream out;
        if (params.get("output").isPresent()) {
            final var fn = params.get("output").get();
            try {
                out = new PrintStream(new FileOutputStream(fn));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            out = System.out;
        }
        if (params.get(0).isPresent()) {
            final var executor = new SimpleExecutor();
            executor.setOutput(out);
            if (params.get("include").isPresent()) {
                final var includes = params.get("include").get();
                final var includePath = includes.split(":");
                for (var include : includePath) {
                    executor.includePath(Paths.get(include));
                }
            }
            try {
                final var input = Files.readString(Paths.get(params.get(0).get()));
                executor.execute(input);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("No program to execute");
        }
    }
}
