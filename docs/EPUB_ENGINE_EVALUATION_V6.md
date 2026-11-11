# FOLILUNE — EPUB Rendering Evaluation and v6 Integrity Gate
**Date:** 11 November 2026  
**Status:** Phase 3 research + verified prototype experiment; *no production engine selected*  
**Design:** Modern Atelier, unchanged as requested

## Purpose and product decision
FOLILUNE's v5 standalone reader is a valuable HTML proof, not a standards-compliant EPUB engine. It flattens or discards complex publishing features. Rather than hiding that, v6 identifies likely fidelity losses for each imported EPUB and fixes a backup-position serialization defect before a larger engine integration.

## Engine options — evaluated from official documentation
| Engine | Primary target | Confirmed strengths | Integration and product limitations | Provisional use |
| --- | --- | --- | --- | --- |
| Readium Kotlin Toolkit | Native Android tablet/ChromeOS | EPUB2/3, EPUB reflow and fixed layout, PDF, RTL, search, annotations/decorations, TTS as documented [E1] | Different implementation for laptop browser/desktop; custom library UX required | Primary Android *feasibility spike*, not approved stack |
| Readium Web (TS + Go / Thorium Web) | Web-hosted tablet/laptop | EPUB web reader architecture with publication/resource streaming [E2] | Not plug-and-play for a self-contained offline HTML file; Go/API and HTTPS design need investigation | Promising long-term web architecture research |
| EPUB.js 0.3.x | Browser EPUB renderer | Pagination, rendition API, hooks, EPUB CFI/locations; BSD-2-Clause [E3] | Current npm stable 0.3.93 is older; must test security, accessibility, large files, RTL, fixed layout and bundled/local behavior [E4] | Focused browser benchmark candidate |
| Foliate-js | Modern browser module option | EPUB, CBZ, other ebook formats, modular text/graphics renderer; MIT with transitive licenses [E5] | Upstream says library is not stable and has no release; API may change [E5] | Secondary experimental benchmark |

**Decision: DEFERRED.** No candidate has been integrated into FOLILUNE or validated against the full fixture matrix. Do not imply the production reader is engine-backed yet.

## v6 implemented in the prototype
- **Content fidelity reporting:** an imported EPUB is explicitly labeled a **text preview**. Only inside Reader Settings, the reader can expand **EPUB text-preview limitations** to see detected categories of unsupported or altered content.
- Detection covers illustrations, CSS/fonts, hyperlinks, notes/footnotes, tables, fixed layout, RTL/vertical indications, and media/scripts.
- Counts are approximate heuristics from EPUB manifest and XHTML; **not** authoritative EPUB conformance checks. The message clearly states that undetected issues may remain.
- **Restore bug fix:** reading anchors in live state use `{block,fraction}`; v5's backup parser had silently converted them to `{block,offset}`. v6 normalizes `fraction`, accepts legacy `offset` for backward compatibility, and preserves anchored position through backup/restore.
- Fidelity metadata survives v6's export/restore, but older backups without it remain importable.
- The v5 progressive disclosure, themes, Story/Comic/Study sample, file import and backup remain in place. No new screen, content marketplace, AI service, PDF import or backend was added.

## Verification performed
- JavaScript syntax: `node --check` passed.
- Chromium smoke tests: **1440×900, 960×760, and 390×844**.
- An **original two-chapter EPUB** fixture contains an embedded illustration, CSS file, internal link, footnote, table, RTL indication, and bold/italic text. Import succeeded at all three widths.
- The settings panel identified all six expected fidelity categories (images, styles, links, footnotes, tables, RTL).
- Safe rich text (**strong**, **em**) displayed; image markup did **not** silently appear as a broken image.
- Chapter-next, bookmark, Library backup download and restore into a fresh page passed.
- Backup restore preserved exact test anchor `{block:1,fraction:0.375}` through export.
- JavaScript exceptions: zero. Viewport-wide horizontal overflow: none in tested flows.
- The browser sandbox **blocks both `file://` and `127.0.0.1` / `localhost` navigation**; real local-origin IndexedDB persistence over browser restarts **could not** be measured here. Only per-session and exported-backup recovery were tested.
- v6 visuals are still a prototype, not a comprehensive design audit or accessibility certification.

## User validation
1. Download [Modern Atelier Reader Proof v6](../prototypes/modern-atelier-reader-proof-v6.html) and the original EPUB format-fidelity test book attached to the ChatGPT conversation.
2. Open the HTML in Chrome/Edge (not the in-app preview); go Library → Add a book → select EPUB.
3. Open **Aa Reading settings** → scroll down to **Reading accuracy** → expand **EPUB text-preview limitations**.
4. Verify the report mentions missing images, CSS, links, tables, notes and RTL.
5. Bookmark chapter 2; return to Library → Back up or restore your library → export JSON; restore it and verify chapter/reading-position continuity.
6. Test on an actual laptop and Android tablet. Confirm book persistence after closing and reopening browser. Report exact error, screenshots and reproduction steps if it fails.

## Remaining release gates
- Run hands-on engines against identical licensed EPUB fixtures (illustrated, prose, complex layout, RTL/Indic script, long documents).
- Choose a standards-compliant engine with architecture and license decision recorded before production import release.
- Conformance, accessibility, performance, annotation CFI anchoring and security testing remain mandatory.
- No complete EPUB/PDF/CBZ support is promised by v6.

## Research sources
[E1] https://readium.org/kotlin-toolkit/latest/  
[E2] https://readium.org/web/ and https://github.com/readium/web  
[E3] https://github.com/futurepress/epub.js  
[E4] https://www.npmjs.com/package/epubjs  
[E5] https://github.com/johnfactotum/foliate-js
