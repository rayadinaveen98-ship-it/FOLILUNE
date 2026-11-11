# FOLILUNE — Local Library Recovery v5 Test Report

**Date:** 2026-11-11  
**Status:** Interactive standalone HTML artifact tested locally in headless Chromium  
**Visual direction:** Modern Atelier remains unchanged

## What was built

A self-contained HTML prototype `FOLILUNE_Modern_Atelier_Reader_Proof_v5.html`, delivered as a downloadable ChatGPT conversation artifact. **This HTML file is not yet committed to GitHub or deployed.** The prior v4 HTML source remains the last source-backed repository prototype.

This phase focused on reader data safety, not decorative features.

### New, contextually revealed actions

Within **Library**, a collapsed `Back up or restore your library` section contains:
- **Save backup file**: save imported text, reading positions, per-book bookmarks, notes, highlights and preferences as versioned JSON.
- **Restore from file**: validate a compatible backup and ask for confirmation before restoring settings and annotations. Imported books not included in the backup remain available.
- Privacy notice: backups include readable book content and private notes; users must store them safely.

Import remains offline. No accounts, external requests or third-party runtime libraries are required for this demonstration.

### Defensive handling

- Backup has a recognized format/version and 30 MB prototype limit.
- Up to 100 books in one backup, 400 chapters per book, bounded paragraph count and text lengths.
- Reject duplicate or malformed imported-book identifiers.
- Restore validates the payload *before* making changes. While storage writes are intentionally best-effort for session-only browsing, this is not a transactional data restore.
- Restored HTML markup is sanitized through a strict inline-tag whitelist on import **and** at rendering. Script tags, event attributes and images are discarded in the experimental text-only reader.
- Import never claims to support DRM-protected or fixed-layout books.

## Browser test evidence

Tested at **1440×900**, **960×760**, **390×844** in headless Chromium:
1. Open Library, verify recovery controls collapsed until expanded.
2. Import the original two-chapter reflowable test EPUB.
3. Advance to chapter 2 and add a chapter bookmark.
4. Export a versioned backup; inspect document count, chapter count, reading location and bookmark.
5. Restore backup in a fresh page; reopen imported book at chapter 2.
6. Verify no horizontal viewport overflow and no page JavaScript exceptions.
7. Inject a hostile backup containing `<img onerror>`, `<script>`, and an event handler on `<strong>`; verify no injected elements/events survive and safe emphasis survives.
8. Verify a static fallback message renders when JS is disabled.

**Result:** all above smoke checks passed. This does not mean the browser's own IndexedDB survives a real OS/browser restart.

## Important limitations / safety gate

- Saving downloaded backups is a **user action**, not automatic durable storage.
- JSON files contain readable text and personal notes, with **no encryption**. Never upload them publicly.
- Memory/size limits are prototype-specific and may exclude very large books or libraries.
- Restore currently overwrites demo preferences and annotations after confirmation, but merges imported books by identifier. It does **not** guarantee fully transactional rollback if the environment fails mid-write.
- Actual file-origin storage persistence, real Android/Windows tests and cross-device recovery are still pending.
- The parser is not EPUB 3 conformant; no images, CSS, media overlays, links or advanced layout.
- Users should retain the original source EPUB/TXT/MD files.
- No production engine selected. See [engine evaluation](ENGINE_EVALUATION_2026-11.md).

## Next gate

On the user's Windows laptop, use Chrome or Edge to import an original test EPUB, export backup, reopen the HTML, restore backup, and confirm chapter/notes/bookmarks persist. Once the usability result is known, run an isolated maintained-engine fidelity spike; do not promote the text-extraction parser to release code.
