#!/bin/bash

# Enable error handling
set -e

# Get the absolute path of the script
SCRIPT_DIR=$(dirname "$(readlink -f "$0")")

# Check if ANSI color support is enabled
grep -q "^TERM=xterm-256color" ~/.bashrc || echo "export TERM=xterm-256color" >> ~/.bashrc

echo "ANSI color support is enabled."

# Set the project directory
cd "${SCRIPT_DIR}/../../"

# Set directory paths
SRC_DIR="$(pwd)/src"
BIN_DIR="$(pwd)/bin"
LIB_DIR="$(pwd)/lib"

# Ensure bin directory exists
if [ ! -d "$BIN_DIR" ]; then
    mkdir -p "$BIN_DIR"
fi

echo "Deleting old compiles in the bin directory..."
rm -rf "$BIN_DIR"
mkdir -p "$BIN_DIR"

# Create an empty classpath variable for jar files
CLASSPATH=""

# Loop through all JAR files in the LIB_DIR and add them to CLASSPATH
for f in "$LIB_DIR"/*.jar; do
    [ -e "$f" ] && CLASSPATH="$CLASSPATH:$f"
done

# Remove leading colon if it exists
CLASSPATH=${CLASSPATH#:}

echo "Compiling Java files..."

# Create a variable to store all Java files
JAVA_FILES=()

# Find all Java files in the SRC_DIR and add them to the JAVA_FILES array
while IFS= read -r -d '' file; do
    JAVA_FILES+=("$file")
done < <(find "$SRC_DIR" -name "*.java" -print0)

# Check if JAVA_FILES is not empty
if [ ${#JAVA_FILES[@]} -ne 0 ]; then
    javac -d "$BIN_DIR" -cp "$CLASSPATH" "${JAVA_FILES[@]}"
else
    echo "No Java files found to compile."
    exit 1
fi

echo "Done compiling!"

echo "---------------------------------------Starting Engine-------------------------------------------------------"
clear

export SDL_VIDEODRIVER=x11
export SDL_RENDER_DRIVER=opengl

# Run the main class from the bin directory, including external libraries
java -cp "$BIN_DIR:$CLASSPATH" main.java.com.pws.dryadengine.core.App

if [ $? -ne 0 ]; then
    echo "Execution failed. Aborting."
    exit 1
fi
