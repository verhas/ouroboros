echo off

cd /d "%~dp0"

REM Read the version from the VERSION file
set /p VERSION=<VERSION
set /p VERSIONS=<VERSIONS

REM Loop over the installer types to create both exe and msi installers
for %%I in (exe msi) do (
    echo Creating installer type %%I
    jpackage --input target ^
        --vendor "Peter Verhas" ^
        --name ur ^
        --app-version %VERSION% ^
        --main-jar ouroboros-%VERSIONS%.jar ^
        --main-class com.javax0.ouroboros.cmd.App ^
        --type %%I ^
        --dest output ^
        --java-options -Xmx2048m ^
        --win-console ^
        --resource-dir src/packaging-resources
)
