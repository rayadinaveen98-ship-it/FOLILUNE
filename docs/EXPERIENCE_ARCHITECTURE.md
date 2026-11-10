# FOLILUNE — Experience Architecture (Draft)
_Status: hypotheses awaiting prototype review._

## Principle: a quiet beginning, intelligent depth
**Level 0 — Arrival:** a calm greeting, one clear Continue Reading action, a small number of meaningful shelves.
**Level 1 — Reading:** content-dominant surface with discreet reading progress, navigation and a reversible way to reveal controls.
**Level 2 — Context:** selecting text, a panel or a chapter exposes just the tools that apply.
**Level 3 — Workspace:** readers can deliberately open notes, research, reading analytics, multi-pane tools and customization.
**Level 4 — Personalization:** advanced layout, automation and optional assisted-reading controls belong in settings or relevant workspaces.

Progressive disclosure must **not** make essential functions impossible to find; discoverability and accessibility will be tested.

## Primary surfaces
### Home / Continue
Current read and a handful of intentional shortcuts; no dashboard overload.

### Library
Cover-led browsing; flexible views (shelf/grid/list); collections, search, tags and filter controls available as needed. Large libraries require fast indexing and robust import status.

### Reader engines
- **Prose:** typography, themes, reflow, bookmarks, highlights, navigation.
- **Visual story:** page/panel navigation, manga reading direction, fit/zoom, respectful transitions.
- **Document/study:** precise pages, navigation, search, text selection when supported, side notes.
A common app shell should not force incompatible reading behaviors on different content.

### Insight & Study
Notes, highlights, cross-references and contextual aids as an opt-in side workspace. No AI panel permanently occupying the reading surface.

### Sessions & Reflection
Reading goals and insights only when wanted; never shame users with streak loss or intrusive prompts.

## Tablet interaction hypotheses
- Portrait: one dominant reading surface; controls appear through intentional tap/selection.
- Landscape: optional two-page spread or reader + notes; dependent on content and screen size.
- Stylus: drawing/highlighting only where document capabilities support reliable anchoring.
- Gesture customization and undo are valuable; navigation gestures must avoid accidental page turns.

## Laptop interaction hypotheses
- Resizable windows; mouse, trackpad and keyboard-first use.
- Optional side-by-side text/notes, multi-column or spread reading.
- Discoverable shortcuts and menus; no reliance on touch-only gestures.
- UI density adjusts to window width, not merely device name.

## Visual direction — not yet approved
Explore editorial typography, restrained depth, warm/dark reading environments, excellent transitions and carefully staged micro-interactions. Avoid heavy GPU effects, skeuomorphic imitation and motion that compromises readability. Design mockups must be presented for owner approval before implementation.

## Potential delight moments to test
- A reading session transitions into an unobtrusive, immersive page.
- A text selection unfolds a concise semantic menu, which can expand into a learning workspace.
- A library shelf responds subtly to user organization, with reduced-motion equivalent.
- Manga pages animate only in ways that respect artwork and reading direction.

## Important technical experience constraints
- Reliable reading-position anchoring across font-size/reflow changes.
- Stable highlight anchors across version changes wherever feasible.
- Secure imports; file parsing isolation; recovery from corrupted files.
- Persist reading state locally before optional sync.
- Private content should stay local unless explicit cloud or assistance features are enabled.

## Prototype order
1. Home → Library → open a book.
2. Prose reading in portrait/landscape and desktop resizes.
3. Comic/manga page navigation.
4. Highlight → contextual menu → notes/workspace.
5. User settings and advanced feature discovery.

**Approval gate:** owner reviews real interactive prototypes or device screenshots before visual implementation is locked.
