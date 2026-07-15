# Gradle bootstrap

This repository includes `gradlew` and `gradlew.bat` launchers that download and cache
Gradle 8.9 automatically on first use. This avoids requiring Gradle to be preinstalled
in a fresh Codespace.

Build with:

```bash
chmod +x gradlew
./gradlew clean assembleDebug --stacktrace
```
