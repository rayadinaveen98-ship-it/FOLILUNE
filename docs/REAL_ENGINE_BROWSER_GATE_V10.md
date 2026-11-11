# FOLILUNE — v10 Real EPUB.js Browser Verification Gate
**Date:** 2026-11-11
**Phase:** 3, EPUB renderer feasibility
**Result:** v10 test harness built; **actual EPUB.js runtime remains unverified in the restricted execution environment**.
**Approved visual baseline:** Modern Atelier v7 (unchanged).

## Concrete deliverables in this conversation
- `FOLILUNE_Real_EPUB_Engine_Test_v10.html` — a standalone Modern Atelier engine test page
- `FOLILUNE_v10_Original_Engine_Test.epub` — original, two-chapter EPUB3 publication with proper navigation, local CSS, local illustration and real XHTML
- `FOLILUNE_Real_Engine_Test_Kit_v10.zip` — HTML, EPUB, browser smoke-test results and instructions

**Important:** These v10 files are distributed as conversation download artifacts. They are **not yet committed to this GitHub repo, nor deployed to a public website**. Do not invent a GitHub source link or claim a live deployment.

## What differs from v9
- A `Run real EPUB test` action generates a local File object for the original EPUB and passes it through the **actual**, non-mocked EPUB.js import path.
- The diagnostic panel marks separately: engine loaded, metadata, TOC/chapter navigation, rendered image/typography, CFI checkpoint, and restart persistence.
- Green PASS is reserved for browser-observed assertions. FAIL means error; MANUAL is visual/unobservable; PENDING requires additional action.
- The HTML bundles **actual JSZip v3.10.1** (MIT license notice included), reducing the CDN dependencies to EPUB.js 0.3.93.
- The test intentionally fails visibly when the EPUB.js dependency cannot load; it does not synthesize a success result or render fake book content.

## Tests actually performed in the restricted execution environment
- Source scripts passed `node --check`.
- Original EPUB ZIP passed corruption checks; its XML, XHTML and OPF parsed successfully.
- JSZip executed inside real headless Chromium.
- Desktop (1440×900), tablet (960×760), mobile (390×844) test cases: structural shell rendered; test controls visible; zero page JavaScript exceptions; no viewport-wide horizontal overflow; blocked CDN produced a visible engine failure and **no false passing tests**. All 3 smoke-test runs passed.
- The test screenshot shows the expected **failure** in the network-restricted browser, not a successful EPUB.js render.

## Not validated; still blocking a production choice
- External EPUB.js 0.3.93 package execution and actual content rendition on the user's computer
- True image fidelity, CSS, EPUB navigation links, CFI relocation, and position-restoration across restart
- Cross-browser / Android tablet reliability and accessibility
- Threat-model audit: third-party scripts, EPUB embedded network resources, XSS/iframe sandbox behavior, CSP/SRI, ZIP bombs
- Full EPUB3 compliance, Indic/RTL/vertical writing, fixed-layout media

## Safe user acceptance protocol
1. Extract the v10 kit to Windows desktop.
2. Open the HTML file in Chrome or Edge **with Internet access**.
3. Click **Run real EPUB test** and inspect individual status labels; they may be PASS/FAIL/MANUAL/PENDING.
4. Visually confirm the original illustration and `The House of Pages`.
5. Choose the second TOC entry `The Garden Beyond`, verify real chapter-two text.
6. Reopen the HTML in the same browser and run again. A successful storage checkpoint is **not equivalent** to full CFI restoration.
7. Do not import confidential publications into this experiment.

## Engine choice
No renderer was selected. v7 remains the current browser-tested visual reader. v9 remains an isolated adapter candidate; v10 makes real engine testing practical without requiring the owner to set up npm. Any real runtime failure should be recorded and fixed before integration.

## References
- [EPUB.js source and API](https://github.com/futurepress/epub.js)
- [EPUB.js 0.3.93 npm package](https://www.npmjs.com/package/epubjs)
- [JSZip license](https://github.com/Stuk/jszip/blob/main/LICENSE.markdown)
- [v9 engineering checkpoint](ENGINE_SPIKE_V9.md)
