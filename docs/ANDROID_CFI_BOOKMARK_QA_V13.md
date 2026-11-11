# FOLILUNE — Exact EPUB CFI & Bookmark Durability Gate

**Date:** 11 November 2026  
**Status:** **PASS — Android API 34 AOSP emulator, 2/2 instrumented tests**  
**Implementation:** Existing v12 Modern Atelier and EPUB.js engine unchanged; test suite expanded.

## Evidence
- [Passing GitHub Actions run 42999357106](https://github.com/rayadinaveen98-ship-it/FOLILUNE/actions/runs/42999357106).
- Branch: `experiment/android-tablet-apk-v12`, draft [PR #3](https://github.com/rayadinaveen98-ship-it/FOLILUNE/pull/3).
- Tested on Android 14 (API 34) AOSP x86_64 emulator via Android WebView.
- Instrumentation runner `ReaderDurabilityTest` — stages `seedImportedEpub` and `verifyPersistedEpub`, each reports `OK (1 test)`.
- Both EPUB.js and JSZip are bundled in the existing v12 APK; no CDN renderer or mock used.

## What was verified
1. Import a generated, original two-chapter EPUB containing **120 distinct paragraphs in chapter two** through the real WebView file input.
2. Confirm imported book is persisted in WebView IndexedDB, not just in a JavaScript object.
3. Use the real EPUB.js TOC to navigate into chapter two, then turn forward to a **distinct CFI within that chapter**.
4. Confirm the live EPUB.js rendition's actual CFI is the same CFI written by FOLILUNE to its book-specific reading state.
5. Add a bookmark through FOLILUNE UI, confirm the exact CFI is stored in the book's bookmark list.
6. Force-stop the Android app process via ADB. Reopen in a **new instrumentation process** with the same WebView storage origin.
7. Find and reopen imported book, then compare the **actual EPUB.js rendition currentLocation().start.cfi** to the CFI stored before process death; require exact equality and chapter index 1.
8. Confirm the exact CFI bookmark survived process termination.
9. Increase font size and assert the reader remains in chapter two, with saved bookmark retained.
10. Confirm tests pass without altering the approved app screens or altering the production reader.

## Earlier failed run: emulator system dependency, not proven app defect
- [Diagnostics run 42998589025](https://github.com/rayadinaveen98-ship-it/FOLILUNE/actions/runs/42998589025) passed the import stage, then instrumentation aborted during reopen.
- Android logcat explicitly stated ActivityManager killed `com.folilune.tablet` because it depended on the Google Play Services FontsProvider while `com.google.android.gms.persistent` was dying after a Google module update/restart.
- There was no FOLILUNE Java stack trace identifying an app crash. Switching from the `google_apis` emulator image to the AOSP `default` image eliminated that interruption in the passing run.
- This does not prove there are no device-specific crashes. Google-backed emulator/device regression remains useful later.

## Limitations
- Pass applies to API 34 AOSP emulator with one controlled original EPUB; no claim of universal EPUB CFI accuracy.
- Exact CFI equality was checked **before reflow** at the same viewport. Following font-size change, test confirmed **same chapter and retained bookmark**, not identical pixel or CFI location under re-pagination.
- No real tablet, device reboot, app update, uninstall/reinstall, or storage eviction was tested.
- The native Android Documents picker was not manually exercised (test injected a browser File object).
- Android backup/export, retention after app upgrade, attachment migration, PDF/CBZ reading, accessibility, fixed layout, Indic/RTL conformance and security review remain open.

## Next gate
Implement and test a user-accessible **Android-native export/restore** path with content-size checks and explicit confirmation before any destructive replacement. This will reduce risk of data loss from WebView data eviction or app reinstall. Keep existing UI visuals and reader flow unchanged.

**Decision:** The specific Android AOSP process-restart CFI/bookmark gate passes. Phase 3 is still open.
