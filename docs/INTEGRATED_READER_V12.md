# FOLILUNE — Integrated EPUB.js Reader v12

**Date:** 2026-11-11  
**Status:** Integrated prototype; browser-tested, NOT production-approved  
**Design:** Modern Atelier preserved

## Accomplishment

A standalone v12 HTML build now renders **real EPUB.js 0.3.93** books from the existing FOLILUNE Library/Story Reader interface. EPUB.js and JSZip 3.10.1 are bundled locally, so no CDN is required. EPUBs may use either the full engine or the older limited text-preview fallback.

- Open imported original EPUBs from the Library.
- Real EPUB.js rendition and image rendering; chapter TOC navigation.
- Reader controls for font and theme with retained location.
- EPUB CFI checkpoints and bookmarks in local browser state.
- Switch into limited preview and back without replacing original author content.
- Preserve existing Comic and Study demonstration screens.
- Avoid remote requests and leave original reader v7 untouched.

## Tests actually run

The recorded full-engine integration suite returned **48/48 PASS** in Chromium at **1440×900, 960×760 and 390×844**, with 16 checks per viewport: visible home/library, chapter content, original embedded image, two-chapter TOC, chapter-two navigation, valid CFI checkpoint, font/theming behavior, CFI bookmarking, fallback to text preview, return to full rendition, saved source/chapter after reload in a controlled test environment, no viewport overflow, zero uncaught page errors and no remote requests. The provided v12 ZIP includes `report.json`.

A separate manual smoke suite at 960×760 also passed its checks for the UI shell, navigation, bookmarks, and preservation of the older screens.

**Known regression gate:** an older script using an earlier v4 EPUB fixture timed out while searching for the v12 TOC selector. Do not claim arbitrary older fixtures or all source-book migration paths passed; resolve this before promotion.

## Remaining risks / release blockers

- Browser data persistence may differ across devices, local-file origins, quotas and private mode. Real Windows/Android browser restart and source recovery still require device testing.
- The v12 fixture passed; complex EPUB fixed-layout, scripting, tables, RTL/Indic scripts, large-file stress and accessibility are not certified.
- EPUB.js 0.3.93 is an older published library; review advisories, dependency licenses, CSP/sandbox, imported HTML and remote-resource blocking.
- JSON backup may contain sensitive notes/content; treat as private. Book redistribution rights remain separate from engine licensing.
- Existing text-preview anchors do not automatically map perfectly to full EPUB CFI across editions.
- This proof is not an APK, hosted site, publisher catalog or production app.

## Delivery and branch

The current downloadable artifact is `FOLILUNE_Modern_Atelier_Integrated_Reader_v12.html`, with `FOLILUNE_Integrated_EPUB_Prototype_v12_Kit.zip` including the original illustrated fixture, test report, and third-party license notices. The HTML is a conversation artifact until separately committed; do not assume GitHub source parity from this report alone.

## Next milestone

Run real hardware storage/restart validation and a structured EPUB fixture suite against v12, then address failure/migration paths and accessibility before discussing release scope. No new cosmetic features in this phase.
