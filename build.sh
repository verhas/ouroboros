#!/bin/bash

VERSION=`cat VERSION`
export VERSION
VERSIONS=`cat VERSIONS`
export VERSIONS

# Function to create package based on the operating system
create_package() {
    local INSTALLER_TYPE=$1
    jpackage --input target \
        --name ur \
        --app-version $VERSION \
        --main-jar ouroboros-$VERSIONS.jar \
        --main-class com.javax0.ouroboros.cmd.App \
        --type $INSTALLER_TYPE \
        --dest output \
        --java-options -Xmx2048m \
        --resource-dir src/packaging-resources
}

# Detect the operating system and create appropriate package
case "$(uname -s)" in
    Linux*)
        create_package deb
        ;;
    Darwin*)
        create_package pkg
        ;;
    *)
        echo "Unsupported operating system"
        exit 1
        ;;
esac
