# Compile compatibility review

Configuration reviewed:

- Android Gradle Plugin: 8.7.3
- Gradle bootstrap: 8.9
- Kotlin Android plugin: 2.0.21
- Java language/toolchain target: 17
- compileSdk / targetSdk: 35
- minSdk: 26
- AndroidX enabled
- Filament Android modules: 1.71.6, pinned to the same version
- Android SDK packages installed by Codespaces and GitHub Actions scripts:
  platform-tools, platform 35, build-tools 35.0.0

Observed earlier source blockers addressed in this package:

- Element.shells compatibility restored
- ScaleJourneyView helper no longer collides with View.draw(Canvas)
- activity transition calls are API-gated
- Android window inset calls are API-gated
- Java and Kotlin compile targets both use 17

Static checks are not a substitute for a real Gradle compile. The included workflow and
build-codespace.sh perform the decisive compile, unit-test, and lint checks.
