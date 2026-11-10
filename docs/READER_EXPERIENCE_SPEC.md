# FOLILUNE — Progressive Discovery Experience Specification
**Version:** Phase 1 / v0.1 | **Status:** proposed, awaiting product-owner visual approval

## Core contract
**First 10 seconds:** user immediately recognizes the primary action: continue reading or add/select a book. No feature dashboard.
**First reading session:** text or art dominates the canvas. The reader gets clear navigation and a predictable way to reveal controls. Nothing requires hidden gestures alone.
**When curious:** tapping content, selecting text or choosing a visible 'Tools' affordance reveals only relevant actions.
**When experienced:** a deliberate workspace opens deeper layouts, notes, references, search, analytics and customization.
**Always:** exit is easy; back navigation predictable; all layers respect keyboard/screen reader/reduced motion.

## Three proposed experiences, one library
### Story / Prose
- Single column portrait, possible dual spread landscape/desktop when it improves line length.
- Adjustable reading typography: face, size, line length, margin, line height, themes; avoid full CSS fights before testing the EPUB.
- Chapter/search/table of contents readily discoverable; contextual dictionary and annotation accessible by selection.
- Optional narrative aides must be user initiated; they should never display future-book spoilers. Spoiler prevention needs rigorous source boundaries; it cannot be guaranteed by a basic model prompt.

### Visual / Comics, manga, webtoons
- Preserve author artwork, aspect ratios, panel order, and color.
- Single/double page, RTL/LTR book direction, webtoon continuous vertical scroll, zoom, optional guided panel navigation.
- Panel detection is a **candidate research feature**, not a guarantee; always provide an immediate manual fallback.
- Do not blur or darken panels for decoration, crop vital art, overlay unsolicited text, or narrate copyrighted art using cloud models by default.

### Study / Documents and nonfiction
- Reader-first view; optional split workspace with notes, highlights, quotes and document outline.
- Notes should link back to stable source location where possible and expose uncertainty if source changes.
- Keyboard and stylus interactions are equal citizens, not afterthoughts.
- Export own annotations/notes in portable formats. Avoid exporting substantial copyrighted text in ways that infringe rights.

## Shared navigation map (proposal)
Home — Continue / Library / Search
  -> Work detail — metadata / read / manage
     -> Reader — content-focused
        -> Quick navigation — TOC / progress / search / bookmark
        -> Contextual tools — highlight / define / note / translate, format-dependent
        -> Deliberate workspaces — Study, compare, layout settings
     -> Collection — organize / details
Settings — accessibility / data & privacy / import & sync / shortcuts

## Key interaction test cases
1. Can a first-time reader return to the library without hunting for gestures?
2. Can a fiction reader toggle all nonessential UI off and back on with one discoverable action?
3. Can a manga reader switch to right-to-left without accidentally changing image order?
4. Can a research reader find their earlier note and jump to the correct source?
5. Does expanding advanced features preserve reading position and make returning trivial?
6. Can each action be done with keyboard-only input?
7. Does the app remain useful with motion reduced, ambient visuals disabled and no network?

## Proposed interaction grammar
- **Tap/Click on neutral page margin:** reveal or hide toolbar; never on reading text unless deliberate.
- **Select text:** concise contextual menu; expanded actions one clear step deeper.
- **Long press on page / right click:** contextual alternatives, not primary undiscoverable tools.
- **Keyboard:** shortcuts visible in menu; arrows/page navigation configurable and context-aware.
- **Stylus:** use for annotations where supported, with clearly indicated stroke and undo.
- **Reader settings:** preview changes immediately, undo/restore default easily.

## Visual quality bars
Editorial type, stable contrast, content-specific layouts, responsive spacing, deliberately limited animation. Premium is measured by comfort and accuracy, not GPU complexity. No full-screen onboarding parade, fake decorative books in lieu of content, or irreversible visual effects.

## Risks and checks
- **Feature invisibility:** progressive disclosure becomes hiding. Run discovery tasks without instruction.
- **Accidental UI reveal:** prevent overlays during page turns, annotation and zoom.
- **Layout instability:** fonts/theme/orientation must not lose reading position.
- **Manga mistakes:** page order and panels are semantic; human-adjustable override is needed.
- **Format mismatch:** tools unavailable for scanned PDF shouldn't appear to work.
- **Loss of trust:** no dark-pattern streaks, forced account or unannounced AI upload.

## Approval
This is an interaction direction. Neither the visual style nor exact screen designs are approved. High-fidelity Android tablet and laptop prototypes must be reviewed before implementation.
