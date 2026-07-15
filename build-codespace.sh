#!/usr/bin/env bash
set -Eeuo pipefail
cd "$(dirname "$0")"

PROJECT_DIR="$PWD"
SDK_ROOT="${ANDROID_SDK_ROOT:-$HOME/android-sdk}"
CMDLINE_VERSION="14742923"
CMDLINE_ZIP="commandlinetools-linux-${CMDLINE_VERSION}_latest.zip"
CMDLINE_URL="https://dl.google.com/android/repository/${CMDLINE_ZIP}"
GRADLE_TASKS=(clean assembleDebug)

log() { printf '\n[%s] %s\n' "$(date +'%H:%M:%S')" "$*"; }

run_gradle() {
  export JAVA_HOME="$JAVA17"
  export PATH="$JAVA_HOME/bin:$PATH"
  ./gradlew --no-daemon --console=plain "$@"
}
fail() { echo "ERROR: $*" >&2; exit 1; }
trap 'echo; echo "BUILD FAILED near line $LINENO. Review the error immediately above." >&2' ERR

log "Preparing Java 17"
JAVA17=""
for candidate in \
  "${JAVA_HOME:-}" \
  /usr/lib/jvm/java-17-openjdk-amd64 \
  /usr/lib/jvm/temurin-17-jdk-amd64 \
  /opt/java/17; do
  if [ -n "$candidate" ] && [ -x "$candidate/bin/java" ]; then
    major="$("$candidate/bin/java" -version 2>&1 | sed -n '1s/.*version "\([0-9]*\).*/\1/p')"
    if [ "$major" = "17" ]; then JAVA17="$candidate"; break; fi
  fi
done

if [ -z "$JAVA17" ]; then
  if command -v sudo >/dev/null 2>&1 && command -v apt-get >/dev/null 2>&1; then
    log "Installing OpenJDK 17"
    sudo apt-get update -qq
    sudo DEBIAN_FRONTEND=noninteractive apt-get install -y -qq openjdk-17-jdk curl unzip
    JAVA17=/usr/lib/jvm/java-17-openjdk-amd64
  else
    fail "Java 17 is required."
  fi
fi

export JAVA_HOME="$JAVA17"
export PATH="$JAVA_HOME/bin:$PATH"
java -version

log "Preparing Android command-line tools"
mkdir -p "$SDK_ROOT/cmdline-tools"
SDKMANAGER="$SDK_ROOT/cmdline-tools/latest/bin/sdkmanager"

if [ ! -x "$SDKMANAGER" ]; then
  command -v curl >/dev/null 2>&1 || fail "curl is required."
  command -v unzip >/dev/null 2>&1 || fail "unzip is required."
  CACHE_DIR="$HOME/.cache/periodic-android-sdk"
  mkdir -p "$CACHE_DIR"
  ZIP_PATH="$CACHE_DIR/$CMDLINE_ZIP"
  if [ ! -s "$ZIP_PATH" ]; then
    log "Downloading official Android command-line tools"
    curl --fail --location --retry 5 --retry-delay 3 --connect-timeout 30 \
      --progress-bar "$CMDLINE_URL" -o "$ZIP_PATH"
  fi
  rm -rf "$SDK_ROOT/cmdline-tools/latest" "$CACHE_DIR/unpacked"
  mkdir -p "$CACHE_DIR/unpacked"
  unzip -q "$ZIP_PATH" -d "$CACHE_DIR/unpacked"
  mkdir -p "$SDK_ROOT/cmdline-tools/latest"
  cp -a "$CACHE_DIR/unpacked/cmdline-tools/." "$SDK_ROOT/cmdline-tools/latest/"
fi

export ANDROID_HOME="$SDK_ROOT"
export ANDROID_SDK_ROOT="$SDK_ROOT"
export PATH="$SDK_ROOT/cmdline-tools/latest/bin:$SDK_ROOT/platform-tools:$PATH"

log "Accepting SDK licenses non-interactively"
set +o pipefail
yes | "$SDKMANAGER" --sdk_root="$SDK_ROOT" --licenses >/dev/null
LICENSE_STATUS=$?
set -o pipefail
if [ "$LICENSE_STATUS" -ne 0 ] && [ "$LICENSE_STATUS" -ne 141 ]; then
  fail "Android license acceptance failed with status $LICENSE_STATUS."
fi

log "Installing Android SDK platform 35 and build tools"
"$SDKMANAGER" --sdk_root="$SDK_ROOT" \
  "platform-tools" \
  "platforms;android-35" \
  "build-tools;35.0.0"

printf 'sdk.dir=%s\n' "$SDK_ROOT" > local.properties

log "Validating repository source"
python3 tools/validate_source.py

log "Building Periodic 2.4 debug APK"
chmod +x gradlew
run_gradle "${GRADLE_TASKS[@]}" --stacktrace

APK="$PROJECT_DIR/app/build/outputs/apk/debug/app-debug.apk"
[ -f "$APK" ] || fail "Gradle did not create $APK"
cp -f "$APK" "$PROJECT_DIR/Periodic-v2.4-debug.apk"

log "Running unit tests"
run_gradle testDebugUnitTest --stacktrace

log "Running Android lint"
run_gradle lintDebug --stacktrace

log "BUILD SUCCESSFUL"
echo "APK: $PROJECT_DIR/Periodic-v2.4-debug.apk"
ls -lh "$PROJECT_DIR/Periodic-v2.4-debug.apk"
