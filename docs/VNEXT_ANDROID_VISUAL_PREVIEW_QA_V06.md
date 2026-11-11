# FOLILUNE vNext v06 Android Visual Preview APK — Verification
Date: 2026-11-11

## Final state
- **Signed Android debug preview APK built and packaging verified**, app ID `com.folilune.vnextpreview`, version `0.6.0-visual-preview`, minSdk26, targetSdk34.
- Existing functional v12 reader app ID `com.folilune.tablet`; preview installs alongside it, with distinct private storage.
- **CI run [43040973590](https://github.com/rayadinaveen98-ship-it/FOLILUNE/actions/runs/43040973590) — SUCCESS**.
- **Android 14 AOSP emulator smoke tests 2/2 PASS:** real child iframe ready handshake and offline local first-launch, WebView localStorage across forced process restart.
- Clean Android process screenshot from GitHub artifact `FOLILUNE-vNext-v06-Android-QA` shows actual welcome layout with logo, typography, intro text, call-to-action buttons and original moonlit landscape (not blank).
- `apksigner verify` and `aapt dump badging` passed; exact bundled `prototypes/vnext-complete-experience-phase-e-v06.html` asset was checked.
- SHA-256 of final handoff APK: `ec28de63bc1f0ff1978d20c26b4f3ac418406c7989726c2631ceab374f0f423b`.

## Debugging detail
An initial emulator screenshot was blank although the app's DOM tests passed. The WebView URL override erroneously blocked the browser-managed sandboxed `about:srcdoc` URL; modified it to block only remote http/https while allowing internal browser schemes. A second screenshot was still blank because instrumentation tore down the Activity and screenshot capture reused the same process. The final screenshot script explicitly force-stops the preview and launches a **new Android process** before capture. This produced a nonblank, legible screenshot. Instrumentation was also tightened to require a genuine first-launch iframe `ready` postMessage, not just an iframe DOM node.

## Scope and caveats
This is a **visual prototype APK**, not the integrated v12 EPUB reader. Real EPUB imports, source-based highlights/notes, sync and Google account features are not part of it. The current prototype's changes are limited to the separate preview Android app, its source, QA and companion design prototype; no v12 user data was migrated or changed. Native button handoffs and browser storage were smoke-tested on an emulator, **not on the owner's actual tablet**. No production security, EPUB conformance, or accessibility certification. Signed with ephemeral CI debug key; later preview APK updates may require uninstalling only the preview, losing demo state. Do not uninstall v12.

## Next gate
Owner installs preview on tablet and reviews portrait/landscape, welcome, Skip, Home, Story, Visual, Study, Library, Settings, and returning-user flow. Fix real-device issues without changing v12 EPUB engine. Integrate approved visuals with the real engine only after user acceptance.
