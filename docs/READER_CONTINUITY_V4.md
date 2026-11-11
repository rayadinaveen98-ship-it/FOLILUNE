# FOLILUNE — Phase 3: Reader Continuity v4

**Date:** 2026-11-11  
**Status:** Prototype verified in headless Chromium (not production engine approval)  
**Visual direction:** Modern Atelier — retained from v2/v3

## Deliverable

[Standalone v4 prototype](../prototypes/modern-atelier-reader-proof-v4.html) — a single offline HTML file, distributed in ChatGPT with a downloadable DRM-free original test EPUB. The test-book fixture used for browser checks was generated locally; it is not included in this GitHub commit.

## Specific improvements over v3

1. **Basic EPUB text semantics.** Imported EPUB chapters now retain a limited subset of headings, paragraphs, blockquotes, bullets, italic, bold, superscript and subscript. No remote styles or external resources are loaded. All permitted inline markup is reconstructed using strict whitelisting; scriptable/image content is discarded.
2. **More stable reading positions.** Store the closest visible text block plus a fractional offset within that block, alongside the old scroll fraction as a fallback. Restoring after a font-size or panel change uses the block anchor rather than absolute scroll percentage alone.
3. **Accurate reading progress.** Progress now derives from completed chapters plus location in the current chapter, with an updated visible percentage while scrolling; merely opening chapter 1 does not mark that chapter as finished.
4. **Reading checkpoints.** Save when leaving the story screen and on browser visibility/page hide, in addition to scroll events and chapter changes. The first return to a book can appear in Continue Reading even before substantial scrolling.
5. **Graceful storage degradation.** When the browser blocks IndexedDB, a file can still be read for the current session, with a visible 'session only' warning. The file will need importing again after reload in that environment.
6. **Click accessibility fix.** The fixed prototype badge no longer intercepts interactions near reader footer controls.

## Testing completed

- JavaScript syntax validated in Node.js.
- Headless Chromium v144 (screens and interactions via set_content) at **1440×900**, **960×760**, **390×844**.
- No viewport-wide horizontal overflow; zero page JavaScript errors.
- Parsed a newly constructed two-chapter compressed EPUB with XHTML headings, 27 blocks in chapter 1, italic/bold, blockquote and list content.
- Verified that embedded image markup / onerror attributes are absent from reconstructed reading content.
- Tested scroll-to-block anchoring after opening settings and increasing font size: the same paragraph block remained closest to the top in all three viewport sizes.
- Tested chapter change to chapter 2, returning to Library and reopening the book at chapter 2.
- Exercised the actual hidden HTML file-picker import path in the browser: parsed a valid EPUB and displayed the imported text with emphasis intact.
- The headless browser environment blocks navigation to `file://` and `localhost`. Therefore **IndexedDB persistence across a real browser restart is NOT tested**. The browser test correctly displayed 'session only' on an opaque-origin set_content page.

## Known limitations (do not advertise as complete)

- This is **still a proof-of-concept parser**, not a standards-compliant EPUB 3 engine or a security-audited importer.
- Imported EPUB images, CSS, fixed-layout pages, tables, SVG, links, annotations tied to EPUB CFI, ruby, footnotes, vertical text and media overlays are not supported.
- EPUB reading-position anchors are relative to imported block order; they are not canonical cross-edition or cross-device references.
- Browser storage can be unavailable for local files on some browsers or in previews; readers should keep their source files.
- Cross-browser persistence, quota eviction, corruption recovery and device restore need dedicated manual and automated QA.
- Comic reading and study screens remain original visual demonstrations, not imported publication engines.
- No accounts, cloud sync, real audio playback, paid APIs or publisher-distributed catalog.

## Immediate next decision gate

1. On the user's Windows laptop, import the test EPUB in Chrome/Edge, read and scroll, resize text, navigate back, reopen, close and reopen the HTML file. Confirm storage, position, and bookmark persistence.
2. Compare Readium Kotlin (Android) and Readium Web/EPUB.js approach (laptop/web) for a maintained, standards-compliant production reader. As of November 2026, Readium Kotlin lists EPUB 2/3, pagination/scrolling, search and highlighting as implemented; CBZ remains partial. See [Readium Kotlin](https://readium.org/kotlin-toolkit/latest/) and [Readium Web](https://readium.org/web/).
3. Only after the above should the engineering team commit to a production rendering stack or expand into PDF/CBZ import.

**Owner approval gate:** v4 is an experiment; the UI/engine are not finalized by this commit.
