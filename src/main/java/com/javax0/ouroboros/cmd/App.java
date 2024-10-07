package com.javax0.ouroboros.cmd;

 import com.javax0.ouroboros.cmd.CmdParser;
 import com.javax0.ouroboros.interpreter.SimpleExecutor;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
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
                       --include PATH the list of include paths separated by\s""" + pathSeparator() + """
                    
                      --help         Display this help message and exit.
                    """);
            return;



        }
        if (params.get("version").isPresent()) {
            String version = null;
            try (final var res = App.class.getClassLoader().getResourceAsStream("META-INF/version")) {
                if (res != null) {
                    version = new String(res.readAllBytes(), StandardCharsets.UTF_8);
                }
            } catch (IOException e) {
                version = null;
            }
            System.out.println("Ouroboros [ur] version " + version);
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
                final var includePath = includes.split(pathSeparator());
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

    private static String pathSeparator() {
        String osName = System.getProperty("os.name").toLowerCase();

        if (osName.contains("win")) {
            return ";";
        } else {
            return ":";
        }
    }
}
