# FOLILUNE vNext Phase C v04 — Browser QA

**Date:** 11 November 2026  
**Result:** **169/169 PASS** in headless Chromium through Playwright.  
**Artifact:** [Offline interactive HTML](../prototypes/vnext-main-surfaces-phase-c-v04.html).

| Browser viewport | Checks | Outcome |
| --- | ---: | --- |
| Laptop 1440 × 900 | 42 | PASS |
| Tablet landscape 960 × 760 | 42 | PASS |
| Tablet portrait 768 × 1024 | 42 | PASS |
| Compact 390 × 844 | 42 | PASS |
| Keyboard Ctrl+K entry | 1 | PASS |

**Per-viewport checks:** logo and original artwork; Home hero and three spaces; Library grid/count/original covers; Visual filter; grid/list switches; title sort; search and no-result state; reset; Story reader, original prose, TOC and chapter navigation, bookmark and notes panel, typography and theme; **minimum reader-page and prose width**; Visual/Comic original inline SVG and page changes; Study panel show/hide; Settings and guided tour; no horizontal page overflow across main screens; zero uncaught JS exceptions; zero remote network requests.

**Bug fixes verified:** compact reader sidebar modal priority, 390px paper grid, compact ragged-right typography, test-selector correction.

**Visual evidence:** 20 full-page viewport captures for Home, Library, Story, Visual, Study; PNG overview and original inline vector artwork included in the downloadable conversation kit along with the test script and JSON results.

**Material limits:** QA ran via `page.set_content` in a restricted browser. It does **not** prove actual on-disk browser persistence, imported EPUB fidelity, exact CFI recovery, Android physical-device performance, complete focus management/screen-reader support, release readiness or owner approval. Existing v12 Android/EPUB CI evidence applies to **that separate prototype**, not to this design demo.

No functional reader was modified.
