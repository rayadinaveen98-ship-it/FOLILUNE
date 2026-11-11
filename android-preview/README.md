# FOLILUNE vNext Phase E — VISUAL PREVIEW APK

This is a **separate installable app for visual review** of the Phase E Modern Atelier experience.

**Application ID**: `com.folilune.vnextpreview`  
**Label**: `FOLILUNE vNext Preview`  
**Version**: `0.6.0-visual-preview`

It deliberately does not share package storage with `com.folilune.tablet` (the verified v12 real EPUB reader). Do not replace or uninstall the v12 reader to try this build.

- Home, Library, Story/Visual/Study, Settings, onboarding, Saved, Collections, Search, Notes, Help are *demo experiences*, using original placeholder content.
- It **does not import actual books**, read existing v12 files, or provide real EPUB/CBZ/PDF support, Gmail sync, or backup.
- HTML is copied from `prototypes/vnext-complete-experience-phase-e-v06.html` during CI (not a duplicate design source). No remote dependencies/network.
- Reader navigation, user interactions and return-user preview storage use the Android WebView browser, but data durability on the user's physical tablet is still to be tested.
- This is a **debug APK** signed by CI; it is not a Google Play release.
- The Folded Moon icon is provisional pending owner brand approval.

## Testing on tablet
Install `FOLILUNE vNext Preview` alongside your existing FOLILUNE v12. Open to welcome/Skip, view Home and sections, rotate, adjust theme/text size and test reopening. Send screenshots and bugs. This is a visual prototype acceptance check, not feature or user-data validation.
