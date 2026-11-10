# FOLILUNE — Prototype Plan and Approval Gates
**Stage:** Phase 1 -> Phase 2 handoff | **Status:** proposed, not implemented
**Goal:** Validate how an advanced reader can feel simple on a laptop and an Android tablet before architecture or visuals are locked.

## What to demonstrate, not simply describe
**Prototype A — Enter the Library**
- Empty/first-run state: 'Add a book' and one public-domain sample; advanced controls absent until needed.
- Returning state: current title + calm library surfaces; obvious route to imported works.
- Show responsive 10-13 inch tablet portrait/landscape and 13-16 inch laptop window.
- Open book or comic in a single clear action; no forced account for local reading.

**Prototype B — Read a Story**
- Typographic EPUB-like layout, pagination/scroll alternatives, immersive controls that appear and recede.
- Table of contents, progress, bookmark, select-text menu with deeper tools behind a single expansion.
- Open a notes rail in landscape/desktop; restore the same place after font resize.

**Prototype C — Experience a Comic**
- Illustration-faithful pages; single/double-spread; LTR/RTL and webtoon flow as clearly labeled modes.
- Quick zoom or guided panel suggestion with manual override; never assume automated panels are perfect.
- Navigation stays unobtrusive and predictable on both pointer and touch devices.

**Prototype D — Study without leaving the page**
- Source document remains dominant; an expandable workspace displays linked notes/highlights.
- Show a source jump, collapse notes, switch back to reading, and optional compare panel.
- Make advanced controls easy to find via keyboard, not only hover/gesture.

## Visual directions to present for owner selection — no choice made yet
1. **Quiet Editorial:** ink/ivory surfaces, fine typography, subtle depth.
2. **Cinematic Library:** dark atmospheric materials with content-focused reading surfaces.
3. **Modern Atelier:** tactile, airy creative workspace for literature, comics and research.
Each direction must include the *same real UX flows* so style comparisons are fair.

## Motion and fidelity rubric
| Goal | Pass criterion |
| --- | --- |
| Reading primacy | Text/art stays legible, unclipped, visually dominant at every breakpoint. |
| Progressive discovery | Core reading/navigation is findable without a tutorial; advanced tools do not flood first-run. |
| Delight | Micro-interactions have purpose; no distracting ambient motion in reading state. |
| Accessibility | Keyboard, readable focus, large type, screen-reader label plan, reduced-motion behavior. |
| Consistency | Identical terms and underlying behaviors across tablet and laptop, tailored layouts. |
| Performance | No interaction depends on slow 3D effects, network, or a paid AI service. |
| Recovery | Closing/rotating/resizing never appears to lose book location or current notes. |

## Prototyping sequence and evidence required
1. Low-fidelity interaction maps reviewed for hidden or excessive steps.
2. Tablet + laptop high-fidelity mockups in all three proposed styles, clearly labeled concepts.
3. Interactive click-through showing reading, library, comic, study, and feature discovery.
4. Capture viewport-sized screenshots and walkthrough recordings if tooling permits.
5. Run at least five scenario tests with uncoached users when available; record where people get stuck.
6. Owner selects visual direction; **only then** lock design system, components and implementation.

## Research tasks before stack selection
- Compare Readium Kotlin, Readium Web/epub.js and PDF.js against *same* legal samples.
- Prototype safe CBZ rendering separately; add RTL/webtoon logic tests.
- Test annotation anchors, reading position and offline behavior across two window sizes.
- Estimate device/platform support cost and dependency license obligations.

## Decision backlog
- First release: Android tablet and Windows (native vs browser/PWA)? Other systems later?
- First-release formats: recommend evaluation of EPUB, PDF and CBZ first, not a promise of coverage.
- Local-only vs optional account/sync.
- Product's relationship to external catalogs and discovery.
- Whether AI, TTS and audio belong in first release.
- Revenue strategy after competitor analysis and real reader testing.

## Owner approval checkpoint
**No visual direction has been selected yet and no functional app has been created.** The deliverable is a research-backed design brief to support the next review.
