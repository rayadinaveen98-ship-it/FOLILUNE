# FOLILUNE — Modern Atelier Integrated EPUB.js v12

**Date:** 2026-11-11
**State:** **PROTOTYPE SUCCESS — owner review required; main remains unchanged**
**Branch:** `experiment/modern-atelier-epub-v12`
**Review:** https://github.com/rayadinaveen98-ship-it/FOLILUNE/pull/2 (draft PR)

## What changed
- The *real*, locally bundled EPUB.js **0.3.93** reader is now integrated within the existing Modern Atelier **Library → Story Reader** experience.
- Imported user-supplied DRM-free EPUB files retain a local original file blob in browser IndexedDB, alongside the existing simplified chapter-based preview. Earlier imported books without a raw EPUB continue to use the preview until reimported.
- A visible, opt-in **Full EPUB / Text preview** switch maintains access to the older extraction path. Existing Comic and Study demonstration screens remain.
- Actual EPUB table-of-contents navigation and EPUB CFI bookmarks/reading-location checkpoints are supported in the integrated prototype.
- Font sizing, line-height and paper/sepia/night themes apply through EPUB.js without reconstructing the whole app view. Panel overlay does not force iframe resize when opening controls.
- Bundled EPUB.js and JSZip scripts make the prototype HTML independent of an external CDN; retain library license/attribution notices.
- No account, remote book upload, AI service, PDF importer, CBZ importer, TTS or backend was added.

## Test evidence — **48/48 pass**
The most recent HTTP-origin CI run using **real, unmocked Chromium + real EPUB.js 0.3.93** passed 48/48 assertions at:
- Laptop: **1440×900**
- Tablet: **960×760**
- Phone: **390×844**

Run reference: GitHub Actions **42975529043** (success), and earlier fully instrumented passing run **42974987695** with 48/48 checks.

Each viewport tested:
1. Home and Library.
2. Import a synthetically generated, original two-chapter EPUB through the native file input.
3. Real XHTML prose rendering inside EPUB.js iframe.
4. Actual packaged PNG image decode.
5. Two navigation entries in the actual EPUB TOC.
6. Navigating to chapter two.
7. Saving a valid EPUB CFI.
8. Changing text size while chapter two remains visible.
9. Switching to night theme.
10. Saving and finding CFI bookmarks.
11. Returning to simplified text preview.
12. Switching back into the real EPUB engine.
13. Retaining the imported EPUB and **restoring chapter two after full page reload**, including IndexedDB file persistence.
14. No viewport-wide horizontal overflow.
15. No uncaught JavaScript page errors.
16. No external HTTP requests.

Additionally, local Chromium smoke tests on desktop and tablet viewport widths passed against the original illustrated **FOLILUNE v12 sample EPUB**. These local tests cannot verify persisted browser reloads, but the HTTP-origin CI suite does.

## Defects discovered and corrected
- **Settings panel resize issue:** setting panels previously caused the EPUB iframe to be reconstructed/resized in some cases. A non-destructive overlay preserves the reading surface.
- **Position overwritten when switching reading modes:** the text-preview progress checkpoint discarded the EPUB CFI. Preview progress now preserves an existing CFI if the chapter is unchanged; changing preview chapter clears it, avoiding an incorrect cross-chapter restoration.
- **Initial relocation race:** early EPUB 'relocated' events no longer overwrite a saved position while a restoration attempt is running.

## Known limitations and caution
- This is a **prototype**, not a production EPUB reader. EPUB.js supports substantially richer layout than our earlier parser, but this single matrix **does not** certify EPUB3 conformance, fixed layout, RTL/Indic script, tables, complicated fonts, large publications, or accessibility.
- Test sizes emulate tablet/phone viewports using desktop Chromium; actual Android tablet, Windows desktop app, mobile Safari/iPadOS and screen-reader testing remain pending.
- Saved CFI is tied to a specific EPUB edition/contents; position migration across materially different book versions is not implemented.
- **Backup JSON does not include original EPUB bytes.** Users must reimport the source EPUB to regain full mode after restoring a backup in a different browser/device.
- The original EPUB is stored locally by the browser; eviction and storage quota/long-term durability need testing on actual devices. Use the provided manual backup functionality and retain the source files.
- EPUB imports are untrusted content; parser/iframe isolation, full CSP hardening, security fuzzing and dependency audit remain open. Avoid importing sensitive/confidential third-party EPUBs until reviewed.
- This is neither a copyright-cleared book catalog nor an authorization to bypass DRM.
- App appearance and feature scope beyond this proof are not finalized.

## User acceptance test
1. Download the v12 standalone HTML and original illustrated EPUB from ChatGPT.
2. Open the HTML in **Chrome or Edge**, preferably in a normal browser rather than an embedded preview.
3. Library → **Add a book** → choose the sample EPUB.
4. Open its full EPUB reader, navigate to chapter two from Contents, change theme/font and save a bookmark.
5. Switch to **Text preview**, then back to **Full EPUB**; check that the chapter is retained.
6. Return to Library, close/reopen the HTML in the **same browser**, reopen the book and verify chapter-two recovery.
7. Check Library → Backup & restore, remembering this JSON does not contain the original raw EPUB file.
8. Send any screen/interaction issues for refinement before v12 merges into `main`.

## Approval gate
**Do not merge the draft PR to main until product owner reviews the actual v12 prototype and its Modern Atelier visual/interaction quality.** Next production tasks include deeper EPUB compatibility, security, keyboard/screen-reader behavior and real-device persistence.

## Copyright and dependencies
- EPUB.js 0.3.93 — BSD-2-Clause: https://github.com/futurepress/epub.js
- JSZip 3.10.1 — MIT (dual-licensed): https://github.com/Stuk/jszip
Both are bundled into the standalone HTML with attribution and license details in the downloadable kit.
