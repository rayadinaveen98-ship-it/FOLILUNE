# FOLILUNE vNext — Brand and Design System v02
**Stage:** A2/A3 review ready · 11 November 2026 · **not production approved**

## Decision boundary
Modern Atelier remains the selected aesthetic; logo A (Folded Moon) is a **working default**, not a finalized mark. B (Leafbound F) and C (Story Aperture) remain selectable. No changes to v12 EPUB.js, imports, local storage, bookmarks, APK, or tablet test releases.

## Actual implementation
- [Interactive standalone design lab](../prototypes/vnext-experience-lab-v02.html): three original SVG logo directions, responsive wordmark, adaptive icon masks, small-size studies, light/night, skippable splash, colors, typography, controls, forms, empty/loading/error states, icon grammar, Home/Reader/Settings visual shells.
- [Proposed machine-readable tokens](../design/tokens/vnext-components-v02.json): ink, moss, brass, paper/night, responsive breakpoints, scales and motion.
- [Vector master mark](../design/identity-v02/folded-moon.svg) and alternate [B](../design/identity-v02/leafbound-f.svg) / [C](../design/identity-v02/story-aperture.svg).
- Adaptation source: [foreground](../design/identity-v02/android-foreground.svg), [background](../design/identity-v02/android-background.svg), [monochrome](../design/identity-v02/android-monochrome.svg). Downloadable ZIP in conversation includes PNG render variants (24–512 px) and original sources.

## System in brief
Brand: **A world between pages** (working tagline). Respect reader content above chrome. Display via Georgia (offline-safe serif), UI via system sans-serif. 4 px spacing unit; warm paper / deep ink / moss / readable brass accents; button 12 px radius, card 20 px, hero 28 px. Stroked navigation icons: 24 px viewbox, 1.8 px stroke, rounded joints. Touch target proposal ≥48 dp Android, ≥44 CSS px web. Intro is skippable, reduced-motion aware; returning launches must not be delayed for an animation. Modern Atelier must adapt to tablet portrait, tablet landscape, laptop width and compact fallback.

### Adaptive icon constraints
Android's official guide requires separate foreground/background adaptive icon layers in a 108 × 108 dp container and a centered 66 × 66 dp safe zone; optional monochrome layer supports themed icons. The proposed foreground scales the logo to about 52 × 63 dp. This is source artwork, **not an installed launcher icon or native build validation**. Reference: https://developer.android.com/develop/ui/compose/system/icon_design_adaptive.

## Quality and status
The lab passed **76/76 Playwright Chromium interaction/layout checks** across 1440 × 900, 960 × 760, 768 × 1024 and 390 × 844; no horizontal overflow or uncaught JS page errors observed. Internal checks include logo selection, paper/night, skippable splash, palette, form validation, tab controls, error/empty/loading and concept screen switching. This is not a WCAG audit or device certification; exact production motion, touch targets, screen readers and Android launcher masks require real device tests.

## Next decision gate
Owner reviews whether Folded Moon or another study better expresses FOLILUNE and agrees on typography/color. Only then freeze A2, tune A3 for native component production, and move into **Phase B first-launch/onboarding and guided Home**, with genuinely interactive screen prototypes. Deep functionality is held for the next major update. Do not merge this design branch into the working reader without visual sign-off.
