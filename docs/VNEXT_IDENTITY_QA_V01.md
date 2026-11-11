# FOLILUNE vNext — Identity Review QA v0.1
**Date:** 2026-11-11
**Status:** Browser concept tested; NOT a functioning app and NOT an approved final logo
**Branch:** design/vnext-modern-atelier-brand

## Artifacts
- [Live-in-browser standalone identity review](../prototypes/vnext-identity-review-v01.html)
- [Brand identity, layout, motion and accessibility specification](VNEXT_BRAND_IDENTITY_V01.md)
- Screenshots for 1440×900, 960×760, 768×1024 and 390×844 delivered in the current conversation (not committed to GitHub).

## Test conditions
Chromium headless using original inline HTML/SVG/CSS/JS, loaded from a controlled HTML document. All scripts and icons are embedded; no image CDN, external font or network package is required.

| Review scenario | Laptop 1440×900 | Tablet landscape 960×760 | Tablet portrait 768×1024 | Compact 390×844 |
| --- | --- | --- | --- | --- |
| 12 interaction and sanity checks | 12/12 | 12/12 | 12/12 | 12/12 |

**48/48 checks passed**, 0 uncaught page errors, 0 horizontal page overflow at all four widths.

Checks: identity visible; select a proposed SVG mark; selected mark changes; switch night mode; return to paper; navigate to Home sample; navigate from Home to Reader sample; resize prose sample; open welcome; skip welcome; no horizontal overflow; zero page exceptions.

## Preliminary light color contrast calculations
WCAG relative luminance formula (normal text target >=4.5:1):
- Ink #26342E on paper #FFFCF5: **12.71:1**
- Secondary #516359 on paper: **6.25:1**
- Moss #365747 on paper: **7.85:1**
- Brass revised to #94632C on paper: **5.02:1**
- Night ink #F3F1E9 on #101A18: **15.70:1**
- Night secondary #C3CEC6 on #101A18: **10.96:1**

This is only a token pairing calculation, not a full WCAG audit of every rendered element, hover/focus state, transparent overlay, illustration or the shipped app.

## Scope deliberately excluded
- No updates to the existing v12 Android reader, EPUB.js, bookmark storage or data model.
- No prototype claims about functioning content import, real comic reader, audiobook, cloud sync, AI or accessibility certification.
- Home and Reader preview copies are original illustrative content; buttons there navigate concept screens or adjust sample text only.
- Logo A/B/C are original reproducible SVG concepts, not selected/trademark-cleared logos.
- Browser simulation is not real Android hardware QA.
- No owner approval has been received yet for these details.

## Review gate and next steps
1. Owner reviews the three SVG logo directions and the Modern Atelier palette, via laptop/tablet or standalone HTML.
2. Refine one logo in small sizes, monochrome and Android adaptive icon masks; obtain explicit approval before locking identity.
3. Produce final icon/copy/art direction and a machine-readable token registry for Phase A2/A3, then implement first-use splash/onboarding prototypes for screen-level review.
4. Do not merge onto main or change the installed APK without approval.

This is **Phase A1 in review**, not the end of Phase A–E.
