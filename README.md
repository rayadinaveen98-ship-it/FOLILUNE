# FOLILUNE
**A world between pages.**

FOLILUNE is a tablet- and laptop-first reading experience in preproduction. The product aims to make reading novels, visual stories, nonfiction, essays, and other written forms deeply personal and beautifully simple, while revealing richer capabilities only when readers need them.

## Product status
- **Stage:** Phase 0 — discovery and preproduction
- **Code:** Not started
- **Visual UI:** Not approved
- **Platform strategy:** Tablet and laptop first; precise deployment targets TBD
- **Tagline:** Working concept, not final marketing copy

## Core experience principles
1. **Calm on the surface, powerful beneath:** progressive discovery over feature dumping.
2. **The content is the hero:** legibility, comfort, accessibility and reading flow outrank decorative effects.
3. **Form-aware experiences:** prose, comics/manga, documents and other formats deserve tailored interactions.
4. **Built for large screens:** genuine responsive tablet/desktop layouts, not stretched phone screens.
5. **Reader agency:** customization, offline access, privacy and optional intelligence; never force distracting features.
6. **Legitimate content access:** reader-owned files, public-domain and licensed works; no assumption that copyrighted catalogs may be bundled.

## Preproduction documents
- [Product brief](docs/PRODUCT_BRIEF.md)
- [Experience architecture](docs/EXPERIENCE_ARCHITECTURE.md)
- [Formats and content rights](docs/FORMATS_AND_RIGHTS.md)
- [Roadmap](docs/ROADMAP.md)
- [Decision register](docs/DECISIONS.md)

## Phase 1 research
- [Competitive research and positioning](docs/COMPETITIVE_RESEARCH.md)
- [Technical feasibility](docs/TECHNICAL_FEASIBILITY.md)
- [Progressive discovery experience specification](docs/READER_EXPERIENCE_SPEC.md)
- [Tablet and laptop prototype plan](docs/PROTOTYPE_PLAN.md)
- [Modern Atelier v3 reader proof](prototypes/modern-atelier-reader-proof-v3.html)
- [Modern Atelier v4 reader proof](prototypes/modern-atelier-reader-proof-v4.html)
- [v4 reader-continuity tests and limitations](docs/READER_CONTINUITY_V4.md)
- [v5 library recovery report (conversation prototype)](docs/LIBRARY_RECOVERY_V5.md)
- [Modern Atelier v6 compatibility prototype](prototypes/modern-atelier-reader-proof-v6.html)
- [Modern Atelier v7 illustrated EPUB reader proof](prototypes/modern-atelier-reader-proof-v7.html)
- [v7 illustration support, tests and limitations](docs/ILLUSTRATED_EPUB_V7.md)
- [v6 EPUB engine evaluation, fidelity and recovery test report](docs/EPUB_ENGINE_EVALUATION_V6.md)
- [EPUB engine evaluation (Nov 2026)](docs/ENGINE_EVALUATION_2026-11.md)
- [Phase 3 engine spike and test limitations](docs/READER_ENGINE_SPIKE_V3.md)
- [v8 EPUB validation matrix: 12/12 baseline checks](docs/ENGINE_VALIDATION_BASELINE_V8.md)
- [Reader engine adapter boundary (proposed)](docs/READER_ENGINE_INTERFACE.md)
- [v9 EPUB.js integration candidate — needs online engine/runtime testing](prototypes/epubjs-engine-spike-v9.html)
- [v9 test matrix and remaining engine gates](docs/ENGINE_SPIKE_V9.md)
- [v12 integrated real-engine prototype test report](docs/INTEGRATED_READER_V12.md)
- [v10 real EPUB.js browser verification (conversation test kit)](docs/REAL_ENGINE_BROWSER_GATE_V10.md)
- [v11 VERIFIED real EPUB.js renderer (9/9 unmocked CI tests)](docs/REAL_EPUB_ENGINE_V11.md)
- [v11 isolated engine experiment (draft PR, not merged)](https://github.com/rayadinaveen98-ship-it/FOLILUNE/pull/1)

**Important:** Proposed features in these documents are discovery candidates, not approved scope. Implementation technologies, final branding, monetization and detailed visuals remain open decisions.

## vNext — Premium visual experience (design branch / review only)
- [Identity and art-direction brief](docs/VNEXT_BRAND_IDENTITY_V01.md)
- [Interactive identity, welcome, Home and Reader preview](prototypes/vnext-identity-review-v01.html)
- [Viewport and contrast QA](docs/VNEXT_IDENTITY_QA_V01.md)
- [Proposed design tokens](design/tokens/vnext-proposal-v01.json)

**Status:** Phase A1 in owner review. No logo is finalized. This does not modify the functioning v12 Android reader.

### vNext Phase A2/A3 design review
- [Interactive visual system laboratory](prototypes/vnext-experience-lab-v02.html)
- [Brand and component specification](docs/VNEXT_DESIGN_SYSTEM_A2_A3.md)
- [76/76 responsive browser test report](docs/VNEXT_A2_A3_QA.md)
- [Proposed design tokens](design/tokens/vnext-components-v02.json)
- [Reproducible SVG primary mark](design/identity-v02/folded-moon.svg) — **provisional, awaiting owner selection**.

The v12 tablet reader is unchanged. No logo is locked or integrated into an APK.

### vNext Phase B — first-launch visual flow (review edition 03)
- [Interactive offline Phase B experience](prototypes/vnext-first-launch-phase-b-v03.html)
- [Screen-by-screen interaction blueprint](docs/VNEXT_PHASE_B_EXPERIENCE_V03.md)
- [Browser validation: 116/116 checks and limitations](docs/VNEXT_PHASE_B_QA_V03.md)

**Review only.** This new welcome/setup/guide/reader preview does not replace the working v12 Android EPUB app and is not a new APK. Final logo selection remains pending.

### vNext Phase C — Main reading surfaces (review only)
- [Offline interactive Home / Library / Story / Visual / Study experience](prototypes/vnext-main-surfaces-phase-c-v04.html)
- [Phase C composition and interaction specification](docs/VNEXT_PHASE_C_EXPERIENCE_V04.md)
- [169/169 Chromium browser and responsive QA summary](docs/VNEXT_PHASE_C_QA_V04.md)

**Not a release build.** Original demo content and illustration only. Real EPUB.js imports and persistent reading are in the separate existing v12 prototype; Folded Moon remains a candidate logo. The Phase B first-launch and Phase C main-surface reviews are separate HTML artifacts pending owner approval and integration.

### vNext Phase D — Supporting experience (interactive visual study 05)
- [Open the complete offline Phase D prototype](prototypes/vnext-supporting-experience-phase-d-v05.html)
- [Screen inventory, data boundaries and owner review gate](docs/VNEXT_PHASE_D_EXPERIENCE_V05.md)
- [QA: 219/219 Phase D checks, 56/56 Phase C regressions](docs/VNEXT_PHASE_D_QA_V05.md)

Includes a connected sample Search, Collections, Saved Places, Notes, full Settings, Help, empty/loading/error states and More hub. **This is design-only, not a new Android APK.** All demo data is separate from v12. Phase E visual/motion/accessibility polish is next and the owner still needs to approve the visuals.
