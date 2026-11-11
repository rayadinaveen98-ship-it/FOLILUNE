# FOLILUNE vNext — Phase C / Main Reading Surfaces (v04)

**Status:** Interactive design review ready, not approved for integration or release.  
**Date:** 2026-11-11  
**Working aesthetic:** Modern Atelier Elevated; Folded Moon logo remains provisional.  
**Branch:** `design/vnext-modern-atelier-brand` (draft PR #4).

## Purpose and boundaries
Transform Phase B's visual language into a coherent, tablet- and laptop-first Home → Library → Reader experience. Keep the approved v12 Android EPUB.js app, user imports, settings and saved locations unchanged. Phase C is a **separate review file**, not yet combined physically with Phase B onboarding.

[Open the interactive HTML concept](../prototypes/vnext-main-surfaces-phase-c-v04.html).

## Screen contract
| Surface | Visual experience | Working interactive prototype | Not claimed |
| --- | --- | --- | --- |
| Home | Editorial headline, illuminated original moon/book hero, primary Continue card, reading desk, mood shelf, three reading spaces | Navigate to sample Story/Visual/Study, Library, sample book cards | Demo 42%/30% progress is not actual persisted reading data |
| Library | Original SVG covers, responsive shelves, title/genre/author hierarchy | Search, category filters, sort, grid/list, sample open, empty state | Real import still lives in the separate v12 Android app |
| Story Reader | Large-format paper page, subtle rails, justified text on large screens, left-aligned at compact widths | 3 original sample chapters, TOC, chapter changes, sample chapter bookmarks, type/line-size, paper/night | No real EPUB.js, EPUB CFI bookmarks, reader-owned notes, licensed book catalog |
| Visual Reader | Original 3-part vector illustrated narrative and page-first space | 3 manual art-page changes | No CBZ, manga direction, webtoon, panel detection |
| Study Space | Document-first writing with expandable annotations rail | Hide/show illustrative notes | No PDF engine, true annotations, sync/export |
| Appearance shell | Quiet design token applications | Paper/night, short guided tour, reader comfort | Complete settings belongs to Phase D |

## Engineering craft and design rules
- Identity and palette inherit proposed A2/A3 color and typography tokens. Original inline SVG vectors are scalable and reproducible; no CDN/image/font/network dependencies.
- 1440×900 laptop and 960×760 tablet landscape: side navigation rail, content-measure-aware reading pages, generous white space and columns.
- 768×1024 tablet portrait: horizontal icon navigation; 3-up illustrated Library; content-first story.
- 390×844 compact fallback: viewport-fixed bottom navigation; modal readers stack above nav; full-width reading paper; left-aligned prose to prevent excessive justification gaps; content reserves space below fixed navigation.
- Reader chapter bookmarks and demo book state are in isolated JavaScript demo state / optional `folilune_vnext_phasec_v04` browser-local key; not shared with v12, no imported EPUBs.
- Essential controls are visible, not hidden behind decorative animation. No autoplay, network requests or account prompts.

## Verification and bugs found
See [Phase C QA](VNEXT_PHASE_C_QA_V04.md). **169/169 browser assertions pass** at laptop, tablet landscape, tablet portrait, compact and keyboard-search smoke.
- Corrected a real 390px grid issue: hiding paper side rails had placed the reading paper into a 0-width track (46px paper, 0px prose). Fixed to one-column with explicit min-readable-width regression checks (390px page, 344px prose).
- Raised reader panel/backdrop above compact fixed navigation; corrected test that originally clicked a hidden part of the backdrop.
- Changed compact prose from fully justified to ragged-right/left-aligned for comfortable spacing.

## Known limitations
No physical tablet UI check, Android APK, EPUB3 conformance, device durability, WCAG screen-reader audit, safe import handling, content rights evaluation or end-to-end Phase B→C integration was performed in this design-only workstream. Browser tests loaded the standalone HTML via `page.set_content` and do not certify durable local storage across restart. Exactly what was tested is in the QA document.

## Gate
Owner reviews Home, Library, Story, Visual and Study compositions and requests corrections if needed. After approval, proceed to Phase D secondary screens, settings and edge states, followed by Phase E visual QA. Keep the design branch draft until explicit approval and verified integration against the functioning reader.
