#!/usr/bin/env bash
set -euo pipefail

gradle -p android-app --no-daemon assembleDebug assembleDebugAndroidTest

adb install -r android-app/app/build/outputs/apk/debug/app-debug.apk
adb install -r android-app/app/build/outputs/apk/androidTest/debug/app-debug-androidTest.apk
adb shell pm clear com.folilune.tablet

echo "=== Stage 1: import and save a genuine EPUB ==="
adb shell am instrument -w -e class 'com.folilune.tablet.ReaderDurabilityTest#seedImportedEpub' \
  com.folilune.tablet.test/androidx.test.runner.AndroidJUnitRunner | tee /tmp/folilune-seed.log
grep -q 'OK (1 test)' /tmp/folilune-seed.log

echo "=== Stage 2: terminate app and verify saved EPUB ==="
adb shell am force-stop com.folilune.tablet
adb shell am instrument -w -e class 'com.folilune.tablet.ReaderDurabilityTest#verifyPersistedEpub' \
  com.folilune.tablet.test/androidx.test.runner.AndroidJUnitRunner | tee /tmp/folilune-reopen.log
grep -q 'OK (1 test)' /tmp/folilune-reopen.log

echo "FOLILUNE Android WebView persistence tests passed"
