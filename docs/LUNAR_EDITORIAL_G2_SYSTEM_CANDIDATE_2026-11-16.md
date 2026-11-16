# FOLILUNE Lunar Editorial — G2 Experience System Candidate
**2026-11-16 | Design direction: APPROVED | Exact tokens, mark and screens: REVIEW CANDIDATE**

Lunar Editorial **evolves**, rather than replaces, the accepted Modern Atelier baseline. Reading content dominates; the system offers quiet editorial hierarchy and warm mineral-paper atmosphere without delaying a book. No visual renders are claimed as delivered here.

## Source-inventory baseline (current, not redesigned)
The existing `vnext-integrated.css` uses:

| Role | Paper theme | Night theme | Evidence state |
| --- | --- | --- | --- |
| Background | `#F5F2EA` | `#101A18` | Current CSS |
| Elevated surface | `#FFFCF5` | `#1B2925` | Current CSS |
| Main ink | `#26342E` | `#F3F1E9` | Current CSS |
| Muted type | `#516359` | `#C3CEC6` | Current CSS |
| Moss | `#365747` | `#A9C6B1` | Current CSS |
| Brass accent | `#94632C` | `#DCBA87` | Current CSS |
| Border | `#D9D9CB` | `#34483F` | Current CSS |

### Contrast checks (WCAG relative-luminance calculation, not device validation)
- Main ink on Paper background: **11.64:1**.
- Muted type on Paper background: **5.73:1**.
- Brass on Paper background: **4.60:1**; sufficient for normal AA text at this exact pairing, but small decorative accents should not be the only signal.
- White on Paper moss button: **8.04:1**.
- Night ink on Night background: **15.70:1**.
- Night muted type on Night background: **10.96:1**.
- Night brass on Night background: **9.65:1**.

**Caution:** These calculations do not certify every surface state (hover, selected, focus, translucent overlay, book cover, image, disabled control). Full contrast matrix is still required.

## Proposed token grammar — needs visual review before freeze
- **Type:** elegant literary heading face with robust fallback; legible UI/system face; reader type chosen by user. Keep title, section and metadata scale coherent across all eight connected surfaces. No external font download or license commitment.
- **Spacing:** consistent 4/8pt-based scale; prefer 8, 12, 16, 24, 32, 48 in the G2 shell; do not apply wholesale to EPUB publisher body text.
- **Shape:** 12px compact control, 16–20px surface panels, restrained paper-edge cover radius. Avoid novelty polygons and decorative cards around every line.
- **Hierarchy:** deep ink for primary text, muted ink for secondary text, moss for action, brass for infrequent attention/brand details only.
- **Motion:** meaningful 150–300ms UI transitions, one skippable first-launch brand moment, returning launch without an extra blocking reveal. Honor `prefers-reduced-motion` and Android animation settings; no dependence on motion for meaning.
- **Focus and touch:** clear 3px or similarly visible focus ring, minimum **48dp effective touch area** on Android. G2 CSS has a 48px candidate override for selected controls but requires real density, font-scale and TalkBack checks.
- **Responsive reading:** bounded text measure, contextual controls, navigation adapted to *window width*, not a tablet-name heuristic; no forced orientation.
- **Folded Moon mark:** propose a single-page-plane crescent with flat small-size silhouette; test adaptive launcher foreground/background and monochrome. Existing Android icon remains **provisional** until founder sees actual visuals.

## State-by-state application order
1. Launcher/splash and returning-launch behavior (no additional blocking animation).
2. Welcome/reading-comfort/import first-run path, including skip and accessibility.
3. Empty Home, populated Home and Library without fictional catalog assumptions.
4. Search, Saved Places and settings controls, then Collections/Notes/Help.
5. Real EPUB shell (toolbar hidden/shown, TOC, bookmark, appearance, error fallback).
6. Corrupt/unsupported/duplicate EPUB, storage failure, no result, delete confirmation, offline and restoration safety.

Each screen must be captured on a real Android tablet and evaluated at normal and 200% system text, portrait/landscape, reduced motion, TalkBack and keyboard where available. Confirm design visually with founder before calling any screen accepted.

## Scope guardrails
- **Implemented this checkpoint:** G2 CSS hit-area baseline only; no palette rewrite, animations, new icon master, navigation changes, storage migration or feature expansion.
- **Still proposed:** additional reader themes, icon replacement, onboarding changes, full visual grammar rollout and advanced annotation-related interactions.
- **Explicitly deferred:** G3 annotations/custom collections; G4 data portability/migration pending approval; cloud, AI, PDFs, comics, account features, subscriptions, beta and release.
