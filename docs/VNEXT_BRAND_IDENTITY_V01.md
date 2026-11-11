# FOLILUNE — Modern Atelier, Elevated
**Identity & Experience Direction / Phase A / Review Candidate 01**  
**Status:** PROPOSED — owner approval required before implementation  
**Date:** 11 November 2026  
**Workstream:** vNext premium experience, visually separate from v12 tablet reader

## A. Non-negotiable inheritance

FOLILUNE is a personal reading environment for novels, visual stories, essays, research and other writing. Prioritize tablet and laptop, with a compact-phone fallback. Honor the owner-approved **Modern Atelier** direction and its warm, editorial feel; do not pretend that a concept demonstration has become the production reading app.

**Product promise:** A world between pages. (Existing working tagline; pending approval.)  
**Design intent:** A quiet entry, a memorable identity and thoughtfully revealed depth. Premium means clarity, readability, credible content treatment and care in every state—not decorative effects covering books.

## B. Three original, implementable logo studies

All marks are simple, reproducible vector SVG (no image-model dependency, no specialty font, no runtime API). All require small-size scrutiny in 24 px, 48 dp Android adaptive icon foreground, splash, website favicon and monochrome.

1. **A — Folded Moon / Recommended to review first.** A crescent-like open arc above an opening folio; the ‘world between pages’ narrative in two gestures. Distinctive, recognizable at small sizes. Risks: too abstract; opening and page should remain recognizable in monochrome.
2. **B — Leafbound F.** A continuous, abstract F-shaped folded folio; more functional and modern, less atmospheric. Risks: generic monogram; must not look like a fintech logo.
3. **C — Story Aperture.** A thin orbital ring intersecting an open page; cinematic and spacious. Risks: reduced legibility at 24 px; guard against similarity to other circular brands.

**Proposed wordmark:** FOLILUNE uppercase with editorial letter spacing; use a serif for display-sized wordmark. Never stretch, skew, artificially emboss or use gradients as the only contrast. For utility/sidebar use the short mark plus a readable sans label.

**Decision gate:** choose one mark, then test dark/light/one-color, 24/48/192/512 px, adaptive icon mask and splash art. No mark currently locked.

## C. Color tokens — Modern Atelier 2 (proposed)

| Role | Light | Dark | Usage |
|---|---|---|---|
| Canvas | `#F5F2EA` | `#101A18` | Shell/background |
| Surface | `#FFFCF5` | `#1B2925` | Panels/cards |
| Surface muted | `#ECE6DA` | `#26352E` | Search, tabs, secondary panels |
| Ink | `#26342E` | `#F3F1E9` | Main body text |
| Secondary text | `#516359` | `#C3CEC6` | Descriptions/metadata |
| Moss | `#365747` | `#A9C6B1` | Primary emphasis/actions |
| Brass | `#94632C` | `#DCBA87` | Tiny highlights, editorial signals |
| Line | `#D9D8CB` | `#34483F` | Dividers |
| Error | `#9C4238` | `#F5A99F` | Inline errors |

Do not use brass for small body copy on cream without a measured contrast check. Moss and ink carry most legible UI. Avoid tinted backgrounds for critical notices unless needed. Ensure status is conveyed by icon and text, not color alone.

## D. Typography

- **Display:** Georgia or a subsequently vetted licensed serif, not a network-dependent web font. Reading content and publisher typography remain separable from app chrome.
- **UI:** Segoe UI / Android system sans-serif / system fallback. Never distribute proprietary font files.
- **Hierarchy proposal:** 44/50 hero, 32/40 screen heading, 24/32 section heading, 18/28 card heading, 16/24 body, 14/21 secondary, 12/18 labels. Adjust via size class; avoid using tiny labels as the only information carrier.
- **Prose default:** 18 px, adjustable 16–30 px, line-height ~1.65; target around 55–75 characters per line, responsive to screen width and settings. Respect author content.
- **Copy tone:** lucid, warm, confident; no hyperbolic streaks or guilt prompts; brief success messages and actionable errors.

## E. Component & spatial grammar

- Unit: 4 px. Base rhythm: 8/12/16/24/32/48/64. Do not choose arbitrary per-screen margins.
- Content max width: 1360–1440 px for shell, 680–780 px for single-column reading prose; study workspace uses split panes only when content warrants.
- Touch targets: target >=48 x 48 dp on Android, at least 44 x 44 CSS px on web; keep 8 px separation where possible.
- Nav: persistent sidebar on spacious laptop; compact rail/side-sheet on tablet landscape; bottom navigation + occasional sheet for tablet portrait/compact widths. Always visible path back from reader.
- Cards: 12–20 px radius based on hierarchy; subtle border before shadow; restrained shadows never hover-dependent on touch.
- Icons: 20/24 dp utility; 1.8–2.0 px stroke at 24 viewport, rounded caps and joins, optical centering, stable metaphors. Never mix emoji, filled glyphs and thin line icons in primary navigation.
- Input: persistent labels; focus ring; clear invalid/disabled states; no placeholder-only field names.
- Dark mode: a real tuned ink-and-moss palette, not a simple inverse filter.

## F. Opening and onboarding motion

- Splash must not block a returning user from immediately accessing their book. First launch may show a 1.4–2.0 second identity reveal, always skippable; returning opens are near-instant with a subtle <=350 ms transition.
- Proposed sequence: ink-colored arc draws -> folio opens -> wordmark settles -> clean handoff to Home. No flashing, blurring text or prolonged forced waits.
- Respect `prefers-reduced-motion`, system animation settings and accessibility switch. Provide a no-motion equivalent.
- Never animate the pages of a book without respecting its pagination and chosen reading direction.
- Setup: optional reading interests/theme/format questions, a clear Skip, local-first explanation, no forced sign-in or unnecessary permissions. Account/sync only after implemented.

## G. Information architecture — first design slice

**Primary Home**: resume prominent, add/import a book, 2–3 shelves with purpose, restrained personal greeting. No dashboard wall of cards or deceptive recommendation systems.  
**Library**: visual grid/list, collections, search/filter, authors/series as optional secondary views.  
**Reader**: text or artwork takes priority, controls reveal on demand, chapter/progress/notes clearly accessible.  
**Study**: content-first workspace, notes panel secondary, no fake integrated engine claims.  
**Settings**: Reading, Appearance, Library & imports, Accessibility, Privacy & data, About & help; optional Account & sync is explicitly marked future/deferred.

Do not add live recommendations, cloud services, comic panel OCR, AI summaries or audiobook control without the next functional scope approval. Concept UI must mark unimplemented actions as demonstrations.

## H. Every state needs design

Primary, hover, pressed, focused, loading, empty, offline, importing, import failed, corrupt publication, storage full, permission denied, successfully saved, undo, disabled, untrusted content, reduced motion, large type, dark theme, portrait/landscape and keyboard navigation.

## I. Size-class review matrix

| Width of actual app container | Layout intent |
|---|---|
| below 640 px | compact stack, bottom navigation, full-screen book surface |
| 640–899 px | tablet portrait: wide readable center and low-depth nav |
| 900–1199 px | tablet landscape: side navigation, flexible 2-column shelves |
| 1200 px+ | laptop: persistent rail, rich shelves and optional workspace split |

Resize based on available container width, not just user-agent device labels. Confirm real Android tablet DP sizes and Windows laptop browser/window sizes.

## J. Motion and performance budgets — proposed acceptance targets, not measured results

- First useful reader control in <=1 intentional gesture once book is open.
- 100% of core navigation keyboard reachable; no hover-only action.
- App navigation transitions generally <=250 ms; entry effects skippable.
- No continuous decorative animations in reader; no external image/font dependency for basic navigation.
- Screenshot review at 390×844, 768×1024, 960×760, 1280×800, 1440×900; portrait and landscape.
- WCAG 2.2 AA as design goal for user controls, focus states, visual contrast; verify with tools and actual assistive technology before claim of compliance.
- For every interactive preview action, either implement it or visibly label it as a concept. No inert controls masquerading as working product.

## K. Phase A work packages / acceptance gates

**A1. Identity directions (this review):** original SVG logo A/B/C, wordmark, light/dark palettes, typography, motif and 4 states. Preview owner selects a direction or requests revision. **Not locked.**

**A2. Brand system:** final master logo, adaptive Android icon, favicon, scalable art, brand tone, light/dark contrast audit. Freeze only with approval.

**A3. Design tokens/components:** structured JSON/design variables for typography, spacing, icon geometry, colors, radii, shadows, focus, states. Verify across tablet/laptop.

**B. Entry experience:** splash, welcome and setup variants, reduced-motion parity, screen-by-screen approval. Keep sign-in optional until sync is ready.

**C. Primary surfaces:** Home, Library, Story Reader, Comic, Study, navigation shells. No engine replacement or data migration unless separately approved.

**D. Supporting surfaces:** search, annotations shells, settings, import/error/empty screens and help.

**E. Refinement & acceptance:** original artwork system, motion implementation, copy editing, accessibility and responsive visual QA; screenshot evidence and real tablet review.

**After E (separate major update):** depth of functionality, format engines, notes/sync/search/AI/audio features as specifically prioritized. Don't silently pull functional work into this visual update.

## L. Quality approval checklist (must pass before implementation merge)

1. Owner selects a logo and approves light/dark brand mood.
2. Reader content remains more legible than preview chrome.
3. Tablet portrait and landscape, laptop and compact widths look intentional.
4. Consistent icons, typography, input, focus, spacing and sensible target sizes.
5. No blank page, fake data promises or interactive dead ends.
6. Reduced motion, keyboard and touch pathways reviewed.
7. Original design assets can be faithfully implemented and reproduced as vectors.
8. Main reader project and v12 APK baseline stay untouched until approval.

**Current outcome:** Brand direction v0.1 and executable review concept; A1 in owner review, A2–E not started. No completed logo or visual system approval implied.