#!/usr/bin/env bash
set -euo pipefail
mkdir -p android-preview/qa-logs
finish() {
  status=$?
  adb logcat -d -v brief > android-preview/qa-logs/logcat.txt 2>/dev/null || true
  echo "Android vNext preview QA exit status: $status"
}
trap finish EXIT

APK=android-preview/app/build/outputs/apk/debug/app-debug.apk
TEST=android-preview/app/build/outputs/apk/androidTest/debug/app-debug-androidTest.apk
adb install -r "$APK"
adb install -r "$TEST"
adb shell pm clear com.folilune.vnextpreview
echo '=== Stage 1: actual Android WebView renders offline first-launch ==='
adb shell am instrument -w -e class 'com.folilune.vnextpreview.PreviewSmokeTest#seedOfflineWelcomeAndStorage' \
  com.folilune.vnextpreview.test/androidx.test.runner.AndroidJUnitRunner \
  | tee android-preview/qa-logs/first-run.txt
grep -q 'OK (1 test)' android-preview/qa-logs/first-run.txt

echo '=== Screenshot: clean first launch in separate app process ==='
adb shell am force-stop com.folilune.vnextpreview
adb shell am start -W -n com.folilune.vnextpreview/.MainActivity
sleep 7
adb shell screencap -p /sdcard/Download/folilune-vnext-preview.png
adb pull /sdcard/Download/folilune-vnext-preview.png android-preview/qa-logs/android-preview-launch.png

echo '=== Stage 2: separate process retains preview storage ==='
adb shell am force-stop com.folilune.vnextpreview
adb shell am instrument -w -e class 'com.folilune.vnextpreview.PreviewSmokeTest#verifyStorageAndMainAppStillLoads' \
  com.folilune.vnextpreview.test/androidx.test.runner.AndroidJUnitRunner \
  | tee android-preview/qa-logs/reopen.txt
grep -q 'OK (1 test)' android-preview/qa-logs/reopen.txt
echo 'FOLILUNE vNext Preview Android emulator passed (2/2)'
