# FOLILUNE G2 — Lunar Editorial end-to-end experience contract v0.1
**Date:** 2026-11-16
**Status:** Founder's **Lunar Editorial direction APPROVED**. This detailed implementation is a **review candidate**, not final visual acceptance.
**Branch:** `experiment/vnext-g2-live-surfaces` (draft PR #7 targeting experimental G1).
**Locked exclusions:** G3 annotations/custom shelves, native backup/restore/migration, cloud/accounts, monetization, production signing, merges or launch.

## Product promise
A calm, original, trustworthy local-first reading room. A person brings a legitimately obtained DRM-free book, reads comfortably, can resume or bookmark that book, and always knows whether a screen is connected to a real publication or a labeled sample. **Reading is primary; illustration supports it.**

## A single journey, rather than disconnected features
| Moment | User intent | Source of truth / implemented route | Experience acceptance |
| --- | --- | --- | --- |
| Launch | Arrive without confusion | Native WebView + G1 visual bridge | Stable first paint; returning users do not see forced welcome |
| First use | Know what it does | Skippable 3-step G2 welcome | Accessible progress semantics; intro → comfort → own-book import or explore |
| Empty Home | Find next action | G2 Home + real `snapshot().books` | One editorial headline, one import CTA, labeled sample shelf, local privacy caveat |
| Import | Bring own book | Existing Android SAF/file import | File validation, errors, no fictional catalog; no new data permissions |
| Populated Home | Open or resume | `lastOpened`, `data-book` | Newly imported says *Open book*, actually opened says *Continue reading* |
| Library/search | Find a publication | Existing G2 data bridge | Real titles only, clear filter/no-results and sample distinction |
| Real EPUB reading | Disappear into a book | Bundled EPUB.js/JSZip; v12 reader behavior unchanged | No new engine or publisher CSS override, readable chrome, TOC, CFI |
| Saved place | Return to a location | Existing EPUB CFI/chapter bookmarks | Exact request to saved CFI; no deleted-book resurrection |
| Collections/notes | Organize/find existing data | G2 automatic shelves, preview-study annotations | No claim that custom shelves or real EPUB annotations already exist |
| Settings/help | Choose and recover | Existing settings & Help | Accurate local-only limitations, actionable errors, source clarity |

## Shared Lunar Editorial G2 visual grammar
**Art direction:** Modern Atelier's warm paper, mineral ink, moss, quiet brass, crescent/book imagery. No unrelated illustrations or new paid font assets. Keep user-selected EPUB page theme independent from app chrome.

- **Tokens:** continue to use verified `--v-*` Paper/Night colors; additive `--le-*` tokens are centralized in `vnext-lunar-editorial-g2.css`. They are candidate values pending visual approval.
- **Typography:** elegant classic serif display only for heading/book identity; high-legibility system sans-serif for navigation, controls and support text. Max supporting-text measure `62ch`. Never force font choices inside publisher EPUB iframes.
- **Surfaces:** consistent 18px panel corners, 12px controls, light paper shadows; Night uses defined surface/border pairs without light-theme artifacts. Avoid piling multiple large cards on the same empty screen.
- **Interaction:** min 48 CSS-px core controls (separate physical Android dp verification still required), visible 3px focus, selected states expressed with text/border as well as color. Small hover lift only on devices that actually hover.
- **Motion:** meaningful restrained transitions, no animation required for state understanding, honor reduced motion.
- **Layout:** window-width adaptation for portrait/landscape/split screen; preserve top-bar/navigation; long titles must wrap or truncate safely; 200% system text requires on-device verification.
- **Illustration:** existing Folded Moon/book art retained as provisional; app icon master and custom imagery not frozen and **not yet founder-accepted**.

## Quality gates
1. Screens 1–8: a semantic H1 and shared G2 shell after route navigation; no horizontal viewport overflow on Android emulator.
2. Three welcome steps show the correct accessible `aria-current="step"`, Skip works and never erases imported books.
3. Current real EPUB/import/CFI/bookmark/deletion/restoration tests remain green; neither Android package nor storage schema changes.
4. Compare emulator captures for Paper/Night and Home/Library/Welcome; record screenshot and real device differences. Emulator screenshot **does not certify** real tablet.
5. Physical test: 10–11 inch tablet portrait, landscape and multi-window, screen zoom, 200% font, TalkBack, touch focus, Android SAF picker, device process death. No G2 closure before founder physical signoff.
6. Unsupported/corrupt EPUB handling, oversized files, RTL/Indic and security stress remain pending certification.

## G2 execution stages (no feature drift)
- **G2-F / current:** unify presentational tokens, headings, panels, book grids, interaction focus and the first-use step indicator. Add source-backed Android emulator design-system QA.
- **G2-G / pending:** inspect actual multi-screen screenshots, reconcile layout/empty/error states, perform focused approved visual refinements of Library/Search/Saved/Collections/Notes/Help.
- **G2-H / pending:** present real EPUB reader chrome, settings and reading interruption states as one path; keep book render engine protected.
- **G2-I / pending:** physical-tablet accessibility + EPUB stress and founder visual acceptance; gate all later stages.

**Scope truth:** A cohesive first pass is **not** a finished premium product. Every changed screen needs both implementation and real-device visual acceptance before declaring G2 complete.
