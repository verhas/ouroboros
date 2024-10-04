echo off

cd /d "%~dp0"

FOR /F "delims=" %%i IN ('powershell -Command "$content = Get-Content -Raw ''pom.xml''; $content -match ''<version>(.*?)</version>'' | Out-Null; $version = $Matches[1]; $version -replace ''-SNAPSHOT'',''''"') DO SET "VERSION=%%i"

echo Version=%VERSION%

REM Loop over the installer types to create both exe and msi installers
for %%I in (exe msi) do (
    echo Creating installer type %%I
    jpackage --input target ^
        --vendor "Peter Verhas" ^
        --name ur ^
        --app-version %VERSION% ^
        --main-jar ouroboros-%VERSION%.jar ^
        --main-class com.javax0.ouroboros.cmd.App ^
        --type %%I ^
        --dest output ^
        --java-options -Xmx2048m ^
        --win-console ^
        --resource-dir src/packaging-resources
)
