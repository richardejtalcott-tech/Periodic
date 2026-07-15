# Periodic 2.4 replacement repository

This folder is intended to replace the previous Periodic 2.2 repository.

## Upload
Upload the CONTENTS of this folder to the root of a new GitHub repository.
The repository root should directly contain:

- app/
- build.gradle
- settings.gradle
- gradle.properties
- .github/

Do not upload the outer ZIP as a single unopened file.

## Build in GitHub Codespaces
Open the repository in a Codespace. The included GitHub Actions workflow is also configured to build the project.

This package preserves the v2.2-style periodic table and includes the later v2.3 source passes, including the Filament-based 3D environment assets and Explore/Journey source present in the latest project.

## Important
This archive has passed static source/package checks in ChatGPT's environment, but no Android SDK was available there to compile or install the application. A successful Gradle/Android build is still required before treating it as a verified APK release.
