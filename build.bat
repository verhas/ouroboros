@echo off
REM Exit on error
set -e

REM Create packaging resources (if not already created)
call setup-packaging-resources.bat

REM Run jpackage to create the installer
jpackage --input target ^
    --name ur ^
    --app-version 1.0.0 ^
    --main-jar ouroboros-1.0.0-SNAPSHOT.jar ^
    --main-class com.javax0.ouroboros.cmd.App ^
    --type exe ^
    --dest output ^
    --java-options "-Xmx2048m" ^
    --resource-dir src/packaging-resources
