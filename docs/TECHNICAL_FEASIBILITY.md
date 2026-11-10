# FOLILUNE — Technical Feasibility Notes
**Status:** Phase 1 research / no stack selected | **Date:** 2026-11-11

## Standards and design constraints
- EPUB 3.3 is a W3C Recommendation; EPUB 3.3 Reading Systems and EPUB Accessibility are the correct compatibility references. [T01]
- Use WCAG 2.2 as a web/accessibility design benchmark for keyboard navigation, focus visibility, contrast, sizing, and reduced-motion affordances; this does not automatically certify conformance. [T02]
- Imported books are untrusted archives/HTML/images/PDFs; isolate parsing, sanitize scriptable content, bound decompression, validate asset paths, and consider resource budgets.

## Engine candidates — capability is version- and platform-specific
| Candidate | Most relevant platform | Verified status | Critical qualification |
| --- | --- | --- | --- |
| Readium Kotlin Toolkit | Native Android/tablets | EPUB2/3 and PDF supported; audiobook and LCP options; CBZ partially implemented per current feature table. [T03] | CBZ needs a separate pilot; do not assume complete manga/CBR support. |
| Readium Swift Toolkit | iPadOS/macOS | EPUB reflow/fixed, PDF and CBZ supported in published feature table; CBR not supported. [T04] | Adds separate native implementation overhead; not a first-platform decision. |
| Readium Web / Thorium Web | Web-based desktop and tablet | EPUB-focused project; web toolkits (TypeScript/Go); BSD-3 source. [T05] | PDF and comics are described as future directions in project's README, so not an all-format solution. |
| epub.js | Browser/Electron/Tauri/PWA style prototype | EPUB browser rendering, pagination, persistence interface; permissive BSD license. [T06] | Verify actual EPUB3 accessibility, fixed layout, RTL, footnotes, annotations and large files with representative tests. |
| PDF.js | Browser/PWA/desktop web shell | Mozilla-backed PDF parser/viewer; Apache 2.0. [T07] | PDF text extraction varies; scanned image PDFs do not automatically provide reliable selectable text. |
| Custom CBZ page renderer | Web/desktop and native | Technically plausible using safe ZIP decode + image display. | Not a ready-made toolkit; panel order, RTL, rotation, oversize pages and cache eviction require custom engineering. |
| KOReader / Koodo source reuse | Evaluation only | Rich multi-format established apps; AGPL-3.0 license in GitHub repos. [T08] | Do not copy/link source into a closed proprietary app without a licensing assessment. |

## Architecture strategies to compare (UNDECIDED)
### A — Web-first core + responsive app shell
**Strength:** one UI iteration loop across tablet and laptop; easier interactive prototypes; Windows browser/PWA entry. **Risks:** offline file persistence, local filesystem capabilities, e-ink, browser fragmentation, large book performance, deep stylus input. A native wrapper may be needed later.

### B — Native Android-first with separate desktop client
**Strength:** Android tablet file management, OS integration, native ergonomics; Readium Kotlin existing EPUB support. **Risks:** duplicating reading engines and UI effort for Windows; more costly for small team.

### C — Hybrid shared application interface with platform-specific reader adapters
**Strength:** unified experience design while preserving best rendering libraries. **Risks:** adapter complexity, consistency and synchronized anchor formats across engines.

**Research recommendation:** first prototype A for interaction validation on laptop + Android tablet; in parallel, do one isolated Readium Kotlin EPUB spike as a reality check. This is not an architectural commitment and does not imply a deployable product.

## The hidden hard problems (prioritize tests)
1. **Reflow anchoring:** save a chapter/CFI/content reference robustly when font size, device width or edition changes.
2. **Annotation anchoring:** quote+position+context fallbacks and migration across renderer changes.
3. **Offline-first persistence:** imported file safety, metadata integrity, backup and restore.
4. **Sync semantics:** conflicting positions, offline edits, duplicate editions and irreversible highlight deletion.
5. **PDF:** continuous vs paginated, high zoom, text-layer vs scanned PDF, notes across pages.
6. **Comics:** double spreads, right-to-left, webtoon vertical scroll, arbitrary image sizes, panel detection uncertainty.
7. **Unicode/internationalization:** Telugu, Tamil, Malayalam, Kannada, Hindi, CJK, Japanese vertical text, RTL scripts; rendering depends on fonts, EPUB/PDF source encoding and engines.
8. **Accessibility:** keyboard-only operation, screen-reader semantics for reflow and images, low motion, high contrast and alternative text.
9. **Security/privacy:** untrusted imports, safe cleanup, no automatic sending of private publications to external LLMs.
10. **Cost:** cloud storage/egress and AI inference are variable operating costs; start with no required paid service for reading.

## Technical feasibility experiments
| Spike | Platform | Success condition |
| --- | --- | --- |
| EPUB layout | Browser + Android tablet | A public-domain EPUB renders legibly in multiple sizes/themes/widths and restores position. |
| EPUB vs PDF continuity | Laptop browser | Navigation and annotations do not claim identical anchoring when formats differ. |
| CBZ rendering | Android tablet + laptop browser | Reader supports portrait, landscape, spreads, RTL and vertical scroll without page order errors. |
| Large file import | Both | Bounded memory, visibly recoverable errors, no silently lost library state. |
| Offline session | Both | Imported sample remains usable after network disabled and process restart. |
| Accessibility pass | Both | Keyboard navigation, focus, scalable text and reduced motion behavior tested. |

## Important IP/legal distinction
Engine license != content license. BSD/Apache toolkits may permit commercial use with notices, while copyrighted books and comic art still need authorization to distribute. DRM compatibility is a distinct licensing/integration matter and should not be confused with DRM circumvention.

## Sources (accessed November 11, 2026)
[T01] W3C EPUB overview — https://www.w3.org/publishing/epub/ ; EPUB 3.3 — https://www.w3.org/TR/epub-33/
[T02] W3C WCAG 2.2 — https://www.w3.org/TR/wcag/
[T03] Readium Kotlin capabilities — https://readium.org/kotlin-toolkit/latest/
[T04] Readium Swift features — https://github.com/readium/swift-toolkit
[T05] Readium Web project and BSD-3 license — https://github.com/readium/web
[T06] epub.js repository and license — https://github.com/futurepress/epub.js
[T07] Mozilla PDF.js repository and Apache license — https://github.com/mozilla/pdf.js
[T08] KOReader — https://github.com/koreader/koreader ; Koodo Reader — https://github.com/koodo-reader/koodo-reader
