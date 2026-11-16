#!/usr/bin/env bash
set -euo pipefail

# Capture device crashes and instrumentation failures before emulator shutdown.
collect_android_diagnostics() {
  status=$?
  echo "FOLILUNE emulator QA exit status: $status"
  adb logcat -d -v threadtime > /tmp/folilune-logcat.txt 2>/dev/null || true
  if [ "$status" -ne 0 ]; then
    echo "=== Android crash clues ==="
    grep -E -i -A 8 -B 5 'FATAL EXCEPTION|FATAL SIGNAL|OutOfMemoryError|lowmemorykiller|am_crash|SIG(SEGV|ABRT|KILL)|Instrumentation.*crash' /tmp/folilune-logcat.txt | tail -160 || true
  fi
}
trap collect_android_diagnostics EXIT

gradle -p android-app --no-daemon assembleDebug assembleDebugAndroidTest

adb install -r android-app/app/build/outputs/apk/debug/app-debug.apk
adb install -r android-app/app/build/outputs/apk/androidTest/debug/app-debug-androidTest.apk
adb shell pm clear com.folilune.vnextreader

echo "=== Stage 1: import and save a genuine EPUB ==="
adb shell am instrument -w -e class 'com.folilune.tablet.ReaderDurabilityTest#seedImportedEpub' \
  com.folilune.vnextreader.test/androidx.test.runner.AndroidJUnitRunner | tee /tmp/folilune-seed.log
grep -q 'OK (1 test)' /tmp/folilune-seed.log

echo "=== Stage 2: terminate app and verify saved EPUB ==="
adb shell am force-stop com.folilune.vnextreader
adb logcat -c || true
adb shell am instrument -w -e class 'com.folilune.tablet.ReaderDurabilityTest#verifyPersistedEpub' \
  com.folilune.vnextreader.test/androidx.test.runner.AndroidJUnitRunner | tee /tmp/folilune-reopen.log
grep -q 'OK (1 test)' /tmp/folilune-reopen.log

echo "=== Stage 3: simulate a stale localStorage cache with a newer IndexedDB checkpoint ==="
adb shell am force-stop com.folilune.vnextreader
adb shell am instrument -w -e class 'com.folilune.tablet.ReaderDurabilityTest#poisonLocalCacheForRecovery' \
  com.folilune.vnextreader.test/androidx.test.runner.AndroidJUnitRunner | tee /tmp/folilune-stale-cache.log
grep -q 'OK (1 test)' /tmp/folilune-stale-cache.log

echo "=== Stage 4: verify journal recovery and Saved Places exact CFI navigation ==="
adb shell am force-stop com.folilune.vnextreader
adb shell am instrument -w -e class 'com.folilune.tablet.ReaderDurabilityTest#verifyRecoveredJournalAndSavedPlace' \
  com.folilune.vnextreader.test/androidx.test.runner.AndroidJUnitRunner | tee /tmp/folilune-journal-recovery.log
grep -q 'OK (1 test)' /tmp/folilune-journal-recovery.log

echo "=== Stage 5: remove the active EPUB and verify committed bookmark cleanup ==="
adb shell am force-stop com.folilune.vnextreader
adb shell am instrument -w -e class 'com.folilune.tablet.ReaderDurabilityTest#removeImportedBookClearsEpubState' \
  com.folilune.vnextreader.test/androidx.test.runner.AndroidJUnitRunner | tee /tmp/folilune-book-removal.log
grep -q 'OK (1 test)' /tmp/folilune-book-removal.log

echo "=== Stage 6: verify deletion survives a second process restart ==="
adb shell am force-stop com.folilune.vnextreader
adb shell am instrument -w -e class 'com.folilune.tablet.ReaderDurabilityTest#verifyDeletedBookDoesNotReappear' \
  com.folilune.vnextreader.test/androidx.test.runner.AndroidJUnitRunner | tee /tmp/folilune-deletion-restart.log
grep -q 'OK (1 test)' /tmp/folilune-deletion-restart.log

echo "=== Stage 7: verify G2 touch areas and keyboard-visible search focus ==="
adb shell am force-stop com.folilune.vnextreader
adb shell am instrument -w -e class 'com.folilune.tablet.ReaderDurabilityTest#verifyG2ShellTouchAndKeyboardFocus' \
  com.folilune.vnextreader.test/androidx.test.runner.AndroidJUnitRunner | tee /tmp/folilune-g2-ui-a11y.log
grep -q 'OK (1 test)' /tmp/folilune-g2-ui-a11y.log

echo "=== Stage 8: screenshot a clean integrated Home after reading verification ==="
mkdir -p android-app/qa-logs
adb shell am force-stop com.folilune.vnextreader
adb shell am start -W -n com.folilune.vnextreader/com.folilune.tablet.MainActivity
sleep 7
adb shell screencap -p /sdcard/Download/folilune-g1-home.png
adb pull /sdcard/Download/folilune-g1-home.png android-app/qa-logs/folilune-g1-home.png
cp /tmp/folilune-seed.log /tmp/folilune-reopen.log /tmp/folilune-stale-cache.log /tmp/folilune-journal-recovery.log /tmp/folilune-book-removal.log /tmp/folilune-deletion-restart.log /tmp/folilune-g2-ui-a11y.log android-app/qa-logs/ 2>/dev/null || true

echo "FOLILUNE vNext G1 real-EPUB WebView persistence tests passed"
