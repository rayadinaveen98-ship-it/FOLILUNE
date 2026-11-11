# FOLILUNE — Real EPUB.js Engine Gate v11
**Date:** 2026-11-11 | **Status:** real engine integration proven in isolated experiment; app integration still open.

## Grounded result
An isolated draft PR, [#1 — real EPUB.js engine experiment](https://github.com/rayadinaveen98-ship-it/FOLILUNE/pull/1), introduced a read-only GitHub Actions workflow and a genuine Chromium integration suite. The standard, free public-repo Ubuntu runner installed **epubjs@0.3.93**, **jszip@3.10.1** and **playwright-core@1.56.1**, then executed the upstream EPUB.js browser bundle—not a mock.

**CI run [42968054882](https://github.com/rayadinaveen98-ship-it/FOLILUNE/actions/runs/42968054882)**: **9/9 PASS**:
1. Actual `ePub` and `JSZip` loaded from npm distributions.
2. Original EPUB metadata read correctly.
3. Two genuine TOC entries discovered.
4. Chapter-one XHTML rendered inside the EPUB.js rendition.
5. Embedded PNG was decoded (`img.complete && naturalWidth > 0`).
6. A valid EPUB CFI was generated.
7. Rendering navigated to chapter two.
8. Displaying the prior CFI restored chapter one.
9. Zero uncaught browser exceptions in the test.

Follow-up run **[42968314093](https://github.com/rayadinaveen98-ship-it/FOLILUNE/actions/runs/42968314093)** also passed and archived the exact tested minified EPUB.js/JSZip browser bundles, third-party license files, screenshot and JSON assertions.

## Offline demonstrator
A separate, standalone **FOLILUNE_Modern_Atelier_Offline_Real_Engine_v11.html** was generated for this conversation:
- Modern Atelier-themed EPUB.js test interface, **not** a replacement for the approved v7 Library/Story/Comic/Study prototype
- EPUB.js and JSZip embedded locally (no CDN required); open directly in Chrome/Edge
- Original built-in sample and a second test EPUB supplied in a ZIP with BSD-2-Clause / JSZip license notices
- Runs a self-test or opens locally selected, non-DRM EPUB files
- Contents navigation, pagination/scroll, typography modes, and an experimental CFI checkpoint
- Tested in local Chromium via `set_content` at **1440×900 / 960×760 / 390×844**. Actual engine, metadata and TOC chapter-2 flow worked; no external script fetches or JS page errors. The in-page test correctly marked image-in-current-view, CFI durability and browser-restart persistence as MANUAL under opaque-origin restrictions.

The user download and test bundle remain conversation attachments; **this offline HTML has not been committed to main**, and is not a production release.

## Explicit non-claims
- **NOT** full EPUB 3 conformance or advanced publisher CSS/fixed layout/RTL/accessibility coverage.
- **NOT** a security audit of untrusted EPUB inputs. Do not import confidential books or rely on this proof for permanent storage.
- **NOT** confirmed browser-restart persistence on Windows/Android hardware; real CFI resume after closing and reopening remains a device gate.
- **NOT** FOLILUNE v7 reader replacement, nor evidence that comic/PDF import works.
- **NOT** product architecture selection; EPUB.js compatibility, maintenance age and licensing still require review.

## Next engineering milestone
1. Test the offline HTML with user's sample EPUB on **Windows Chrome/Edge and Android tablet**. Verify import, exact saved CFI restoration, reading appearance and offline behavior after browser restart.
2. Run fixture coverage for large illustrated books, publisher CSS, footnotes/links, CJK/RTL/Indic scripts, fixed layout, accessibility and malicious publication isolation.
3. Implement an **engine adapter** for the full Modern Atelier shell on a separate branch, with migration/fallback from v7 extracted-text locations; only merge after functional comparison.
4. Retain v7 as the approved UX reference until these gates pass.

**Decision:** technical feasibility achieved; production adoption **pending**.
