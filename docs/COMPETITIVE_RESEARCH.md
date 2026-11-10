# FOLILUNE — Competitive Research & Positioning
**Version:** Phase 1 / v0.1 | **Date:** 2026-11-11 | **Status:** research, NOT a product decision
**Method:** Official product documentation and a small number of public reader reports; no hands-on competitor usability testing yet. Feature claims are attributed to published sources. Gaps below are *opportunities to investigate*, not proven weaknesses.

## Executive finding
The category is already well-served for EPUB/PDF/CBZ, synchronization, customization, TTS, notes, and panel navigation. FOLILUNE cannot distinguish itself by claiming to combine all these features for the first time. Its proposed differentiator is one calm, format-aware library with deeply crafted experiences for prose, sequential art, and study that reveal functionality in context.

## Competitor evidence
| Product | Verified strengths (publisher claims) | Implication / hypothesis for FOLILUNE |
| --- | --- | --- |
| Readest | Desktop/mobile/web; EPUB, PDF, CBZ etc.; parallel reading; translation; TTS; sync. [R01] | Simply offering split reading or TTS is table stakes; inspect how contextual tools are surfaced. |
| BookFusion | Web, iOS, Android; books/articles/comics; library organization, annotation export, Calibre/Obsidian, sync. [R02] | Strong reference for large-library continuity; test whether deep library tools can be exposed more progressively. |
| Koodo Reader | Extensive book/document/comic formats; multi-platform app. [R03] | Broad format support has cost and reliability implications; avoid promising 'all formats' without a test matrix. |
| KOReader | Multi-format configurable reading; PDF reflow; e-ink optimization; plugin/Calibre ecosystem. [R04] | Power users value controls and openness; our challenge is discoverability and presentation. |
| Panels | Comic-focused CBZ/CBR/PDF/EPUB; guided panel view; manga/webtoon orientation; synced comic libraries. [R05] | Panel viewing is not novel; learn from visual storytelling without assuming flawless auto-detection. |
| YACReader | Mature desktop comic library, manga, webtoon scrolling, panel view on mobile, metadata/search/remote library. [R06] | Collection management and file-heavy edge cases deserve real engineering weight. |
| LiquidText | PDF/study-focused excerpt linking, annotation and spatial workspace on tablets/desktops. [R07] | Study workspace is established; its complexity should be accessible *only when requested*. |

## Reader conversation: tentative signals, NOT representative research
- Some participants report missing or inconsistent reading-position/annotation syncing between third-party devices/plugins; problems vary by format, version and setup. [R13] **Implication:** sync conflict handling and restore/rollback deserve testing, but do not label any entire competing product unreliable.
- A reader sought to browse a remote comic collection without downloading every title. [R14] **Implication:** catalog versus offline-file distinction is worth prototyping.
- A reader seeking free guided comic reading raised concerns about paid-tier boundaries. [R15] **Implication:** clearly disclose which functions would be paid if FOLILUNE later uses monetization.
These are anecdotes, not market-size evidence.

## Jobs-to-be-done to validate
1. 'I want to start or resume reading immediately, even if I left the app abruptly.'
2. 'I read fiction to escape; please do not interrupt my immersion to promote tools.'
3. 'I want my manga/comic to respect its panels, art, and right-to-left or vertical flow.'
4. 'I need to take notes while reading a paper without switching across many windows.'
5. 'I want my personal books and annotations to remain under my control and accessible offline.'
6. 'I want important tools to be discoverable when necessary, not permanently displayed.'

## Positioning hypothesis
**A world between pages**: an inclusive, deeply crafted personal reading environment, especially compelling on tablets and laptops. One library, multiple reading grammars, personalized depth; no pressure to become a social platform.

## Experience opportunity scoring (qualitative, to validate)
| Opportunity | User value hypothesis | Differentiation potential | Technical risk | Provisional priority |
| --- | --- | --- | --- | --- |
| Fast resume and dependable positions | Very high | Medium | Medium | P0 |
| Beautiful prose typography + low-friction controls | Very high | Medium | Medium | P0 |
| Format-aware comic navigation (incl RTL/scroll) | Very high for comic readers | Medium | Medium/high | P0 prototype |
| Contextual action layer on text selection | High | Medium | Medium | P1 |
| Discreet and genuinely useful study workspace | High for students | Medium/high | High | P1 prototype |
| Portable annotations with verifiable anchoring | High | Medium/high | High | P1 |
| Cross-device cloud sync | High | Low as a *feature*, high as trust | High + recurring cost | P2 decision |
| AI assistant / automatic character maps | Unverified | Potentially high | Very high + rights/privacy | Research only |
| Rich 3D motion / animated page turning | Unverified | Low | Performance + accessibility cost | Avoid as core differentiator |
| Content marketplace and social network | Unverified | Unclear | Legal/operational very high | Out of initial scope |

Priority tags are internal research priorities, not release commitments.

## Direct competition to avoid
- Claiming we invented split reading, cloud libraries, TTS, or panel-by-panel comics.
- Shipping a beautiful but illegible page or flashy animations that slow reading.
- Claiming unlimited support for copyrighted book catalogs, third-party DRM or publisher artwork without rights.
- Building a generic 'one giant reader' that compromises both prose reflow and graphic-art fidelity.

## Research limitations
- Official documentation validates *advertised* features, not real-world performance or UX quality.
- No side-by-side product screenshots, instrumentation, formal user survey, or pricing cohort analysis yet.
- The competitive landscape changes rapidly; revisit before technical and business decisions.
- FOLILUNE's initial OS targets, monetization and synchronized cloud solution are NOT decided.

## Sources (accessed November 11, 2026)
[R01] Readest Docs — https://readest.com/docs
[R02] BookFusion Features — https://www.bookfusion.com/features
[R03] Koodo Reader GitHub — https://github.com/koodo-reader/koodo-reader
[R04] KOReader GitHub — https://github.com/koreader/koreader
[R05] Panels — https://www.panels.app/
[R06] YACReader Features — https://www.yacreader.com/features
[R07] LiquidText App — https://apps.apple.com/in/app/liquidtext/id922765270
[R13] Reader reports, sync issues — https://www.reddit.com/r/readest/comments/1x8z3o0/sync_issues/
[R14] Reader request, remote comic collection — https://www.reddit.com/r/comicbooks/comments/1vecvzy/looking_for_ipad_comic_book_reader_where_i_can/
[R15] Reader question, free guided view — https://www.reddit.com/r/ipad/comments/1u9uxib/is_there_a_free_app_for_reading_graphic/

## Next study
Run hands-on observation on a Windows laptop and Android tablet, using the **same legal sample EPUB, PDF and CBZ**, then record: time-to-read, number of steps to reach tools, page turn and reflow quality, RTL and image handling, search usability and error recovery. No ratings without observations.
