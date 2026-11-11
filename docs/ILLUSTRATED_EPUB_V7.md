# FOLILUNE — Illustrated EPUB Preview v7
**Date:** 2026-11-11
**Phase:** 3 — Reader engine experimentation
**Status:** Standalone HTML prototype available as a conversation download; NOT a production EPUB reader.
**Visual direction:** Modern Atelier approved aesthetic, unchanged.

## Implementation delivered
- Original local **PNG, JPEG and WebP** assets referenced from an EPUB's OPF manifest can now display inline with chapter text. Figure captions and image alt text are preserved in the limited preview.
- Images are accepted only from packaged book files: remote URLs, scripts, SVGs, unlisted resources and images beyond strict size/count limits are not loaded.
- Asset limits: at most 12 unique images; each asset up to 1.5 MB uncompressed; combined embedded image bytes capped at 7.5 MB. Additional rejected/unsupported image references are counted for the reader's optional Reading Accuracy panel.
- Backup/restore can preserve supported inline image blocks with strictly validated image data URIs and clean caption/alt strings. V6 backup version stays compatible.
- The reader rechecks its saved paragraph anchor after pending image decodes, helping reduce position drift caused by late image layout.
- No advertising, content marketplace, accounts, cloud requests or new feature sections were added.

## Evidence from actual tests
The HTML prototype was exercised in headless Chromium in three viewport dimensions: **1440 × 900**, **960 × 760**, and **390 × 844**.
An entirely original, two-chapter DRM-free EPUB test fixture included:
- A locally packaged PNG illustration in a nested Images directory referenced from chapter XHTML.
- Text with strong/emphasis markup, figure caption and alt text.
- A remote image URL designed to be ignored; a publisher CSS file.
- A second chapter to test navigation and bookmarks.

All three viewports passed:
- File import and actual PNG decode (950 px natural width) in the story reader.
- Captions/alt text present and image source restricted to a data URI.
- Rejection/omission of remote images and clear Reading Accuracy notices.
- Chapter change, bookmark, private JSON backup download and restoration to a separate page.
- Restored image decoding on reopening the previous chapter.
- Rejection of a crafted backup with a malicious external image URL.
- No page JavaScript errors and no viewport-wide horizontal overflow.

**Unverified:** real browser restart / `file://` IndexedDB durability. The sandbox blocks navigating to local file URLs, so these tests use an opaque-origin page with temporary browser storage. This remains a manual device validation gate.

## Limitations
- The v7 parser is still experimental and lacks canonical EPUB CFI, complete navigation, publisher CSS, math, media overlays, SVG, fixed layouts, ruby, full footnotes, DRM and accessibility conformance.
- JPEG/WebP signature checks are not exhaustive integrity checks. A complete ZIP/EPUB validation and image decoding security audit is required before distribution.
- Raster image bytes are embedded as data URIs, which makes large books and backups inefficient; a production reader should stream resources.
- Reading-position anchors track local chapter/block offsets, not canonical cross-edition positions.
- This is **not** Readium/EPUB.js/Foliate integration and does not replace the production engine selection task.

## Next work gate
1. Owner tests downloadable v7 HTML on Windows Chrome/Edge with original sample EPUB, closes and reopens the browser, verifies positions and imported file availability.
2. Conduct an isolated maintained-engine benchmark with identical licensed EPUBs and at least two OS targets; record fidelity, navigation, memory footprint, accessibility, security and license requirements.
3. Make a documented stack decision after owner reviews benchmark findings; no release or full engine claim until then.

## Supporting research
- [Readium Web](https://github.com/readium/web): publication streaming plus TypeScript navigator.
- [EPUB.js](https://www.npmjs.com/package/epubjs): stable 0.3.93 remains older.
- [Foliate-js](https://github.com/johnfactotum/foliate-js): multi-format browser reader modules, but the upstream library explicitly warns that its API is unstable.
