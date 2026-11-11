# FOLILUNE — Preproduction-to-Release Roadmap
_No dates or engineering estimates committed yet. Phase gates prevent scope drift._

## Phase 0 — Foundation (NOW)
**Deliverables:** project vision, principles, format/rights candidates, preliminary large-screen architecture, decision log, open research questions.
**Exit criteria:** product owner approves brief and priorities.

## Phase 1 — Evidence and positioning (RESEARCH DRAFT COMPLETE; awaiting owner review and hands-on validation)
Reviewed official readers and current accessibility/content standards; documented user jobs, competitive feature overlap, rights constraints, technical candidates and prototype hypotheses in COMPETITIVE_RESEARCH.md, TECHNICAL_FEASIBILITY.md, READER_EXPERIENCE_SPEC.md, and PROTOTYPE_PLAN.md.
**Still open:** hands-on comparisons, reader usability validation, confirmed platform/format prioritization, costs and final positioning.
**Exit criteria:** prioritized user problems and defensible product positioning.

## Phase 2 — Experience system and prototypes (MODERN ATELIER selected; v2 visual prototype approved as a working baseline)
Define design language, interaction grammar and tablet/laptop responsive behavior. Present multiple visual directions and high-fidelity interactive flow prototypes. Review actual device-sized captures.
**Exit criteria:** approved visual direction, prototype usability findings, core flow acceptance.

## Phase 3 — Technical discovery and architecture (EARLY EXPERIMENT IN PROGRESS)
An isolated v3 HTML reader proof now exercises simple local TXT/Markdown imports and text-only DRM-free EPUB extraction, plus per-book reading progress. This is **not** production engine selection, and real device persistence has not yet passed. See READER_ENGINE_SPIKE_V3.md. A follow-on v4 prototype adds basic EPUB text structure, block anchors, corrected live progress and graceful session-only imports when browser storage fails; its automated UI tests passed in Chromium at three sizes. **Still missing:** file-origin persistence testing, maintained production reader engine selection, and full EPUB/RTL/accessibility conformance. See READER_CONTINUITY_V4.md.

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

## Next concrete milestone
Conduct hands-on competitor/engine checks and present low-fidelity interaction maps for **Story** and **Visual** experiences on laptop and tablet. No style or technology is locked. See PROTOTYPE_PLAN.md.
