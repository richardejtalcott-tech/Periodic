# Periodic 2.4 complete replacement repository

This archive replaces the former Periodic 2.2 repository.

## Upload correctly

Extract the ZIP first. Upload the **contents inside the extracted folder** to the root
of the GitHub repository. At the repository root you should immediately see:

- `app/`
- `gradle/`
- `gradlew`
- `gradlew.bat`
- `build.gradle`
- `settings.gradle`
- `build-codespace.sh`

Do not upload the ZIP as an unopened single file if your goal is to build the project.

## One-command Codespaces build

Open the repository in Codespaces, open **Terminal**, and run:

```bash
chmod +x build-codespace.sh && ./build-codespace.sh
```

The first build downloads Gradle 8.9 and Android dependencies, so it can take several
minutes. After success, download:

`Periodic-v2.4-debug.apk`

## Verification status

This package includes all required repository files and has passed ZIP integrity,
Android XML parsing, source brace checks, GLB header checks, and shell-script syntax
checks. The Android application still must complete a real Gradle build in Codespaces
or Android Studio because this ChatGPT environment does not include the Android SDK.
