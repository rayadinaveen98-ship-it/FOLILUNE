# FOLILUNE — Engine Validation Baseline v8
**Date:** 2026-11-11
**Phase:** 3 — standards-compliant reader-engine selection
**Status:** Baseline test suite completed. **No new engine integrated.**
**Stable UI reference:** Modern Atelier v7.

## Why this checkpoint exists
Our v7 standalone reader extracts and sanitizes portions of DRM-free EPUBs and can render small local raster illustrations, but it is not an EPUB standards-compliant engine. Switching blindly to a new renderer risks regressing layout, local privacy, chapter navigation, and existing bookmarks. We created a repeatable original-content fixture and browser test matrix before that switch.

## Deliverable
**FOLILUNE_Engine_Validation_Kit_v8.zip** is provided in the same conversation as this report. It includes:
- The exact v7 HTML tested; current design remains unchanged
- Original EPUB fixture generator and source assets
- 4 generated original EPUBs: prose, illustrated, complex-language/structure, invalid package
- Browser automation with 3 viewport widths
- JSON results and reproduction instructions

The ZIP is a conversation artifact, **not a deployed site or native application**.

## Reproducible baseline (Chromium)
| Fixture | Scenario | 1440×900 | 960×760 | 390×844 |
|---|---|---|---|---|
| Prose | Two chapters, headings, emphasis, quote | PASS | PASS | PASS |
| Illustrated | Local PNG caption and rejected remote image | PASS | PASS | PASS |
| Complex-language | Import + fidelity warnings for CSS, links, notes, tables and RTL | PASS* | PASS* | PASS* |
| Invalid package | Reject missing OPF safely | PASS | PASS | PASS |

**12/12 test runs passed, no uncaught page errors, no viewport-wide horizontal overflow observed.**
*Complex-language PASS means the text-only preview imports and **warns correctly about missing features**, not that RTL, footnotes, hyperlinks or tables render faithfully. It does not establish EPUB or accessibility conformance.*

## Maintained-engine candidates
| Candidate | Officially documented strengths | Next experiment | Blocking consideration |
|---|---|---|---|
| EPUB.js 0.3.93 | Browser-based EPUB rendering, pagination, section navigation, iframe isolation | Integrate in a separately sandboxed browser spike using actual library distribution and JSZip | Older stable release; test security, offline import, RTL and CFI migration |
| Foliate-js | Modular browser reader for EPUB + additional formats, used by Foliate | Prototype its EPUB import and CFI on legally acquired test materials | Library API can change; secure context and module packaging considerations |
| Readium Web | TypeScript navigators + publication streaming Go layer | Prototype full HTTP(S) resource pipeline and accessibility | Not an offline single-file drop-in |
| Readium Kotlin | Native Android EPUB reading toolkit | Android tablet test with same fixtures | Separate Android implementation/Android project needed |

Sources: [EPUB.js](https://github.com/futurepress/epub.js), [Foliate-js](https://github.com/johnfactotum/foliate-js), [Readium Web](https://github.com/readium/web), [Readium Kotlin](https://readium.org/kotlin-toolkit/latest/).

## Why candidate runtime comparisons are still blocked
In this execution environment, dependency downloads from package/CDN origins were unavailable; no prebuilt engine was cached. **We did not run EPUB.js, Foliate-js, or Readium** and will not label any such option as integrated, tested, or approved. Avoid putting unverified third-party scripts on the user's main branch as the production reader.

## Evidence and limits
- Tested current v7 in browser through a `set_content` controlled environment and file-picker simulation.
- Validated import, chapter navigation, basic formatting, inline PNG and caps, fidelity warnings, and invalid-package rejection.
- Used entirely original EPUB text and illustrations; no copyrighted commercial publications redistributed.
- **No real persistence across browser restarts:** both local `file://` and loopback `http://127.0.0.1` were blocked by this environment's browser navigation policy.
- Not tested: malformed EPUB corpus at scale, decompression bombs beyond implemented limits, CFI anchoring, screen readers, actual iPad/Windows/Android hardware, cloud backup, or production-grade engine behavior.

## Decision and next gate
**Decision D-026: KEEP v7 unchanged as the baseline.** Next: install or vendor a maintained engine after dependency/license review; run it against these same fixtures and additional large/RTL publications; measure rendering, offline storage, CFI continuity, performance and screen-reader behavior; obtain owner approval before replacing the core engine.

**No new production feature or visual redesign approved by this checkpoint.**
