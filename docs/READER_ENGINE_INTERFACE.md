# FOLILUNE — Proposed Reader Engine Boundary
**Status:** Architectural proposal, not implemented | **Date:** 2026-11-11

## Why a boundary matters
The app UI (Modern Atelier) should not depend directly on an arbitrary EPUB parser's internal node structure. The library/navigation/notes UX remains stable while the actual engine can be changed after measured validation. This also avoids forcing one engine to handle comics, PDFs, and prose in the same way.

## Candidate domain types
```ts
type BookSource =
  | {kind: 'epub'; file: File}
  | {kind: 'text'; file: File};
type ReaderLocator = {
  bookId: string;
  renderer: 'epubjs' | 'readium' | 'foliate' | 'text-preview';
  renditionVersion: number;
  chapterHref?: string;
  cfi?: string;             // when supported; engine-specific validation
  progress?: number;        // 0...1 fallback only
  excerpt?: string;         // matching aid; never silently replaced by another edition
};
interface ReaderEngine {
  supports(source: BookSource): boolean;
  open(source: BookSource): Promise<{ metadata: {title: string; author?: string}; toc: {title: string; href: string}[] }>;
  mount(container: HTMLElement): Promise<void>;
  navigate(target: {href?: string; cfi?: string; percent?: number}): Promise<void>;
  location(): Promise<ReaderLocator>;
  setAppearance(options: {fontScale?: number; theme?: 'paper'|'sepia'|'night'; lineHeight?: number}): Promise<void>;
  onLocationChanged(handler: (loc: ReaderLocator) => void): () => void;
  dispose(): Promise<void>;
}
```

This is a **design target**, not a type definition guaranteed by upstream libraries.

## Required behavior
1. All imported EPUBs remain untrusted; no user-book script execution by default.
2. No remote images/fonts or embedded external network requests without explicit consent.
3. Do not confuse byte extraction with rendering fidelity: unsupported features must be surfaced, not silently ignored.
4. Save locators atomically on chapter navigation, visibility change and reader exit, with safe rollback; test across font and screen changes.
5. Render inside a controllable, accessible, isolated surface; content must not execute arbitrary scripts.
6. Preserve an independent library record (book identity/filename/edition/rights), annotation schema and settings.
7. Avoid silently migrating `text-preview` block indices to EPUB CFI—migrate with an explicit uncertainty path and fall back safely.
8. Support keyboard, touch, stylus where applicable, screen reader, reduced motion and offline reading.
9. Respect data portability and user-owned content rights. Keep books private.
10. Provide an automatic recovery path if a new engine fails to open a previously imported book.

## Acceptance test before real engine adoption
- Compare all four v8 baseline fixtures plus additional real licensed large EPUB, nested TOC, Indic script and fixed-layout samples.
- Verify chapter/TOC links; cross-chapter and intra-chapter positions; resizing; exported reading positions and note anchors.
- Verify sandbox isolation from malicious EPUB scripts/URLs and archive bombs.
- Run an on-device offline/restart/restore flow. Use real book-position assertions, not just a screenshot.
- No reduction in Modern Atelier reading comfort, visible hierarchy or discoverability.

## Decision state
**PROPOSED** pending technical feasibility + visual/usability approval. Only commit a renderer adapter after comparative proof and license review; do not replace v7 reader in place.
