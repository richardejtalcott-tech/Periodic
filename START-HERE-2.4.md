# Periodic 2.4 — clean GitHub build

Upload the extracted contents of this ZIP to the root of a new repository.

In a new Codespace, use the AI agent or terminal to run exactly:

```bash
chmod +x build-codespace.sh && ./build-codespace.sh
```

The script:
1. selects or installs Java 17;
2. downloads the official Android command-line tools directly from Google;
3. accepts licenses without stopping for manual `y` responses;
4. installs Android platform 35 and build-tools 35.0.0;
5. validates the source;
6. builds the debug APK;
7. runs unit tests and lint;
8. creates `Periodic-v2.4-debug.apk` in the repository root.

The first build downloads Android and Gradle dependencies and can take several minutes,
but it now prints timestamped progress instead of appearing silent.
