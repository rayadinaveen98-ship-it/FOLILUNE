# FOLILUNE vNext — Phase D Supporting Experience
**Date:** 2026-11-11 · **Status:** Review-ready design prototype, not an APK or production implementation.

## Goal
Complete the premium, tablet/laptop-first Modern Atelier visual system around the reader, without adding unsupported backend capabilities or altering the functioning v12 EPUB.js Android app.

## Connected prototype
[Open the standalone Phase D interactive HTML](../prototypes/vnext-supporting-experience-phase-d-v05.html). This extends the Phase C Home, Library, Story Reader, Visual Reader and Study Space; their original sample content and artwork remain intact.

## Screen inventory
| Screen | Designed & wired preview | Data boundary |
| --- | --- | --- |
| Unified Search | Books, Collections and Notes scopes; live queries, sample results, empty state, Ctrl/⌘+K | Only original sample content |
| Collections | Original illustrated shelves, details, add/remove sample titles, create a demo shelf | Separate demo browser storage |
| Saved Places | Sample chapter bookmarks and linked reflections; return to sample Story chapter | Not a real EPUB CFI |
| Notes & Highlights | Original sample reflections, filtering, text search, create a demo note and view context | No real EPUB anchors or export |
| Settings | Appearance, Reading, Library, Accessibility, Privacy & Data, About; Paper/Night, font, line spacing, density, contrast, motion and reset confirmation | No account, Google sync, cloud backup or Android reader setting mutation |
| Help | Searchable FAQ, keyboard shortcuts, truthful format/import explanations | Static local guidance |
| Interface States | Interactive empty/loading/error design patterns with useful actions | Simulated only |
| More | Progressive access to six supporting sections on tablet and compact screens | Navigation only |

## Navigation and design
Desktop side navigation adds Search, Collections, Saved and Notes without disturbing the three readers. Tablet portrait/compact use five destinations: Home, Library, Search, Saved, More. The More hub reveals secondary choices. The existing Moon/Book SVG identity, cover artwork, serif type, warm paper/moss palette and dark mode are preserved. The logo remains a proposal awaiting explicit approval.

## Scope
This is a visually interactive self-contained HTML design study. It does not parse imported EPUBs or operate on the installed Android APK. The isolated data key is `folilune_vnext_phased_v05`; none of the v12 EPUB storage is read or modified. The demo may retain sample notes and collections only when browser storage allows. Do not use the demo to store important personal content.

## Exit gate
Owner reviews all screens and requests changes. After visual approval, Phase E performs motion, art, copy, accessibility and responsive quality passes. Integration into the real EPUB engine and the planned functionality update are separate future engineering gates. Do not merge this design PR or issue an APK without approval.
