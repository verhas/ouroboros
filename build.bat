@echo off
jpackage --input target ^
    --name ur ^
    --app-version 1.0.0 ^
    --main-jar ouroboros-1.0.0-SNAPSHOT.jar ^
    --main-class com.javax0.ouroboros.cmd.App ^
    --type exe ^
    --dest output ^
    --java-options "-Xmx2048m" ^
    --resource-dir src/packaging-resources
