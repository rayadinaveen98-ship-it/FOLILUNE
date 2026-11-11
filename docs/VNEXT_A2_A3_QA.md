# FOLILUNE vNext A2/A3 — Browser QA (v02)
**Executed:** 11 November 2026, Playwright headless Chromium, offline HTML loaded as content. **PASS: 76/76** (19 each at laptop 1440×900, landscape tablet 960×760, portrait tablet 768×1024 and compact phone 390×844).

## Checks at every viewport
Visible logo; default A; select B and return to A; night/paper theme; open/skip welcome; 8 palette swatches; form components and validation; loading/error states; Home, Reader, Settings sample navigation; no horizontal page overflow; zero uncaught JS errors.

An initial compact-screen identity overlap and an HTML native-form validation conflict were found and fixed **before** the passing 76/76 run.

## Selected contrast ratios (WCAG luminance calculation)
- Ink on paper: 12.71:1
- Supporting ink on paper: 6.25:1
- Brass on paper: 5.02:1
- Moss on paper: 7.85:1
- Night ink on dark surface: 13.35:1
- Night supporting ink on dark surface: 9.32:1
- Night brass on dark surface: 8.21:1

These are token-pair calculations only, not certification of all overlays, buttons, accessibility, keyboard focus trap, or native app. See https://www.w3.org/TR/WCAG22/#contrast-minimum. The standalone HTML is a **design laboratory**, not a working reader. Final full QA evidence (JSON/screenshots/test script) is included in conversation download.
