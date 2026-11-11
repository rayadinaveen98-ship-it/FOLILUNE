# FOLILUNE vNext — Phase E QA v06

**Date:** 2026-11-11 · **Method:** Playwright Chromium headless, restricted `page.set_content()` harness with **explicit mock in-memory localStorage**. No real file-origin or localhost navigation was available.

| Viewport | Resolution | Passed |
| --- | --- | ---: |
| Laptop | 1440 × 900 | 48/48 |
| Tablet landscape | 960 × 760 | 48/48 |
| Tablet portrait | 768 × 1024 | 48/48 |
| Compact | 390 × 844 | 48/48 |
| Narrow stress | 320 × 720 | 48/48 |
| Separate reduced-motion check | 960 × 760 | 1/1 |
| **TOTAL** | | **241/241** |

Tests exercise the actual **Skip intro** (splash is removed, not merely bypassed in test code); welcome artwork; reading interest selection; Paper/Night and typography handoff; first-run guided Home; Home, Library, Story, Visual, Study and all Phase D supporting screens; Library/Search filtering; bookmark creation and Saved; Help and States; responsive reading-page minimum width; replay preferences/bookmark preservation; simulated return in a **fresh JavaScript realm** with copied test-only storage; no horizontal page overflow, uncaught page errors or remote HTTP requests on tested routes.

**Touch target spot audit:** at 390px and 768px, all visible buttons on Home, Library, Story, Settings, Notes and More were >=44 × 44 CSS px following targeted toolbar, replay, filter and top-action adjustments. Does not constitute complete WCAG certification.

**Bugs found and corrected:**
1. Onboarding setup re-render silently overwrote splash Skip's click handler. Bound only `#screen [data-action]` to preserve the splash handler.
2. Replay initialized stale preferences instead of the main app's current appearance and text size.
3. Reader/top bar/filter controls were 37–42px in some compact/tablet views; raised touch targets to 44px.
4. Test harness initially selected Phase B's reader class rather than Phase D's actual `.paper-page`; corrected.
5. Same-document storage replay created a duplicate global `const` and was replaced with a new JS realm; the simulated return does **not** prove real disk persistence.

**Still open:** real Android tablet hardware, browser/WebView storage durability, screen readers/focus traversal, every contrast pair, realistic font scaling, production EPUB/CBZ/PDF handling, UI→v12 data integration, upgrade migration, privacy/security and owner sign-off. Existing v12 EPUB.js emulator evidence is for the **separate** functioning reader, not this visual vNext.
