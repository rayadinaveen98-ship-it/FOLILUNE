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

**Verification state:** Patch committed as `3bac6a5691bc5489aa5bcb44f90ad5b2aa581f9b`. New CI conclusions must be inspected before marking TESTED. A passing emulator run still does not close physical tablet or migration gates.

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

**Next:** inspect CI for `3bac6a5`, fix only demonstrated regressions, request real tablet observations, and present the Lunar Editorial icon + core-flow design for visual signoff. Track P0 data integrity/accessibility before decorative polish.
