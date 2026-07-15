#!/usr/bin/env bash
set -euo pipefail

GRADLE_VERSION="8.9"
BASE_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
CACHE_DIR="${GRADLE_USER_HOME:-$HOME/.gradle}/periodic-bootstrap"
DIST_DIR="$CACHE_DIR/gradle-$GRADLE_VERSION"
ZIP_FILE="$CACHE_DIR/gradle-$GRADLE_VERSION-bin.zip"
DIST_URL="https://services.gradle.org/distributions/gradle-$GRADLE_VERSION-bin.zip"

if [ ! -x "$DIST_DIR/bin/gradle" ]; then
  mkdir -p "$CACHE_DIR"
  echo "Gradle $GRADLE_VERSION is not cached. Downloading it now..."
  if command -v curl >/dev/null 2>&1; then
    curl -fL --retry 4 --retry-delay 3 "$DIST_URL" -o "$ZIP_FILE"
  elif command -v wget >/dev/null 2>&1; then
    wget -O "$ZIP_FILE" "$DIST_URL"
  else
    echo "ERROR: curl or wget is required to download Gradle." >&2
    exit 1
  fi

  rm -rf "$DIST_DIR"
  if command -v unzip >/dev/null 2>&1; then
    unzip -q "$ZIP_FILE" -d "$CACHE_DIR"
  else
    echo "ERROR: unzip is required to install Gradle." >&2
    exit 1
  fi
fi

exec "$DIST_DIR/bin/gradle" -p "$BASE_DIR" "$@"
