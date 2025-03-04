@echo off

set MAIN=test.core.Bench
:: Enable delayed variable expansion
setlocal enabledelayedexpansion

set "batchFile=%~f0"

:: Check if ANSI color support is enabled
for /f "tokens=3" %%a in ('reg query HKCU\Console /v VirtualTerminalLevel 2^>nul') do set "ansiEnabled=%%a"

if not defined ansiEnabled (
    echo Enabling ANSI color support...
    reg add HKCU\Console /v VirtualTerminalLevel /t REG_DWORD /d 1 /f >nul
    echo ANSI color support has been enabled. Please restart the terminal.
    pause
    exit /b
) else (
    echo ANSI color support is enabled.
)

:: Set the directory of the project
cd ../../

:: Set directory paths
set SRC_DIR=%BASE_DIR%src
set BIN_DIR=%BASE_DIR%bin
set LIB_DIR=%BASE_DIR%lib

:: Make a bin folder if it doesnt, and if it does remove all its contents in preparation
:: for the java binaries.
if not exist "%BIN_DIR%" (
  mkdir "%BIN_DIR%"
) else (
  @RD /s /q "%BIN_DIR%"
)

:: Create an empty classpath variable for jar files
set CLASSPATH=

:: Loop through all JAR files in the LIB_DIR and add them to CLASSPATH
for %%f in ("%LIB_DIR%\*.jar") do (
    set CLASSPATH=!CLASSPATH!;%%f
)

:: Remove leading semicolon if exists
if "!CLASSPATH:~0,1!"==";" (
    set CLASSPATH=!CLASSPATH:~1!
)

:: Compile all Java files from the src directory to the bin directory, including external libraries
echo Compiling Java files...

:: Create a variable to store all Java files
set JAVA_FILES=

:: Find all Java files in the SRC_DIR and add them to the JAVA_FILES variable
for /r "%SRC_DIR%" %%f in (*.java) do (
    set JAVA_FILES=!JAVA_FILES! "%%f"
)

:: Check if JAVA_FILES is not empty
if not "!JAVA_FILES!"=="" (
    javac -d "%BIN_DIR%" -cp "%CLASSPATH%" !JAVA_FILES!
) else (
    echo No Java files found to compile.
    PAUSE
    exit /b 1
)

:: Check if compilation succeeded
if %ERRORLEVEL% neq 0 (
    echo Compilation failed. Aborting.
    PAUSE
    exit /b %ERRORLEVEL%
)

echo Done compiling!

echo ---------------------------------------Starting Engine-------------------------------------------------------
cls

:: Run the main class from the bin directory, including external libraries
java -cp "%BIN_DIR%;%CLASSPATH%" %MAIN%

if %ERRORLEVEL% neq 0 (
    echo Execution failed. Aborting.
    PAUSE
    exit /b %ERRORLEVEL%
)
