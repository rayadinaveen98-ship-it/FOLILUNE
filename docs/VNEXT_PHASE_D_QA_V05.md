# FOLILUNE vNext Phase D — QA Evidence
**Date:** 2026-11-11 · **Browser:** Playwright headless Chromium · **Not** an Android/WebView or full accessibility certification.

## Final Phase D test matrix
| Viewport | Passed |
| --- | ---: |
| Laptop 1440×900 | 54/54 |
| Tablet landscape 960×760 | 54/54 |
| Tablet portrait 768×1024 | 55/55 |
| Compact 390×844 | 55/55 |
| Ctrl/⌘+K global search | 1/1 |
| **Total** | **219/219** |

Covered Search queries/scopes, demo Collections creation and add/remove, Saved chapter bookmark recovery, Notes search/filter/create, Settings categories and live Paper/Night typography/spacing/density/contrast/motion, Help search, empty/loading/error states, More navigation, no JS exceptions, no remote requests, and document-level no horizontal overflow at each viewport.

**First-run test fix:** Original QA asserted that “Atlas” yielded only one result. Search correctly returned both a book and its linked note; the test assertion was corrected, not the app. The final Phase D suite passed 219/219.

## Existing core experience regression
Four widths × 14 checks = **56/56 pass** for original Home, Library, Story, Visual and Study navigation, table of contents, chapter transitions, visual panel turn, study notes, readable Story width and no uncaught page errors/overflow.

**Overall: 275/275 browser assertions passed.**

## Limitations
The browser sandbox disallows normal local file/navigation origins. Tests used `page.set_content`, which does not validate on-disk browser persistence/restart. Real touch, screen readers, all WCAG focus/contrast combinations, Android tablet hardware and EPUB/CBZ/PDF fidelity are not covered here. Visual prototype data remains separate from the v12 Android app.

The conversation review kit includes the HTML, source CSS/JS, Playwright scripts, detailed JSON and representative screenshots. See [visual design prototype](../prototypes/vnext-supporting-experience-phase-d-v05.html).
