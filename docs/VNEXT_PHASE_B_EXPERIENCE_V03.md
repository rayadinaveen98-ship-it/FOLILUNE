# FOLILUNE vNext — Phase B First-Launch Experience / v03

**Status:** Interactive visual/UX review ready; NOT a production reader, Android APK or approved final brand.
**Date:** 2026-11-11
**Aesthetic:** Modern Atelier Elevated, Phase A2/A3 proposed paper/night, typography, SVG Folded Moon study.

## Review prototype
- [Open offline Phase B interactive design](../prototypes/vnext-first-launch-phase-b-v03.html).
- All artwork original inline SVG and CSS: atmospheric moon, landscape, opening book; no external fonts, network, images or paid services.
- Front-end local prototype uses its own `folilune_vnext_phaseb_v03` storage key; does not touch v12 EPUB.js, reader app storage or the FOLILUNE-STUDIO backend.

## Screen-by-screen experience
| ID | Screen | Primary outcome | Important behavior |
| --- | --- | --- | --- |
| B0 | Animated opening | Brand and calm tone in ~1.65 s | Immediate Skip; reduced-motion automatically bypasses delay; returning user not blocked |
| B1 | Editorial arrival | Understand purpose, start or skip | Original vector illustration, clear CTA, no account required |
| B2 | Reading interests | Optional stories/visual/study preference | Multi-select, no forced choice |
| B3 | Comfort | Paper/Night and reader text-size example | Live preview; reversible; does not override publisher EPUB CSS |
| B4 | First shelf | Sample original book or honest import note | Preview does NOT import or parse EPUB, existing v12 does |
| B5 | Guided Home | First meaningful reading action and discovery | Three-step skippable guide, Back/Next, no feature dump |
| B6 | Sample reader | See original reading experience | Sample text, text sizes, theme, Back |
| B7 | Returning home | Quick continue without animation | Return visit goes directly Home; Replay resets design preview |

## Design rationale
Warm paper, moss, brass and editorial serif remain coherent. Tablet landscape uses compact nav rail and 2-column shelves, tablet portrait vertically stacks first-run introduction and illustration with a horizontal nav, compact mode avoids overflow; desktop retains full sidebar and three shelves. Draw original book art as scalable vector rather than static screenshot to preserve cross-device fidelity.

## Tested
Scripted Playwright Chromium harness completed **116/116 checks** across 1440×900, 960×760, 768×1024, 390×844, 29 per viewport. Separate reduced-motion run passed splash dismissal and arrival readiness. 0 uncaught page errors and no horizontal overflow at tested stages. See [QA details](VNEXT_PHASE_B_QA_V03.md).

**Material limitation:** restricted browser blocks navigation to `file:` and `localhost` URLs. This suite used `page.set_content()` with an explicit **in-memory stand-in for localStorage**. It verifies screen and preview-state logic, **NOT real disk persistence**, Android WebView, physical tablet performance, screen-reader conformance or EPUB imports. Open the HTML locally in a real browser for visual review; integrate only after owner approval and separate device testing.

## Next approvals and scope
Owner should review welcome pacing, original artwork, intent/comfort screens, guide Home and reader preview on tablet. Design changes remain isolated on the draft visual PR. Once approved, proceed to **Phase C — Home/Library/Reader premium visual overhaul**, preserving the working engine and data. Do not silently start Google sync or unrelated functionality.
