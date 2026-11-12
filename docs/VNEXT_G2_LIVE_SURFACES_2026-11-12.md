# FOLILUNE vNext — G2-A connected surfaces checkpoint
**Date:** 2026-11-12
**Branch:** `experiment/vnext-g2-live-surfaces`
**Review:** [Draft PR #7](https://github.com/rayadinaveen98-ship-it/FOLILUNE/pull/7) into the existing experimental G1 branch.

## Implemented (not yet a release)
- Live **Home**: derives imported-book shelf and recent reading entry from the v12 in-memory collection; initial state has an honest add-book path, and fictional samples have an explicit sample label.
- Live **Library**: filters, sorts and searches actual local book metadata, with separate sample display, native import action and truthful format/storage messaging.
- Live **Search**: filters existing in-memory imported and example books; no claimed online catalog, fake content feed or remote request.
- **Saved Places**: lists persisted full-EPUB CFI bookmarks and text-preview chapter bookmarks per book, using an existing `openBook` path to resume at selected saved locations.
- **Settings**: genuine reader text size and page theme via the bounded bridge; existing G1 app-chrome Paper/Night toggle; no Google sync.
- **Navigation**: new side destinations and accessible 44px+ buttons; keeps existing Story/Comic/Study demo routes, which remain labeled samples where appropriate.
- First-run reading-size comfort now uses the bridge instead of attempting to reach private variables from another script.

## Safety boundary
The v12 EPUB.js, JSZip, EPUB parse/import code, IndexedDB schema, saved EPUB CFI implementation and full-renderer lifecycle are unchanged. A small first-party bridge registers approved UI views, returns metadata-only snapshots and permits existing reader navigation and bounded appearance changes. Two new local assets (`vnext-g2.js`, `vnext-g2.css`) are Android WebView-allowlisted. Remote HTTP(S) remains blocked.

**Existing G1 data isolation remains:** experimental package `com.folilune.vnextreader` does not access `com.folilune.tablet` data. Do not uninstall/clear app data or assume future APKs share a debug signing key. Keep source EPUB files.

## Verification
- JavaScript parse checks completed for G1 and G2 presentation scripts.
- Android integration and durability CI checks run on PR #7; see actual workflow conclusions before claiming success.
- Android instrumentation now checks live Home, Search, Saved Places and Settings alongside EPUB/CFI recovery. Previous intermittent CFI issues require repeated stress tests even if ordinary CI passes.

## Open G2 gates
1. Physical Android tablet: portrait/landscape, launch, import, search, saved place navigation and process restart.
2. Full visual parity against the v06 Modern Atelier reference: Collections, Notes and Help must be connected honestly to real capabilities, not copied as fictional demo state.
3. Accessibility: TalkBack, dynamic font scaling, reduced motion, touch sizes, contrast and screen rotations.
4. Content correctness: complex EPUB/Indic/RTL publishing and large library stress not proven.
5. Real data migration/versioned signing/backup and secure account sync deferred to separately approved work.
6. G1 and G2 draft branches are **not** production release builds, and the provisional Folded Moon identity still requires final owner sign-off.
