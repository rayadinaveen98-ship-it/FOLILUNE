# FOLILUNE vNext — G1 Integrated Real-Reader Test (12 November 2026)

**Status:** Android test APK built, QA passing on latest runs, physical tablet and full visual migration still OPEN.
**Branch:** `experiment/vnext-real-reader-integration-g1` — [draft PR #6](https://github.com/rayadinaveen98-ship-it/FOLILUNE/pull/6).
**Validated run:** [43255745631](https://github.com/rayadinaveen98-ship-it/FOLILUNE/actions/runs/43255745631), success.
**Independent Android durability run:** [43255745365](https://github.com/rayadinaveen98-ship-it/FOLILUNE/actions/runs/43255745365), success.

## What is actually integrated
- The **real v12 EPUB.js 0.3.93 engine**, JSZip, native document picker, IndexedDB imported book bytes, EPUB page navigation, real CFI bookmarks and resume.
- The Modern Atelier Elevated paper/moss/brass tokens, Folded Moon **provisional** brand artwork, moonlit illustrated Home hero, optional/replayable welcome and reading comfort, Paper/Night app-chrome theme and refined tablet/compact visual controls.
- A verified dynamic Home startup that no longer exposes the old static no-script tour while IndexedDB loads; full imported library entries still reappear after asynchronous restoration.
- The reader/EPUB rendering code inside `index.html` is **unchanged byte-for-byte** after subtracting the added stylesheet/script references and title text. The appearance layer is contained in two new first-party assets; Android WebView permits only those explicitly allowlisted files and the existing index asset. No extra remote dependencies.

## Isolation / user data boundary
- Android app ID: `com.folilune.vnextreader`, label `FOLILUNE vNext Reader G1`, version 0.13.0-G1 test.
- Existing v12 app ID: `com.folilune.tablet`.
- The two packages have **separate storage**. G1 does NOT import/migrate v12's previously saved EPUB files/bookmarks; v12 remains untouched and should not be uninstalled. Keep source EPUBs. New CI debug keys may not match previous tests; uninstalling G1 could lose its own books.

## Evidence and limitations
- Latest integrated workflow **SUCCESS**, signed APK and fixed package ID verified; SHA256: `4b6184510fc181ab1c3a1dba617b5c1491073d21d3dc3037c8ca975a61c8a398`.
- AOSP Android 14 API 34 tests **2/2 passed**: genuine original EPUB import and persistent bytes in IndexedDB, chapter navigation, exact CFI checkpoint and bookmark; force-stop then reopen and exact CFI and bookmark comparison. The independent Android durability workflow also passed.
- A clean emulator screenshot was inspected, and now shows **real interactive Home**, decluttered compact header and original moonlit book art rather than a static preview fallback.
- **Reliability caution:** an earlier exploratory G1 run failed exact CFI recovery while another run with the same core passed. Later parallel checks passed, but intermittent recovery is not conclusively eliminated. More repeated, multi-device QA needed before release claim.
- No real hardware, app-update migration, backup/export, security review, full book-format conformance, screen-reader audit, Google sync or Play Store certification. No claim that G1 matches all v06 prototype screens.

## Next milestones
1. **G1 physical-tablet check:** opening, Skip, theme, Home, portrait/landscape, import via native Android file picker, EPUB pages, bookmarks and restart recovery.
2. **G2 visual parity:** replace remaining sample-only UI surfaces and connect approved vNext Home/Library/Search/Collections/Settings safely to actual reader state; no invented formats/tools.
3. **G3 functionality integration:** real source-linked notes/highlights, robust library organization, accessibility, data protection/backup and multi-device plans when authorized.
4. **Stabilization:** repeat exact CFI tests; stress reflow, low-memory process death and app updates; preserve existing v12 data through an explicit migration path before choosing any in-place release update.

**Do not merge PR #6 to the v12 reader without owner acceptance and migration/real-device reliability evidence.**
