# FOLILUNE Android tablet prototype (v12)
A minimal **WebView test wrapper** for the existing approved Modern Atelier v12 standalone HTML.

## Purpose
Install the real EPUB.js HTML prototype on an Android tablet and verify:
- library/home/navigation layout in portrait and landscape
- native Android file-picker for DRM-free .epub, .txt, .md and backup .json
- real EPUB text/illustration rendering
- chapter navigation, reading themes, bookmarks
- local progress after leaving/relaunching the app

## Scope and important limitations
- This is a **debug APK** for personal testing, not a production release or Play Store package.
- The UI and reader are still HTML/JS in Android WebView, **not a native EPUB engine**.
- Uses a bundled offline HTML asset; the WebView serves it at a secure local HTTPS-like origin via the built-in request interceptor; attempts to load other remote HTTP(S) resources are blocked.
- The Android system file picker grants temporary per-file access; no broad storage permission.
- IndexedDB and localStorage are enabled. Durability after actual tablet restarts has **not yet been confirmed**.
- The HTML prototype's `blob:` JSON backup-download action may not function in WebView. Keep source EPUBs; do **not** depend on the APK as sole book storage.
- No AI, cloud, analytics, accounts or background services. No private EPUBs until import security has undergone review.
- Script execution within EPUB.js' iframes stays disabled; sandbox and security review remains pending.
- Debug builds are signed with an automatically generated debug key **which differs across CI runs**. Uninstall prior debug APK before installing a newer CI-built APK if Android reports a signature mismatch. Uninstalling may delete locally stored books and positions—export any data you can first.

## How to build
Prerequisites: Android SDK platform 35, Gradle 8.10.2, Java 17 or 21 (AGP 8.7.3).
Run from `android-app/`:
```bash
gradle assembleDebug
```
The APK is at `app/build/outputs/apk/debug/app-debug.apk`. You may also use GitHub Actions' `FOLILUNE tablet debug APK` workflow.

## QA checklist
1. Install on an Android tablet, open FOLILUNE and inspect Home/Library.
2. Rotate to landscape. No blank screen; book covers and navigation remain usable.
3. Library → Add a book → use Android Documents picker to select an original/legal EPUB.
4. Open the imported book, turn chapters, adjust text size, confirm illustration renders.
5. Save bookmark; navigate out and back; then close/reopen the app to check location recovery.
6. Report the device model, Android version, any import failures, and screenshots.
7. Avoid claiming backup/export is working until separately tested on Android.
