@echo off

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

:: Get the directory of the batch file
cd ../../

:: Set directory paths
set BIN_DIR=%BASE_DIR%bin
set LIB_DIR=%BASE_DIR%lib

:: Ensure bin directory exists
if not exist "%BIN_DIR%" (
    mkdir "%BIN_DIR%"
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

:: Run the main class from the bin directory, including external libraries
java -cp "%BIN_DIR%;%CLASSPATH%" main.java.com.pws.dryadengine.core.App

if %ERRORLEVEL% neq 0 (
    echo Execution failed. Aborting.
    PAUSE
    exit /b %ERRORLEVEL%
)