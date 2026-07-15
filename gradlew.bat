@echo off
setlocal enabledelayedexpansion
set GRADLE_VERSION=8.9
set BASE_DIR=%~dp0
if defined GRADLE_USER_HOME (
  set CACHE_DIR=%GRADLE_USER_HOME%\periodic-bootstrap
) else (
  set CACHE_DIR=%USERPROFILE%\.gradle\periodic-bootstrap
)
set DIST_DIR=%CACHE_DIR%\gradle-%GRADLE_VERSION%
set ZIP_FILE=%CACHE_DIR%\gradle-%GRADLE_VERSION%-bin.zip
set DIST_URL=https://services.gradle.org/distributions/gradle-%GRADLE_VERSION%-bin.zip

if not exist "%DIST_DIR%\bin\gradle.bat" (
  if not exist "%CACHE_DIR%" mkdir "%CACHE_DIR%"
  echo Gradle %GRADLE_VERSION% is not cached. Downloading it now...
  powershell -NoProfile -ExecutionPolicy Bypass -Command ^
    "$ErrorActionPreference='Stop'; Invoke-WebRequest -UseBasicParsing '%DIST_URL%' -OutFile '%ZIP_FILE%'; Expand-Archive -Force '%ZIP_FILE%' '%CACHE_DIR%'"
  if errorlevel 1 exit /b 1
)

call "%DIST_DIR%\bin\gradle.bat" -p "%BASE_DIR%" %*
exit /b %ERRORLEVEL%
