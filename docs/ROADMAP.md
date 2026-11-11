# FOLILUNE — Preproduction-to-Release Roadmap
_No dates or engineering estimates committed yet. Phase gates prevent scope drift._

## Phase 0 — Foundation (COMPLETED: docs initialized)
**Deliverables:** project vision, principles, format/rights candidates, preliminary large-screen architecture, decision log, open research questions.
**Exit criteria:** product owner approves brief and priorities.

## Phase 1 — Evidence and positioning (RESEARCH DOCUMENTED; hands-on comparison and user validation still pending)
Reviewed official readers and current accessibility/content standards; documented user jobs, competitive feature overlap, rights constraints, technical candidates and prototype hypotheses in COMPETITIVE_RESEARCH.md, TECHNICAL_FEASIBILITY.md, READER_EXPERIENCE_SPEC.md, and PROTOTYPE_PLAN.md.
**Still open:** hands-on comparisons, reader usability validation, confirmed platform/format prioritization, costs and final positioning.
**Exit criteria:** prioritized user problems and defensible product positioning.

## Phase 2 — Experience system and prototypes (MODERN ATELIER selected; v2 visual prototype approved as a working baseline)
Define design language, interaction grammar and tablet/laptop responsive behavior. Present multiple visual directions and high-fidelity interactive flow prototypes. Review actual device-sized captures.
**Exit criteria:** approved visual direction, prototype usability findings, core flow acceptance.

## Phase 3 — Technical discovery and architecture (EARLY EXPERIMENT IN PROGRESS)
A verified **v7** standalone reader proof adds safely bundled PNG/JPEG/WebP EPUB illustrations with captions, bounded image import, and image-preserving backup/restore. See [ILLUSTRATED_EPUB_V7.md](ILLUSTRATED_EPUB_V7.md). It is still an experimental local reader, not a maintained EPUB rendering engine.

A verified **v6** standalone reader proof adds a quiet EPUB compatibility report for omitted publishing content and repairs backup reading-anchor serialization; see [EPUB_ENGINE_EVALUATION_V6.md](EPUB_ENGINE_EVALUATION_V6.md). Real browser-restart persistence and production engine replacement remain unverified.

An isolated v3 HTML reader proof now exercises simple local TXT/Markdown imports and text-only DRM-free EPUB extraction, plus per-book reading progress. This is **not** production engine selection, and real device persistence has not yet passed. See READER_ENGINE_SPIKE_V3.md. A follow-on v4 prototype adds basic EPUB text structure, block anchors, corrected live progress and graceful session-only imports when browser storage fails; its automated UI tests passed in Chromium at three sizes. **Still missing:** file-origin persistence testing, maintained production reader engine selection, and full EPUB/RTL/accessibility conformance. See READER_CONTINUITY_V4.md. An experimental v5 HTML prototype now adds manually exported/restored local library backups and defensive backup validation; it passed Chromium browser smoke tests at laptop/tablet/phone widths. See LIBRARY_RECOVERY_V5.md. A production EPUB engine is **still undecided**; see ENGINE_EVALUATION_2026-11.md.

Verify platform targets, reader engines, file import pipeline, rendering precision, persistence, sync choices, accessibility, security and costs. Build small feasibility spikes, not product feature sprawl.
**Exit criteria:** technical decisions, measurable performance budgets, feature cutline.

## Phase 4 — Core reading vertical slice
One complete happy path: legally acquired/imported sample → library → read → resume → bookmark/annotate → restore state; include a separate visual-format test flow if approved.
**Exit criteria:** usable, stable, visually approved slice.

## Phase 5 — Controlled expansion
Add approved advanced features in priority order, with discoverability tests, migration coverage, offline robustness and accessibility checks. AI/audio/social features remain optional candidates.
**Exit criteria:** feature complete for an explicitly agreed release scope.

## Phase 6 — Quality, beta and launch
Regression testing, device matrix, privacy/security review, performance, copyright/license audit, onboarding, beta feedback, release preparation and post-launch metrics.
**Exit criteria:** verified quality gates and user-approved release.

## Change-control rules
- Record new feature ideas in a backlog; do not silently insert into a current phase.
- Mark each decision as CONFIRMED, PROPOSED or DEFERRED.
- Never treat concept visuals as approved implementations.
- Run lightweight checks continuously; reserve comprehensive end-to-end testing for agreed milestones, without ignoring critical regressions.
- No claim of an APK, build or completed feature without actual artifact verification.

## v8 verification checkpoint — 2026-11-11
A reproducible, original-content EPUB fixture kit tested the unchanged Modern Atelier v7 in headless Chromium at laptop, tablet and phone viewport sizes (12/12 smoke checks passed). This **only** verifies the existing limited text-preview behavior, including warnings for unsupported publisher features, and safe rejection of an invalid sample. No maintained EPUB engine has been downloaded or integrated in this execution environment. See [ENGINE_VALIDATION_BASELINE_V8.md](ENGINE_VALIDATION_BASELINE_V8.md) and [READER_ENGINE_INTERFACE.md](READER_ENGINE_INTERFACE.md).

## v9 integration candidate checkpoint — 2026-11-11
An **isolated EPUB.js 0.3.93 adapter candidate** with Modern Atelier UI was committed as a separate file. The static failure states and the controls wired to a **mock** EPUB.js API passed 6/6 smoke tests across laptop/tablet/phone widths. No actual EPUB.js library could be downloaded or run in this environment; **real EPUB rendering, publisher fidelity and saved CFI restoration remain UNVERIFIED**. v7 remains unchanged. See [ENGINE_SPIKE_V9.md](ENGINE_SPIKE_V9.md).

## v10 browser runtime verification gate — 2026-11-11
Built a standalone, real-library EPUB.js test page with an original, valid EPUB3 fixture and bundled JSZip. Three viewport smoke tests confirmed the Modern Atelier shell, bundled JSZip, error reporting and prevention of false passes when the network blocks EPUB.js. **Actual EPUB.js rendering is still not verified here**. See [REAL_ENGINE_BROWSER_GATE_V10.md](REAL_ENGINE_BROWSER_GATE_V10.md). The v10 files are distributed as conversation downloads, not yet committed as GitHub prototype source. No production engine chosen.

## v11 — Real EPUB.js engine rendering verified (2026-11-11)

The isolated draft PR [#1](https://github.com/rayadinaveen98-ship-it/FOLILUNE/pull/1) executed **actual epubjs@0.3.93 in GitHub-hosted Chromium**, not a mock. The real engine passed **9/9 browser assertions**: original EPUB metadata, TOC, chapter 1 text, decoded embedded PNG, generated CFI, chapter 2 navigation, CFI restoration, and no uncaught JS exceptions. A follow-up CI run also passed and archived the exact tested EPUB.js and JSZip bundles plus licenses.

A **separate offline HTML v11 engine trial** was built from those bundles, retaining Modern Atelier styling. Local Chromium smoke tests at 1440×900, 960×760 and 390×844 confirmed real EPUB rendering and chapter navigation without external script downloads. **This is not the approved full app, production engine choice, or durability/security certification.** The existing v7 reader remains unchanged. See [REAL_EPUB_ENGINE_V11.md](REAL_EPUB_ENGINE_V11.md).

## Next concrete milestone
Test the offline v11 experiment on actual Windows Chrome/Edge and Android tablet, including browser-restart saved positions. Evaluate larger licensed EPUBs, publisher CSS/fixed layout/RTL/Indic support, accessible navigation and untrusted-book isolation, then integrate a validated adapter into the full Modern Atelier app only on a separate review branch.


## v12 integrated reading proof — 2026-11-11
Actual EPUB.js 0.3.93 is bundled into the existing Modern Atelier Library/Reader experimental HTML, preserving the v7 design and demo screens. The integrated Chromium fixture suite recorded 48/48 checks passing across laptop/tablet/phone viewport sizes. Local-file device persistence, old-fixture migration, EPUB3 conformance, security and accessibility remain open. See [INTEGRATED_READER_V12.md](INTEGRATED_READER_V12.md). No production approval yet.

## vNext premium visual experience — parallel workstream (2026-11-11)
Owner approved beginning a comprehensive **visual and experience quality update**, before the separately planned deeper functionality update. This workstream is isolated from the v12 tablet APK and real EPUB.js engine.

- **A1 — Identity direction: READY FOR OWNER REVIEW.** Modern Atelier 2 brief, 3 original vector logo concepts, light/night palette, skippable welcome motion, Home/Reader visual studies, and 48/48 browser checks at four viewport widths. [Review](../prototypes/vnext-identity-review-v01.html), [brief](VNEXT_BRAND_IDENTITY_V01.md), [QA](VNEXT_IDENTITY_QA_V01.md).
- **A2 — Final identity: NEXT, NOT YET APPROVED.** Owner selects/refines mark; test monochrome/small-size/adaptive icon, wordmark and artwork language.
- **A3 — Design system: PENDING.** Finalize color, typography, spacing, icons, controls, focus/hover states, motion tokens, responsive behavior.
- **B — First-launch: PENDING.** Splash, skippable welcome, optional setup, first-use guide; no forced cloud sign-in.
- **C — Main surfaces: PENDING.** Home, Library, Story, Comic and Study shells across tablet/laptop widths.
- **D — Secondary surfaces: PENDING.** Search, notes/highlights UI, collections, settings, import, empty/loading/error, accessibility/help.
- **E — Visual QA and sign-off: PENDING.** Original art, motion/copy polish, tablet/desktop screenshots, accessibility and owner approval.

**Do not merge visual choices into the installed APK without explicit approval.** Backup/sync and deep functionality remain in later workstreams.

## vNext A2/A3 review gate — 2026-11-11
A functioning offline design laboratory now demonstrates identity choices A/B/C (A remains **provisional**), Android foreground/background/monochrome vector layers, app icon mask and 24/48/96px mark studies, paper/night palette, typography, icons, buttons/forms/tabs, empty/loading/error states and Home/Reader/Settings design shells. **76/76 browser interaction/layout checks passed** at four viewports. See [A2/A3 design spec](VNEXT_DESIGN_SYSTEM_A2_A3.md), [QA](VNEXT_A2_A3_QA.md) and [interactive prototype](../prototypes/vnext-experience-lab-v02.html).
**Next:** owner direction on logo and paper/night rhythm; Phase B splash/welcome/setup prototypes. No production EPUB engine/UI changes or APK released in this design workstream.
