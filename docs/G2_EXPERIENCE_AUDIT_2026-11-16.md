# FOLILUNE Reader — G2 Experience & Engineering Audit
**Date:** 2026-11-16
**Scope:** Android experimental G2, not FOLILUNE-STUDIO.
**Branch:** `experiment/vnext-g2-live-surfaces` — [draft PR #7](https://github.com/rayadinaveen98-ship-it/FOLILUNE/pull/7) into experimental G1, **not main**.
**Design decision:** **APPROVED — Lunar Editorial**, evolving Modern Atelier. Final icon master, token values, animation and individual screens remain subject to visual review.

## Evidence and limits
- Starting verified code: `03b91a4a24e19c2be21f4a3066ca8bf1cd2057c3`. Three successful Android CI workflows at that SHA: [integrated real EPUB/CFI](https://github.com/rayadinaveen98-ship-it/FOLILUNE/actions/runs/43270985130), [APK build](https://github.com/rayadinaveen98-ship-it/FOLILUNE/actions/runs/43270985299), [independent WebView durability](https://github.com/rayadinaveen98-ship-it/FOLILUNE/actions/runs/43270985397).
- Reviewed source: `android-app/app/src/main/assets/index.html`, `vnext-integrated.js/css`, `vnext-g2.js/css`, `vnext-g2-support.js/css`, `MainActivity.java`, `AndroidManifest.xml`, instrumentation test, emulator QA script, README, ROADMAP and DECISIONS.
- **NOT TESTED IN THIS AUDIT:** physical tablet, real-user usability, actual keyboard/trackpad, 200% system text, TalkBack, RTL/Indic/fixed-layout corpus, 1,000-book performance, security penetration testing, production signing, app upgrade/migration. Source inspection is not device verification.
- **Data warning:** v12 package `com.folilune.tablet` and experimental `com.folilune.vnextreader` have separate storage. Android manifest currently has `allowBackup=false`; WebView download listener does not provide a working Android backup download. Keep original EPUBs; do not uninstall/clear data or assume debug signing continuity.

## Source-backed findings by journey
| Journey / surface | Status | Evidence / gap | Priority |
| --- | --- | --- | --- |
| App icon / launcher | IMPLEMENTED — provisional | Android vector is literal crescent + open book, not a reviewed Folded Moon adaptive master. Launcher name and Gradle version still say G1. | P1 |
| Splash / motion | PARTIAL | G1 branded entry animates ~420 ms; reduced-motion CSS exists. Native launch handoff, first-run vs return motion and system animator-scale behavior not accepted. | P1 |
| Welcome / onboarding | PARTIAL | G1 first-run welcome and reader-size/theme choices exist; not yet the approved three-decision import-first flow, and device testing is absent. | P1 |
| Permissions / import | IMPLEMENTED — emulator tested | Native Android document picker; local EPUB/TXT/Markdown import. File corruption, provider variety, cancellation and large-file UX need corpus. | P0/P1 |
| Empty Home | IMPLEMENTED — needs visual acceptance | Honest import call to action, samples labeled. Artwork, density and accessibility need device review. | P1 |
| Populated Home / continue | IMPLEMENTED — needs validation | Real imported shelf and last-opened state, but long-title, missing-cover and large-library states unverified. | P1 |
| Library / search | IMPLEMENTED — partial | Real metadata filtering/sorting; search does not index full EPUB text or annotations. Sample books remain separated. | P1 |
| Saved Places | IMPLEMENTED — emulator baseline | Real EPUB CFI bookmark rows and text-preview chapter bookmarks. Add exact-CFI navigation/recovery regression; manual location management needs UX review. | P0 |
| Collections | PARTIAL | Automatic shelves, not editable user-created persistent collections. | P1 for G3 |
| Notes / highlights | PARTIAL | Existing preview/study notes are shown; no native source-linked full EPUB annotations. | P1 for G3 |
| Reader / typography | IMPLEMENTED — limited | Real EPUB.js 0.3.93 + JSZip, paper/sepia/night and size/line height. Missing robust publisher/RTL/Indic/fixed-layout and accessibility acceptance. | P0/P1 |
| Reading position durability | IMPLEMENTED — experimental | IndexedDB journal mirrors localStorage state; earlier intermittent CFI failure was remediated; current stress coverage still insufficient. | P0 |
| Settings / privacy | PARTIAL | Real tone/size toggles and truthful no-sync messaging; no local export/restore path in Android; System mode and advanced typography not delivered. | P0/P1 |
| Help / errors | PARTIAL | Help FAQs exist; import and renderer errors present, but offline/low-storage/recovery/migration UX not designed as a complete state system. | P1 |
| Tablet / keyboard / screen reader | NOT VERIFIED | Layout media queries exist; no physical-device, split-screen, 200% text, TalkBack or keyboard-only signoff. | P0 |
| Release / migration | BLOCKED | Draft experimental PRs, no v12 migration, production signing or beta. | P0 |

## G2 code integrity defect — addressed, verification pending
**Finding:** `removeImported(id)` previously deleted a book's `readingPositions` and preview bookmarks but **did not clear `epubBookmarks[id]`**, `epubModes[id]` or `lastOpened[id]`. A later import with the same fingerprint could inherit old locations. If removal occurred while a full EPUB rendition was active, `render()` called `storeEpubCheckpoint(epubRuntime)` before disposal and could **re-create the removed position**.

**Patch:** Before clearing the book's state, dispose the active renderer if it belongs to the deleted book. Delete the book's EPUB CFI bookmarks, preview bookmarks, reading position, EPUB mode and last-opened record, along with existing note/highlight cleanup. No storage schema change or new product feature.

**Regression tests added to `ReaderDurabilityTest.java` and `run-emulator-qa.sh`:**
1. Existing real EPUB import and exact CFI bookmark commit.
2. Existing force-stop and exact CFI restoration.
3. Deliberately make localStorage stale while IndexedDB checkpoint is intact.
4. Force-stop, recover journal, verify one imported entry and navigate from Saved Places to exact CFI.
5. Remove the imported book while its full EPUB renderer is active; verify local state, IndexedDB book deletion and committed checkpoint cleanup.
6. Force-stop and verify no deleted book/bookmark reappears.
7. Capture emulator screenshot.

**Verification state (2026-11-16):** Data-deletion fix committed as `3bac6a5691bc5489aa5bcb44f90ad5b2aa581f9b`. At `d2ad0d4`, both the independent durability workflow [43830285622](https://github.com/rayadinaveen98-ship-it/FOLILUNE/actions/runs/43830285622) and a prior run [43830137359](https://github.com/rayadinaveen98-ship-it/FOLILUNE/actions/runs/43830137359) failed at the Saved Places assertion after font-size reflow; stage 1–3 passed. An integrated run [43830285390](https://github.com/rayadinaveen98-ship-it/FOLILUNE/actions/runs/43830285390) passed the full seven-stage pipeline at the same head, identifying an intermittent and overly strict exact-page-start assertion. The test at `9c4987c` now captures the exact requested `rendition.display(CFI)` target, verifies the correct chapter and retained bookmark after reflow, and preserves the unchanged-viewport exact-CFI restart assertion. At **`9c4987c`**, all three workflows passed: [APK build 43833983577](https://github.com/rayadinaveen98-ship-it/FOLILUNE/actions/runs/43833983577), [independent durability 43833983425](https://github.com/rayadinaveen98-ship-it/FOLILUNE/actions/runs/43833983425), [integrated EPUB/CFI 43833983443](https://github.com/rayadinaveen98-ship-it/FOLILUNE/actions/runs/43833983443). **This proves the corrected emulator test contracts at that exact SHA only**; reflow position on real hardware, rapid-activity stress, physical tablet, accessibility and migration remain open.

## Lunar Editorial design acceptance — APPROVED direction, PROPOSED details
**Creative principle:** Paper Modernist grid discipline + Lunar Editorial warmth. Reading content is the hero; expressive moments are quiet, meaningful and skippable.

**Proposed token families (do not silently overwrite approved screens):**
- Surface: warm paper / ivory / cool mist, with Night and Black as accessible reader options; reuse existing `--v-*` variables until reviewed.
- Ink: deep charcoal/green rather than washed-out gray; validate body text contrast in every theme.
- Accent: restrained moss/brass; never use accent as the only state indicator.
- Type: elegant editorial display for headings, highly legible and multilingual-capable body typography; prefer user-controlled reading typefaces.
- Motion: purposeful 150–300 ms component transitions, one-time skippable welcome animation, instant/no-motion fallback; honor OS reduced-motion and animation settings.
- Layout: window-width classes and reader-first adaptive navigation; no mandatory landscape, no overwide reading measure.
- Icon: one folded-page/crescent silhouette that remains legible at launcher sizes; adaptive icon foreground/background and monochrome variant to be designed/reviewed.

**First design review screens:** launcher icon + splash → welcome + reading comfort + import → empty Home → populated Home/Library/Search → real EPUB reader with chrome hidden/shown → saved location → Settings/Help → corrupt-file and storage-error states. Provide actual device captures, not inspiration imagery or fabricated screenshots.

## G2 acceptance checklist (unchecked = not verified)
- [ ] Current branch Android CI passes at the **new** patch SHA.
- [ ] Physical tablet: install without touching v12, import, resume, bookmark, Saved Places, delete, force-stop/restart, portrait/landscape.
- [ ] Design owner accepts icon master, launch behavior, onboarding, Home/Library/Search/Reader/Settings and empty/error states.
- [ ] TalkBack critical tasks and focus order validated on Android device.
- [ ] Maximum Android system font scale and screen zoom do not clip primary actions.
- [ ] Reduced motion, high contrast, touch target, keyboard, mouse and split-screen verified.
- [ ] EPUB corpus: valid, malformed, huge, embedded-font/image, footnote, table, internal link, RTL, Indic, fixed-layout; unsupported content disclosed honestly.
- [ ] Book/CFI/note deletion and repeated restart stress pass without state resurrection or loss.
- [ ] Backup/migration/signing decision explicitly recorded; no destructive testing against existing v12 data.
- [ ] Founder closes G2 only after evidence review; no merge or release by implication.

## Scope discipline and next engineering work
**In scope now:** audit, regression coverage, focused G2 data integrity fixes, physical-tablet acceptance preparation, design tokens and approved screen refinement after review.

**Not authorized by this approval:** G3 native EPUB highlights, custom collections, account/cloud sync, AI, PDF/CBZ engine, monetization, new paid infrastructure, v12 data migration, merging draft PRs, production signing or release.

**Next:** verify the new G2 touch-area CSS against current CI; then test compact/expanded widths, TalkBack, 200% text scaling, portrait/landscape and real-tablet Saved Places. Present Lunar Editorial visuals for owner signoff without rewriting the reader engine. Track P0 data integrity/accessibility before decorative polish.


## 2026-11-16 focused security and accessibility source inspection
**Inspection, not penetration-test certification.** `MainActivity.java` enables JavaScript for EPUB.js but disables direct file access, file-URL-to-file access and universal access from file URLs; disallows mixed content; enables Safe Browsing on supported APIs; blocks remote HTTP(S) resource loads; uses a fixed allowlist for locally bundled UI assets; and denies WebView permission requests. No native `addJavascriptInterface` was found in the inspected activity. These are positive controls, not a guarantee against hostile EPUBs.

**Open risk:** `shouldOverrideUrlLoading` explicitly blocks external HTTP(S) top-level navigation but returns false for other URI schemes; the EPUB pipeline and the WebView's content-URI picker access must be exercised with untrusted/invalid content and disallowed scheme tests before security signoff. No production change has been authorized to this subsystem without a failing reproduction and regression test.

**Visual accessibility:** G2 source styles have 44–45px minimums for some navigation, choice and font stepper controls, less than the 48dp Android target. A narrowly scoped G2 CSS correction moves these hit areas to 48 CSS pixels as a test candidate, with no change to EPUB rendering, stored data or palette. CSS pixels must still be verified against actual Android dp hit areas and TalkBack focus; do not call this fully accessible yet.

**Design system:** The [Lunar Editorial G2 system candidate](LUNAR_EDITORIAL_G2_SYSTEM_CANDIDATE_2026-11-16.md) records existing source values, measured contrast pairs, proposed token grammar, motion and review gates. Direction APPROVED; detailed token values/mark/screens CANDIDATE pending visual acceptance.

## 2026-11-16 G2-C scoped Lunar Editorial first-run and compact Home implementation
**Status:** IMPLEMENTED in the experimental G2 branch; visual acceptance and physical-device validation remain OPEN.

**Source-backed findings:** The earlier welcome had no direct import action; Skip incorrectly claimed to open Library but simply closed the welcome. The badge still said G1, and empty Home repeated the import CTA while a tall hero pushed the local library below the fold.

**Changes, limited to G2 presentation:** A three-decision first-run flow (understand local-first promise → choose app tone and reader size → import EPUB/TXT/Markdown or explore first). The import button delegates to the existing `#importBookInput` Android document picker; no permissions, file parser, IndexedDB schema or EPUB.js engine changes. The G2 badge, browser title and experimental launcher label are corrected. Empty Home now has one primary import action, a reduced hero height at compact width and a truthful empty-library explanation; Home/Library top-bar names are shorter. A ninth Android emulator QA stage validates the flow without opening the OS picker.

**Not delivered:** final Folded Moon brand assets, release, G3 features, physical tablet acceptance, TalkBack/200% text validation, final first-run artwork or approval of exact token values. CI must be checked at this exact commit; earlier results do not verify these changes.

## 2026-11-16 first-launch race revealed by genuine emulator capture
**Finding:** The successful independent emulator QA screenshot in run 43840353718 still showed the old v12 Home, even though connected G2 surfaces worked once navigated. The original `awaitApplication()` test checked G2 only *after* navigating through multiple destinations and therefore missed the first-display defect. This is evidence from an actual 1080×2400 emulator capture, not an inferred UI issue.

**Narrow remediation:** Wait for parser-blocking G2 presentation scripts to finish before the initial `loadImports().finally(render)` Home draw. Preserve recovery timing: no navigation, IndexedDB write, import/parser changes or EPUB engine modifications while storage hydration is pending. Strengthen `awaitApplication()` to require the connected G2 Home **before any navigation**. Retest the real EPUB/CFI/deletion suite and inspect a new screenshot before declaring fixed.

## 2026-11-16 G2-D screenshot-guided Home density refinement (candidate)
**Evidence:** Actual Android portrait emulator screenshot of `0ac7048` showed two nearly identical editorial headings, a very large introductory hero, and a second empty-library panel despite one working import CTA. This is a **design-density** finding, not an EPUB engine defect.

**G2-only changes:** Empty Home now has one page H1 and a modest card with a clear primary import CTA, existing folded-moon book illustration and a short DRM-free/local-storage explanation. Removed the repeated hero H2 and redundant shelf-waiting badge. Reduced the empty-only artwork height for narrow windows and replaced the large empty panel with a small non-interactive reading-desk notice. Populated Home still exposes the actual recent imported title and real data-driven Continue button. Existing sample labeling and privacy warning remain. Added an emulator assertion that checks one H1, one import action, no competing H2 and a reading-desk section inside the initial compact viewport.

**Acceptance:** Source implementation is NOT final visual acceptance. Compare new and previous actual emulator captures; validate typography at 200% system scaling, device tablet portrait/landscape and on-device TalkBack. No reader-storage, EPUB parse, migrations, account/sync, or v12 changes authorized or made.

## 2026-11-16 G2-E imported/returning Home truthfulness and first-run visual evidence
**Finding (source inspection):** Previously the Home featured `lastRead(own.filter(b=>b.lastOpened)) || lastRead(own)`. When an EPUB was newly imported but had no `lastOpened` timestamp, Home still claimed to return to a previously visited page and offered `Continue reading`. The claim was not grounded in stored state.

**Remediation (G2 presentation only):** With a real `lastOpened` entry, offer **Continue reading** and the last opened imported title. With a local book but no recorded last-opened time, offer **Open book** and describe it as ready to read. With no local books, keep a single import CTA and truthful empty-shelf message. All featured book actions use the existing `data-book` handler. EPUB engine, IndexedDB, CFI state and import parser remain unchanged.

**CI acceptance extension:** Seed test goes through the actual newly imported and returning Home states, uses the real EPUB.js renderer after each action, and captures screenshots. First-run G2 test captures real Android `UiAutomation` screenshots of introduction, reading comfort and import steps, all using test-only library state. The shell copies captures into the standard QA artifact. This allows *visual inspection* of the exact welcome UI before requesting final founder acceptance. A screenshot is emulator evidence, **not** proof of physical tablet, TalkBack, 200% text or Android file-picker grant behavior.

**Status:** Implemented in experimental G2 branch; new current-head CI required to mark TESTED. No v12, G3 or release changes.
