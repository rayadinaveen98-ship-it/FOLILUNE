# FOLILUNE — EPUB.js Engine Spike v9
**Date:** 2026-11-11  
**Phase:** 3, production renderer research  
**Status:** ISOLATED INTEGRATION CANDIDATE — **actual EPUB.js runtime NOT verified**  
**Design:** Modern Atelier, unchanged production/prototype baseline v7

## Deliverable

An isolated [EPUB.js trial HTML](../prototypes/epubjs-engine-spike-v9.html) is committed without replacing v7. The conversation also provides a standalone HTML download and **FOLILUNE_Engine_Integration_Candidate_v9.zip**, containing original EPUB fixtures, the unchanged v7 baseline and local mock-interface smoke tests.

## What v9 implements in source

- An explicitly isolated Modern Atelier shell that does not override v7 app data or UI.
- Local .epub selection (60 MB import cap), title/author, TOC and navigational controls.
- Code integrating **EPUB.js 0.3.93** (`ePub`, `book.ready`, `book.loaded.metadata/navigation`, `renderTo`, `rendition.display`, `prev/next`, `relocated`, and `themes`).
- Browser-local EPUB CFI progress checkpoints keyed to source file metadata; reimport of the same book can attempt restore. CFI durability and real migration remain UNVERIFIED.
- Pages/scroll layout selector, warm/night modes, font controls and keyboard arrows.
- EPUB script execution and popups explicitly disabled. No intentional file upload.
- External **JSZip 3.10.1** and EPUB.js scripts are pinned to versioned jsDelivr URLs and downloaded only after the user picks a file.
- Visible static empty state and clear error state when the CDN or engine cannot load, rather than a blank screen.

## What was actually tested (be precise)

| Test | Platform | Outcome | Meaning |
|---|---|---|---|
| v7 regression | Chromium at 1440×900, 960×760, 390×844 | **12/12 pass** | Existing experimental preview unchanged |
| v9 static shell + blocked-CDN error | Same three widths | **3/3 pass** | App explains unavailable runtime without going blank |
| v9 controls wired to **MOCK** EPUB.js-like API | Same three widths | **3/3 pass** | Metadata, TOC, next, font, layout, and CFI-save *wiring* behaves as intended with test double |
| Actual EPUB.js runtime | Not available here | **NOT TESTED** | Browser rendering, CSS, images, actual CFI, security/accessibility are unknown |
| Local file browser persistence after restart | Not possible here | **NOT TESTED** | Opaque-origin test browser disallows localStorage/IndexedDB; mock store is not durability evidence |

Mock code intentionally displays “UI WIRING MOCK — Not real EPUB output”; no upstream engine was loaded for the 3/3 control tests. The fixture test is **not** an EPUB conformance or application security certification.

## Why actual runtime testing was blocked

The test container was unable to resolve `registry.npmjs.org` and could not download CDN packages. We therefore must not label v9 a successful maintained-engine integration. For the initial user test, open the v9 HTML in Chrome/Edge **with Internet access** and use the original EPUB fixtures in the ZIP. The HTML loads pinned CDN scripts (and will display a clear error if they are blocked).

## Security, ownership and limitations

- This is experimental client-side code that downloads and executes third-party scripts; do **not** import private/confidential books for this first test. Packaging packages locally with integrity checks, CSP hardening, dependency review and sandbox proof remain essential.
- EPUB.js documentation states scripted content is disabled by default, but that does not certify the complete import surface against all malicious EPUBs. Do not enable `allowScriptedContent`.
- File name/size/mtime is a provisional checkpoint identity, not a durable book identifier or edition-safe annotation anchor.
- The book will need to be reselected on later browser sessions; this spike stores position only, not full EPUB bytes. Device storage across restarts remains unverified.
- No new PDF/CBZ importer, cloud sync, accounts, monetization, AI, or production engine was added.

## Acceptance gate for choosing an engine

1. Open the **original prose** fixture using actual EPUB.js from the user's laptop, verify TOC and chapter navigation.
2. Open **illustrated** fixture and verify the actual image, styling, caption and rendered page order.
3. Open **complex** fixture; inspect RTL, footnotes, hyperlinks, tables, CSS, publisher layout, and report differences against v7. Failures are expected and must be documented, not hidden.
4. Change font size, navigate and close/reopen the file, reimport the same EPUB, test actual EPUB CFI position recovery.
5. Test in actual Windows Chrome/Edge and Android tablet; log any JS console exceptions and import failures.
6. Establish offline package build, SRI / CSP and content sandbox review before recommending a renderer for production.
7. Only after real runtime passes should we consider adapter integration into the v7 library and a migration plan.

**Decision:** v7 remains the most recently verified reader demo. v9 is a separate, non-production EPUB.js integration candidate awaiting real renderer verification.

## Research references
- [EPUB.js README, documented browser API and sandbox caution](https://github.com/futurepress/epub.js)
- [EPUB.js published npm package](https://www.npmjs.com/package/epubjs)
- [Readium Web architecture](https://github.com/readium/web)
- [Foliate-js status and security warning](https://github.com/johnfactotum/foliate-js)
