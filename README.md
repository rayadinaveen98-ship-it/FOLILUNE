# FOLILUNE
**A world between pages.**

FOLILUNE is a tablet- and laptop-first reading experience in preproduction. The product aims to make reading novels, visual stories, nonfiction, essays, and other written forms deeply personal and beautifully simple, while revealing richer capabilities only when readers need them.

## Product status (verified 2026-11-16)
- **Stage:** **G2 — Experience Foundation & Acceptance**, experimental; **not released**.
- **Code:** Real offline EPUB.js Android WebView integration and connected Home/Library/Search/Saved Places/Settings/Collections/Notes/Help exist on draft [PR #7](https://github.com/rayadinaveen98-ship-it/FOLILUNE/pull/7), based on experimental G1 [PR #6](https://github.com/rayadinaveen98-ship-it/FOLILUNE/pull/6). This is **not** merged to main.
- **Verification:** Three emulator CI workflows passed at G2 commit `03b91a4a`. New G2 deletion/recovery regression tests were added later and require their **own** passing run before any success claim.
- **Visual direction:** Founder approved **Lunar Editorial**, evolving Modern Atelier, on 2026-11-16. Folded Moon final artwork, tokens, animation and screen-by-screen design are **not yet approved or implemented**.
- **Platform:** Experimental Android tablet package `com.folilune.vnextreader`. Physical tablet, laptop-grade behavior and release readiness remain unverified.
- **Data protection:** Existing v12 `com.folilune.tablet` and G2 have **separate private storage**; no migration or working Android backup/export path. Do not uninstall, clear data, change signing identity or assume continuity. Keep original book files.
- **Tagline:** “A world between pages” remains working copy; “Your private reading room” is a proposed positioning line, not finalized marketing copy.

See the [2026-11-16 G2 audit and acceptance register](docs/G2_EXPERIENCE_AUDIT_2026-11-16.md), [updated roadmap](docs/ROADMAP.md) and [decision register](docs/DECISIONS.md).

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

## Android tablet QA
- [Android WebView process-restart persistence report](docs/ANDROID_TABLET_RELIABILITY_QA_V12.md)

## Latest Android reader evidence
- [Exact CFI and bookmark recovery after process death — emulator passed](docs/ANDROID_CFI_BOOKMARK_QA_V13.md)
