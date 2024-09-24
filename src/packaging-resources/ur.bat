@echo off
set "INSTALL_DIR=%~dp0.."
"%INSTALL_DIR%\runtime\bin\java.exe" -jar "%INSTALL_DIR%\app\ouroboros-1.0.0-SNAPSHOT.jar" %*
