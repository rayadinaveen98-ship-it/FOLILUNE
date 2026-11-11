# FOLILUNE — Decision Register
_Last reviewed: 11 November 2026_

| ID | Status | Decision / constraint | Rationale |
| --- | --- | --- | --- |
| D-001 | CONFIRMED | Working product name: FOLILUNE; GitHub repository `rayadinaveen98-ship-it/FOLILUNE`. | Owner created repository. |
| D-002 | CONFIRMED | Prioritize tablet and laptop reading experiences. | Owner's explicit priority. |
| D-003 | CONFIRMED | Surface should feel simple; advanced capabilities are progressively discoverable. | Owner's explicit experience philosophy. |
| D-004 | CONFIRMED | Aim for highly interactive, useful, premium quality. | Owner's product quality goal. |
| D-005 | CONFIRMED | Broad ambition across novels, comics and other forms of written content. | Owner's product concept; specific supported formats TBD. |
| D-006 | PROPOSED | Use separate prose/visual/document reading modes. | Content behavior differs; validate with prototypes. |
| D-007 | PROPOSED | First import path should use user-owned, public-domain or licensed content. | Legal/release feasibility. |
| D-008 | OPEN | Initial OS targets, deployment approach and shared code strategy. | No platform commitment yet. |
| D-009 | OPEN | Reader engines and first supported file formats. | Requires technical research. |
| D-010 | OPEN | Detailed design system and cinematic motion style. | Await user review of mockups. |
| D-011 | OPEN | Pricing, business model, sync/server budget and AI features. | Needs evidence and owner agreement. |
| D-012 | PROPOSED | Story / Visual / Study format-aware experiences under one library. | Competitor overlap means differentiated execution, not raw feature count. |
| D-013 | PROPOSED | EPUB, PDF and CBZ as the first feasibility test samples. | Covers prose, documents and sequential art without promising release support. |
| D-014 | PROPOSED | Prototype web-based interactions plus isolated Readium Kotlin spike before stack choice. | Supports laptop/tablet evaluation, preserves optional native paths. |
| D-015 | PROPOSED | Present three styles: Quiet Editorial, Cinematic Library and Modern Atelier. | Enables owner choice before visual system is locked. |
| D-016 | CONFIRMED | Keep the design-review gate before production implementation; approved prototypes may be used for isolated experiments. | Preproduction workflow and visual quality gate. |
| D-017 | CONFIRMED | Modern Atelier is the selected aesthetic baseline; FOLILUNE v2 opened and was accepted as a good working prototype. | Direct user feedback; this does not lock every screen or component. |
| D-018 | PROPOSED / IN TEST | Explore offline local imports for DRM-free EPUB (text extraction only), TXT and Markdown in an isolated reader proof. | An experimental v3 HTML proof exists; production support not yet approved or verified. |
| D-019 | OPEN | Select a mature EPUB rendering engine and verify per-book persistence on actual Windows/Android tablet devices. | Initial parser is explicitly experimental and automated restart-storage test unavailable. |
| D-020 | EXPERIMENT IN TEST | Modern Atelier v4 now uses block-based reading anchors and limited EPUB formatting in its standalone prototype. | Browser-level rendering passed; storage across restart and standard-compliant reader engine are not verified. |

| D-021 | PROPOSED / IN TEST | Add optional, versioned, local backup/restore for imported books and reader-owned notes to the future product. | v5 recovery prototype passed browser smoke tests, real-device persistence and durability still need validation. |
| D-022 | OPEN | Choose EPUB engine only after cross-platform fidelity, security and location-preservation spikes. | Readium Web normally needs publication server; Readium Kotlin Android-specific; browser libraries need evaluation. |

| D-023 | EXPERIMENT VERIFIED | v6 explicitly labels DRM-free EPUB imports as **text previews** and allows a reader to expand detected fidelity limitations inside Reading settings. | Prevent silent omission of book images/links/layout; detection is heuristic, not full conformance. |
| D-024 | BUG FIX VERIFIED | Backup/restore retains paragraph `fraction` anchors (while accepting legacy `offset`). | v5 serialized the wrong field; v6 export–restore roundtrip passed at laptop/tablet/phone widths. |
| D-025 | OPEN | Real browser-restart IndexedDB tests and maintained EPUB renderer integration. | Sandbox blocks file:// and localhost navigation; require real-device and engine feasibility tests. |

| D-026 | EXPERIMENT VERIFIED | Modern Atelier v7 supports up to 12 locally packaged raster EPUB images within strict import budgets and preserves accepted inline images through backups. | Headless Chromium passed EPUB image decode, restore, and unsafe URL rejection at laptop/tablet/phone sizes. |
| D-027 | OPEN | Browser-restart durability and a standards-compliant production EPUB engine. | Local-file navigation remains blocked in the test environment; v7 is not a release-grade parser. |

| D-029 | VERIFIED BASELINE | Keep v7 Modern Atelier unchanged while using four original EPUB fixtures at three viewport sizes as the comparative regression baseline. | 12/12 browser smoke checks pass; this does not assert EPUB conformance, complex RTL support, or browser restart persistence. |
| D-030 | OPEN / BLOCKED | Runtime integrations of EPUB.js, Readium and Foliate remain unverified because third-party package downloads were unavailable in the test environment. | No engine falsely advertised as integrated; evaluate in a dependency-enabled setup before adoption. |
| D-028 | PROPOSED | Introduce a reader-engine adapter contract that isolates UI, content storage and reader location semantics. | Keep Modern Atelier UX stable and enable measured engine swaps. See READER_ENGINE_INTERFACE.md. |

| D-031 | EXPERIMENTAL ADAPTER | Keep v7 as verified reference while evaluating v9 EPUB.js adapter in a separate HTML experiment. | v9 shell/offline errors and mocked API wiring passed 6/6, but actual EPUB.js was not downloaded or executed. |
| D-032 | OPEN / BLOCKED | Do not adopt EPUB.js, assume browser restart persistence, or import confidential EPUBs until real renderer and security validation pass. | Third-party scripts run from pinned CDN; no CSP/SRI review and no actual EPUB engine test in sandbox. |

| D-033 | PROTOTYPE TEST GATE | Provide a v10 real-EPUB.js on-device validation page with a bundled original EPUB and bundled JSZip; keep main v7 untouched. | Structural/UI and explicit failure-state checks pass in three Chromium viewport sizes. Actual external EPUB.js is not downloaded or run in this environment. |
| D-034 | OPEN / BLOCKED | Require real browser assertions, publication fidelity, CFI restore, network isolation and security checks before considering EPUB.js adopted. | A simulated/blocked engine is not acceptable proof of correct publication rendering. |

| D-035 | VERIFIED REAL ENGINE EXPERIMENT | Unmocked EPUB.js 0.3.93 engine passed nine browser assertions in GitHub Actions (run 42968054882), including decoded image, generated CFI, chapter navigation and CFI restoration. | Actual upstream package was installed/executed on CI; not yet a production engine choice. |
| D-036 | EXPERIMENT VERIFIED | Preserve a separate fully offline Modern Atelier-styled v11 renderer trial based on the exact CI-tested EPUB.js/JSZip bundles. | Local Chromium smoke tests at three viewport sizes confirmed real rendering and chapter navigation with no remote scripts. |
| D-037 | OPEN | Browser restart persistence, accessibility, complex EPUB fidelity, security audit, and migration into v7 remain unapproved and unverified. | The validated isolated experiment must not be confused with a complete app or production readiness. |

## Rules
A PROPOSED or OPEN item is not permission to implement it. New product decisions should be added here with date and reasoning. Preserve history if a confirmed decision is superseded.
