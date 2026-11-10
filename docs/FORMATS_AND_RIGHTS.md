# FOLILUNE — Format Support & Content Rights
_Status: feasibility matrix, not a promise of support._

## Reading-format candidates
| Format / type | Reader behavior | Feasibility & research |
| --- | --- | --- |
| EPUB (reflowable) | Novels, nonfiction, poetry | High priority candidate; verify EPUB 3 parsing, navigation, accessibility and CSS behavior. |
| EPUB (fixed layout) | Illustrated / design books | Requires separate layout and font/zoom handling; test on tablets. |
| PDF | Textbooks, papers, documents | Important, but highlights and reflow depend on text layers. OCR is separate and optional. |
| CBZ (comic archive) | Comics/manga | Candidate for early visual-story support; verify file safety, page order and image scaling. |
| CBR / other archives | Comics | Investigate archive libraries and applicable licensing before committing. |
| TXT / Markdown / HTML | Essays and own writing | May be feasible via text reader or safe import conversion; sanitize HTML. |
| DOCX / ODT / RTF | Manuscripts, documents | Evaluate conversion fidelity, security and maintenance costs. |
| Audio formats | Audiobooks | Different media/playback engine; possible later stage. |
| Web articles | Reading saved material | Requires legal capture model, canonical links and safe rendering. |

## Rights and commercial boundaries
- Public-domain content and properly licensed sample books are suitable for development and demonstrations.
- User-provided, legally possessed, DRM-free files may be read subject to applicable law and terms.
- A book/comic catalog is **not** implicitly available just because its metadata or cover can be found online.
- DRM bypass is not a baseline feature; integrations with proprietary ebook ecosystems require permissions and technical verification.
- Avoid redistributing copyrighted text, images or artwork without authorization.
- Metadata, covers, quotations, translations and AI-processing rights should be evaluated individually.

## Security and privacy
- Treat EPUB/HTML/archive imports as untrusted input.
- Prevent path traversal and decompression bombs; impose sensible file limits.
- Do not run embedded scripts in imported content.
- Keep private notes, reading progress, annotations and files local unless reader intentionally enables sync.
- Explain any remote processing, cloud uploads and AI assistance before use.

## Format research acceptance tests
1. Open representative small and very large files.
2. Test chapters, tables of contents, multilingual text, right-to-left and vertical scripts where relevant.
3. Test image fidelity, orientation, zoom and alternate comic reading directions.
4. Measure stability after app backgrounding, shutdown and device rotation.
5. Validate keyboard, screen reader, scalable type and reduced motion.
6. Verify backup/restore and export of reader-owned progress and annotations.

## Decision gate
Lock initial format support only after cross-platform feasibility testing and prioritization with the product owner.
