# FOLILUNE — EPUB Reading Engine Evaluation

**Date:** 2026-11-11  
**Stage:** Phase 3 technical discovery  
**Decision status:** PROPOSED. No production rendering engine selected.

## Key decision

Do not swap the offline experimental parser for an untested library and label it production-ready. FOLILUNE needs high-fidelity EPUB rendering and stable locations on **Windows/laptop** and **Android tablet**; a common UI does not guarantee a single compatible engine.

## Candidate evidence (official vendor/project docs, checked 2026-11-11)

| Candidate | Known strengths | Key issue for our current standalone HTML test | Provisional fit |
| --- | --- | --- | --- |
| Readium Web / Thorium Web | Readium TS toolkit and production Thorium Web offer rich publication rendering; publication server/manifest architecture | Readium Web TS generally consumes Web Publications prepared by an HTTP service; not a drop-in local `.epub` parser for an offline file URL. | Architectural study for a future hosted/desktop service-backed app |
| Readium Kotlin | Implemented EPUB2/3 reflow/fixed layout, locations, search, highlights, TTS and PDF per feature matrix | Native Android only; Windows requires a separate engine/UI integration; CBZ partial. | Strong Android candidate for a dedicated feasibility spike |
| EPUB.js 0.3.93 | Browser EPUB rendering, pagination, CFI APIs; BSD-2 license; broad adoption | Mature but older releases; require tests for RTL, fixed layout, embedded CSS, sanitization and offline/file-origin behavior. | Candidate for isolated web reader fidelity experiment |
| Foliate-js 1.0.1 | EPUB, CBZ, MOBI/KF8 and modular browser renderer; MIT license | The project explicitly warns its library API is unstable and that book scripts must be blocked with strict CSP. | Investigate later; do not integrate sight-unseen |
| Existing FOLILUNE parser v4-v5 | No third-party runtime required; tightly controlled text-only extraction and simple formatting | Fails EPUB fidelity: publisher layout, images, footnotes, CSS, vertical text, media overlays, semantic links; limited position anchors. | Prototype fallback ONLY, not production |

## Recommended engineering path

1. Keep existing Modern Atelier user experience as an **independent application shell**.
2. Define a reader adapter interface for open/close, locations, chapter navigation, settings and selections.
3. Conduct separate, measurable technology spikes, using the *same legally owned EPUB fixtures*, in **EPUB.js (browser)** and **Readium Kotlin (Android)**. Consider Foliate-js after a security/maintenance review.
4. Measure: content fidelity, CFI/position restoration across font changes, image rendering, TOC/link behavior, memory, cold-open latency, offline storage, keyboard/stylus accessibility and Unicode/RTL.
5. Select stack **only after** real laptop and tablet results. Avoid forcing the same engine on unlike platforms.

## Architecture boundary to maintain

A portable app-level `ReadingLocation` record can include `bookId`, `chapter`, `fraction`, `engineType`, `engineLocator`, `textQuote` and `savedAt`; conversion between engines must be explicitly tested and may be lossy. **Never claim universal or exact cross-engine location compatibility based solely on a scroll fraction.**

Local import must remain a user-controlled flow. Book files and annotations should not be sent to AI/cloud services without permission.

## Budget and availability

No paid API/server is required for the current offline prototype. A future Readium Web publication server would introduce hosting and data-transfer costs. Do not silently commit to a paid service or user account.

## Official references

- https://readium.org/web/
- https://www.edrlab.org/software/readium-web/
- https://readium.org/kotlin-toolkit/latest/
- https://www.npmjs.com/package/epubjs
- https://github.com/johnfactotum/foliate-js
- https://www.w3.org/TR/epub-33/
- https://www.w3.org/TR/WCAG22/

## Status conclusion

**No engine promoted to production.** FOLILUNE can continue reader UX and recovery experiments without sacrificing the approved Modern Atelier design, while dedicated rendering-engine validation proceeds.
