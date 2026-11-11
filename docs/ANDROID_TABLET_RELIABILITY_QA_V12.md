# FOLILUNE — Android tablet reliability gate (v12)

**Date:** 11 November 2026  
**Test environment:** GitHub Actions Ubuntu 24.04, Google APIs Android API 34 x86_64 emulator, Android WebView.  
**Status:** **PASS** for imported-publication persistence through *app process termination*. Not physical tablet certification.

## Verified run

- [GitHub Actions run 42996230219](https://github.com/rayadinaveen98-ship-it/FOLILUNE/actions/runs/42996230219) — **completed successfully**.
- Branch: `experiment/android-tablet-apk-v12` (draft [PR #3](https://github.com/rayadinaveen98-ship-it/FOLILUNE/pull/3)).
- Android debug APK signing/build verification remains covered separately by the `FOLILUNE tablet debug APK` workflow.

## Instrumented tests (2/2 passed)

### Stage 1 — seedImportedEpub
- Launched the real FOLILUNE WebView at its fixed secure asset origin.
- Verified the actual bundled `window.ePub` and `window.JSZip` libraries were available, not mocks.
- Used JSZip in WebView to construct an original two-chapter EPUB fixture.
- Triggered the reader's genuine browser file-input import event with a WebView `File` object.
- Waited for the imported title and the EPUB.js rendition iframe.
- Confirmed EPUB bytes were recorded in Android WebView's actual IndexedDB `folilune_local_library_v3`, not just an in-memory JavaScript object.
- Set a localStorage durability probe and requested a bookmark.
- Instrumentation output: `OK (1 test)`.

### Stage 2 — verifyPersistedEpub
- Explicitly ran `adb shell am force-stop com.folilune.tablet` between instrumentation runs.
- Launched the app in a new instrumentation session/process.
- Verified localStorage durability probe still exists.
- Opened Library and found the previously imported original EPUB restored from IndexedDB.
- Reopened the EPUB in the actual bundled EPUB.js reader and verified a rendition iframe appeared.
- Instrumentation output: `OK (1 test)`.

The action recorded **'FOLILUNE Android WebView persistence tests passed'** after both suites. QA logs are published as a workflow artifact.

## What this does *not* establish

- No test of exact saved EPUB CFI, bookmark list, note positions, or reflow-anchor restoration across a process kill. The first-stage test clicked Bookmark but did not assert the stored CFI or restoration in Stage 2.
- No Android tablet hardware test, device reboot, app upgrade, uninstall/reinstall, low-storage eviction, or backup/export durability testing.
- No end-user native `ACTION_OPEN_DOCUMENT` UI-picker flow was exercised; the test triggered an equivalent WebView file-input change event with a generated book. Manual native picker QA is required.
- No full EPUB conformance, complex RTL/fixed layout, security assessment or accessibility certification.
- This is not a release-ready APK or approval to merge the draft PR.

## Next reliability gate (no new features)

1. Verify precise EPUB CFI and bookmarks across app process death/reflow and screen rotation.
2. Run a manual native file-picker test on the user's physical Android tablet, including closing and reopening the app.
3. Test large EPUB handling, storage quota errors, corrupted files, and import rollback.
4. Implement and verify an Android-native user-accessible backup/export path *before* depending on WebView as persistent personal library storage.
5. Keep Modern Atelier screens unchanged until these functional gates pass.

**Decision:** Android process-restart recovery is now proven on emulator; physical-device QA and CFI/bookmark accuracy remain open.
