# FOLILUNE vNext — Phase E Complete Experience v06

**Date:** 2026-11-11 · **Status:** visual/interaction review ready, **NOT** a native release or real-reader integration.

## What's new
For the first time, Phase B's original illustrated welcome, skippable opening, optional reading interests, Paper/Night and text-size setup, sample-book introduction and guided Home hand off to Phase D's **actual connected visual study** in one [offline HTML prototype](../prototypes/vnext-complete-experience-phase-e-v06.html). The main interface includes Home, Library, Story, Visual, Study, Search, Collections, Saved Places, Notes, Settings, Help and empty/loading/error state gallery.

- First-run entry is optional, skippable and reduced-motion aware.
- Preferences transfer to the main demo. A quiet Home label reflects interests.
- Welcome tour can be replayed from the top bar; replay restores the **current** theme and type size without clearing sample bookmarks.
- Returning visitors go straight to Home if browser storage works.
- Modern Atelier styling, original SVG art and the Folded Moon **provisional** logo are retained.
- Improved `:focus-visible`, skip-navigation link and compact/tablet buttons; a DOM audit of six screens at 390px and 768px found zero visible targets under 44px per dimension.
- Fixed an actual first-launch bug: bridge initialization rebound the splash's Skip intro handler. Setup events are now scoped to the screen so Skip works.

## Architecture boundary
This remains a self-contained **design study**. The complete Phase B HTML is embedded in a sandboxed `srcdoc` iframe (scripts enabled, same-origin denied), exchanging only expected `postMessage` events from the verified frame window. The separate Phase D visual app has isolated preview storage keys prefixed `folilune_vnext_phasee_v06_`; the existing **v12 EPUB.js real reader, Android APK, import pipeline, CFI positions and user data are NOT changed**. No network, login, Google Drive or cloud feature was added. This iframe bridge is a review implementation, **not** the required future native architecture.

## QA and approval
[Browser QA](VNEXT_PHASE_E_QA_V06.md) shows 241/241 Playwright checks (five viewport sizes plus reduced-motion); these rely on `page.set_content` and explicitly mocked memory storage because sandbox browser navigation to file/localhost was blocked. This does **not** prove real disk persistence, device restart behavior, EPUB imports or screen-reader accessibility.

[Owner visual approval matrix](VNEXT_VISUAL_APPROVAL_GATE_V06.md) remains open. Approval of the visual system should be followed by a separate engineering integration branch using the actual v12 reader, verified data migration, EPUB/CFI continuity, physical tablet QA and accessibility. Only after that gate should deep functionality expansion start.
