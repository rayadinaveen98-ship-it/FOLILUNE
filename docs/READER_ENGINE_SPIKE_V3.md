# FOLILUNE — Phase 3 Reader Engine Proof v3
**Date:** 2026-11-11
**Status:** Experimental vertical slice; NOT a production reader
**Visual baseline:** Modern Atelier (approved direction)

## Artifact and scope
Produced a standalone, offline HTML test artifact in the ChatGPT conversation named `FOLILUNE_Modern_Atelier_Reader_Proof_v3.html`. A small original demo EPUB, `FOLILUNE_Original_Test_Book.epub`, was also created for testing. The HTML prototype source is now preserved at [prototypes/modern-atelier-reader-proof-v3.html](../prototypes/modern-atelier-reader-proof-v3.html). The original sample EPUB remains a conversation download for device tests, not a repository fixture yet. Neither file represents a deployed or native app.

### Preserved
- Home and Library, responsive tablet/laptop/phone visual shell.
- Story reading, comic sample, study workspace.
- Existing typography/theme controls, notes, bookmarks and settings.
- Script-disabled static fallback, to avoid a blank page in restricted previews.

### New experiment
- **Local file import:** .txt and .md (plain-text reading), and **DRM-free, reflowable EPUB** with *text-only extraction*. Files are selected from the user's device.
- **Local persistence:** imported extracted chapters are stored in IndexedDB, and per-book reading location/bookmarks in localStorage. Does not upload books, require a server, or connect an account.
- **Per-book continuity:** chapter, scroll fraction, current-book notes and bookmarks; locally imported books appear in Library and can be removed.
- **Defensive ZIP handling:** rejects encrypted ZIP entries, unsupported compression, too many entries, huge declared sizes, unsafe paths, malformed packages and missing text chapters. This is an experimental parser, not a security-audited EPUB engine.

### Known limitations / honest boundaries
- EPUB3 fidelity is **not** met: images, CSS, fixed layout, table formatting, ruby, footnotes, semantic links, scripts, vertical writing and all mixed-media features are dropped or flattened.
- .md is displayed as plain text. It is not a Markdown rendering engine.
- PDF, CBZ/CBR, audiobooks, DRM, cloud sync, cross-device syncing, accounts and AI are not implemented.
- ZIP decompression uses the browser's native `DecompressionStream('deflate-raw')`, which may be absent on some browsers.
- Local-file storage behavior differs across browsers; IndexedDB+localStorage need direct on-device persistence/restore verification.
- The proof's custom EPUB ZIP parser is **not selected as the production implementation**. Prefer evaluating a maintained Readium-based or other standards-compliant reader before shipping.
- Existing sample works are fictional UI exemplars; not all sample showcase books have readable contents.
- No automated test of browser restart persistence was possible in the current test harness; do not claim it is proven.
- The artifact is not a native desktop/tablet executable and has not had accessibility, security or performance certification.

## Evidence / checks actually performed
- JavaScript parses without syntax errors in Node.
- EPUB import parsing was exercised on an original two-chapter, standards-structured EPUB containing a container XML, OPF manifest and XHTML spine. Both chapters and title/author extracted.
- TXT paragraph import was exercised.
- Chromium `set_content` rendering tested at 1440×900 and 960×760 viewport widths, including navigation and imported-book display, and returned no page exceptions.
- On those viewports the same imported book opened in Story Reader. Chapter-next behavior was verified using a DOM click, resulting in chapter 2.
- The test environment blocks browsing to file:/// and localhost, so real file-origin IndexedDB storage/restart could not be checked. Test harness substituted an in-memory store for UI navigation tests only.
- Mobile breakpoint UI retained from v2; full mobile import interaction is not a completed QA gate.

## Exact manual testing checklist for product owner
1. Download `FOLILUNE_Modern_Atelier_Reader_Proof_v3.html` and `FOLILUNE_Original_Test_Book.epub` from the same ChatGPT reply.
2. Open the HTML locally in Chrome or Edge (not the ChatGPT preview).
3. Go to Library -> **Add a book**, select the sample EPUB.
4. Check chapter 1, use **Next chapter**, open Contents, adjust font and theme, bookmark chapter 2.
5. Scroll within a chapter; return to Library, reopen and verify reading place is retained.
6. Close and reopen the HTML in the same browser and check whether the imported book remains available.
7. Import a small .txt or .md file and test reflow.
8. Select a passage and save a note. Check the correct note is shown for the relevant book.
9. Return to Library and remove one imported book. Check it no longer appears.
10. Report any missing text, broken formatting, blank state, failed storage, or unexpected book disclosure.

## Next quality gate
- Confirm import and persistence on the user's Windows laptop and Android tablet browser.
- Replace the experimental EPUB parser with a maintained, standards-compliant reading engine, test CSS/fixed-layout/RTL/accessibility, and add representative legal EPUB fixtures.
- Expand only after faithful EPUB import/resume and annotation anchoring are reliable; CBZ/PDF are independent workstreams.
- No final UI lock or production-release commitment without owner's review.
