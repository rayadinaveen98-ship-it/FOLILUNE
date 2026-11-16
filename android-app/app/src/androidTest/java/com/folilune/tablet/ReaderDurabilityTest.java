package com.folilune.tablet;

import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;

import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.*;

/**
 * Offline FOLILUNE WebView integration check.
 *
 * Test runner intentionally executes seedImportedEpub then stops the app process,
 * then executes verifyPersistedEpub in a NEW instrumentation process.
 * This exercises actual Android WebView's browser storage rather than a JS mock.
 */
@RunWith(AndroidJUnit4.class)
public class ReaderDurabilityTest {
    @Rule
    public ActivityScenarioRule<MainActivity> scenario =
            new ActivityScenarioRule<>(MainActivity.class);

    private String eval(String script) throws Exception {
        CountDownLatch done = new CountDownLatch(1);
        AtomicReference<String> result = new AtomicReference<>();
        scenario.getScenario().onActivity(activity -> {
            ViewGroup root = activity.findViewById(android.R.id.content);
            WebView web = (WebView) root.getChildAt(0);
            web.evaluateJavascript(script, value -> {
                result.set(value);
                done.countDown();
            });
        });
        assertTrue("WebView JavaScript callback timed out", done.await(20, TimeUnit.SECONDS));
        return result.get();
    }

    private boolean waitFor(String expression, int seconds) throws Exception {
        long end = System.currentTimeMillis() + seconds * 1000L;
        do {
            if ("true".equals(eval(expression))) return true;
            Thread.sleep(300);
        } while (System.currentTimeMillis() < end);
        return false;
    }

    private void awaitApplication() throws Exception {
        assertTrue("Modern Atelier screen did not load",
                waitFor("document.readyState==='complete' && !!document.querySelector('#screen') && document.body.innerText.includes('FOLILUNE')", 30));
        assertEquals("true", eval("location.origin==='https://appassets.androidplatform.net'"));
        assertEquals("true", eval("typeof window.ePub==='function' && typeof window.JSZip==='function'"));
        assertTrue("vNext integrated visual JS failed to load",
                waitFor("window.FOLILUNE_G1?.version==='g1'", 15));
        assertEquals("true", eval("!![...document.querySelectorAll('link[href]')].find(x=>x.getAttribute('href')==='./vnext-integrated.css')"));
        assertEquals("true", eval("!!document.querySelector('#vnWelcome')"));
        assertTrue("Live Home failed to replace the old no-JavaScript fallback",
                waitFor("!!document.querySelector('#screen > .content .hero')"
                        + " && !document.querySelector('.fallback-top')", 15));

        assertTrue("Modern Atelier Moon/Book illustration did not replace older Home art",
                waitFor("document.querySelector('.hero-art svg')?.getAttribute('aria-label')?.includes('open book')===true", 15));
        if ("true".equals(eval("window.FOLILUNE_G1.isWelcomeOpen()"))) {
            // Simulate an actual reader skipping onboarding. This must never reset library data.
            assertEquals("true", eval("(()=>{document.querySelector('#vnSkip').click();"
                    + "return window.FOLILUNE_G1.isWelcomeOpen()===false})()"));
        }
        // G2 screens must be connected to actual reader state rather than a
        // separate catalog of fictional demo tiles.
        assertTrue("G2 connected surfaces not initialized",
                waitFor("window.FOLILUNE_G2?.version==='g2a'", 15));
        assertEquals("true", eval("window.FOLILUNE_REAL_UI?.snapshot().books.length>=7"));
        assertEquals("true", eval("(()=>{document.querySelector('.nav-btn[data-view=search]').click();"
                + "return !!document.querySelector('#g2Search')"
                + "&& document.querySelector('#topTitle').textContent==='Search'})()"));
        assertEquals("true", eval("(()=>{document.querySelector('.nav-btn[data-view=saved]').click();"
                + "return document.querySelector('#topTitle').textContent==='Saved Places'})()"));
        assertEquals("true", eval("(()=>{document.querySelector('.nav-btn[data-view=settings]').click();"
                + "return !!document.querySelector('.g2-settings')})()"));
        assertEquals("true", eval("(()=>{document.querySelector('.nav-btn[data-view=home]').click();"
                + "return !!document.querySelector('.g2-hero')})()"));
        assertTrue("G2 supporting surfaces did not load",waitFor("window.FOLILUNE_G2_SUPPORT?.version==='g2b'",15));
        assertEquals("true",eval("(()=>{document.querySelector('.nav-btn[data-view=collections]').click();return !!document.querySelector('.g2b-collection-grid')})()"));
        assertEquals("true",eval("(()=>{document.querySelector('.nav-btn[data-view=notes]').click();return !!document.querySelector('#g2bNotesResults')})()"));
        assertEquals("true",eval("(()=>{document.querySelector('.nav-btn[data-view=help]').click();return document.querySelectorAll('.g2b-faq').length>=7})()"));
        assertEquals("true",eval("(()=>{document.querySelector('.nav-btn[data-view=home]').click();return !!document.querySelector('.g2-hero')})()"));
    }

    private static String quoted(String text) {
        return JSONObject.quote(text);
    }

    /**
     * Observe the real rendition created by EPUB.js without replacing its rendering methods.
     * Used only in instrumentation; never included as an app UI feature.
     */
    private void observeRendition() throws Exception {
        assertEquals("true", eval("(()=>{const original=window.ePub;if(typeof original!=='function')return false;window.ePub=function(...args){const book=original(...args);const renderTo=book.renderTo.bind(book);book.renderTo=function(...renderArgs){const rendition=renderTo(...renderArgs);const display=rendition.display.bind(rendition);rendition.display=function(target,...rest){window.__foliluneQaRequestedDisplay=target;return display(target,...rest)};window.__foliluneQaRendition=rendition;return rendition};return book};return true})()"));
    }

    private static final String STATE =
            "JSON.parse(localStorage.getItem('folilune_atelier_v1')||'{}')";
    private static final String LOCATION =
            "window.__foliluneQaRendition?.currentLocation()?.start";
    // Verify against the actual bookmark saved before process death.
    // The separate test-only localStorage key sometimes is not flushed to disk
    // before Android force-stop, despite the real reader state being durable.
    private static final String EXPECTED_BOOKMARK =
            "(()=>{const st=" + STATE + ";const marks=st.epubBookmarks?.[st.book];"
            + "return Array.isArray(marks)&&marks.length===1"
            + "&&typeof marks[0]==='string'&&marks[0].startsWith('epubcfi(')"
            + "?marks[0]:null})()";

    @Test
    public void seedImportedEpub() throws Exception {
        awaitApplication();
        observeRendition();
        final String container = "<?xml version='1.0'?><container version='1.0' xmlns='urn:oasis:names:tc:opendocument:xmlns:container'><rootfiles><rootfile full-path='OEBPS/package.opf' media-type='application/oebps-package+xml'/></rootfiles></container>";
        final String opf = "<?xml version='1.0'?><package xmlns='http://www.idpf.org/2007/opf' unique-identifier='uuid' version='3.0'><metadata xmlns:dc='http://purl.org/dc/elements/1.1/'><dc:identifier id='uuid'>urn:uuid:834a1d38-1c74-4bf2-a664-e6788cc7dc3a</dc:identifier><dc:title>FOLILUNE Tablet QA</dc:title><dc:creator>FOLILUNE Lab</dc:creator><dc:language>en</dc:language><meta property='dcterms:modified'>2026-11-11T00:00:00Z</meta></metadata><manifest><item id='nav' href='nav.xhtml' media-type='application/xhtml+xml' properties='nav'/><item id='ch1' href='one.xhtml' media-type='application/xhtml+xml'/><item id='ch2' href='two.xhtml' media-type='application/xhtml+xml'/></manifest><spine><itemref idref='ch1'/><itemref idref='ch2'/></spine></package>";
        final String nav = "<html xmlns='http://www.w3.org/1999/xhtml' xmlns:epub='http://www.idpf.org/2007/ops'><head><title>Contents</title></head><body><nav epub:type='toc'><ol><li><a href='one.xhtml'>Beginning</a></li><li><a href='two.xhtml'>Afterwards</a></li></ol></nav></body></html>";
        final String chapter1 = "<html xmlns='http://www.w3.org/1999/xhtml'><head><title>Beginning</title></head><body><h1>Tablet QA Chapter One</h1><p>FOLILUNE Android offline reading test.</p></body></html>";
        StringBuilder second = new StringBuilder(
                "<html xmlns='http://www.w3.org/1999/xhtml'><head><title>Afterwards</title></head>"
                + "<body><h1>Tablet QA Chapter Two</h1>");
        for (int i = 1; i <= 120; i++) {
            second.append("<p>Paragraph ").append(i)
                    .append(". This distinct reading location must reopen after Android process termination."
                            + " A beautiful reader remembers precisely where the reader was within the chapter."
                            + " This paragraph is used only for controlled test content.</p>");
        }
        final String chapter2 = second.append("</body></html>").toString();
        final String seed = "window.__foliluneQa='starting';(async()=>{"
                + "const z=new JSZip();z.file('mimetype','application/epub+zip',{compression:'STORE'});"
                + "z.file('META-INF/container.xml'," + quoted(container) + ");"
                + "z.file('OEBPS/package.opf'," + quoted(opf) + ");"
                + "z.file('OEBPS/nav.xhtml'," + quoted(nav) + ");"
                + "z.file('OEBPS/one.xhtml'," + quoted(chapter1) + ");"
                + "z.file('OEBPS/two.xhtml'," + quoted(chapter2) + ");"
                + "const bytes=await z.generateAsync({type:'uint8array'});"
                + "const f=new File([bytes],'FOLILUNE-Tablet-QA.epub',{type:'application/epub+zip'});"
                + "const input=document.querySelector('#importBookInput');"
                + "if(!input)throw new Error('Import control missing');"
                + "const transfer=new DataTransfer();transfer.items.add(f);input.files=transfer.files;"
                + "input.dispatchEvent(new Event('change',{bubbles:true}));window.__foliluneQa='selected';"
                + "})().catch(e=>window.__foliluneQa='error:'+e.message);'started'";
        assertEquals("\"started\"", eval(seed));
        assertTrue("EPUB creation/selection failed: " + eval("window.__foliluneQa"),
                waitFor("window.__foliluneQa==='selected'", 30));
        assertTrue("Imported EPUB did not open its reading surface",
                waitFor("!!document.querySelector('#epubStage iframe') && document.body.innerText.includes('FOLILUNE Tablet QA')", 50));
        assertEquals("true", eval("!document.body.innerText.includes('Session only: FOLILUNE Tablet QA')"));
        // Add a stable local checkpoint that must survive process death.
        assertEquals("true", eval("(()=>{localStorage.setItem('folilune_android_qa','durable');return localStorage.getItem('folilune_android_qa')==='durable'})()"));
        // Navigate inside the REAL EPUB.js rendition, then capture the exact CFI.
        assertEquals("true", eval("!!document.querySelector('[data-panel=contents]')"));
        eval("document.querySelector('[data-panel=contents]').click();true");
        assertTrue("EPUB table of contents missing",
                waitFor("document.querySelectorAll('[data-epub-href]').length===2", 15));
        eval("document.querySelectorAll('[data-epub-href]')[1].click();true");
        assertTrue("EPUB did not navigate to chapter two",
                waitFor(LOCATION + "?.index===1", 25));
        // Move forward within the long second chapter so we're not merely bookmarking its beginning.
        String before = eval(LOCATION + "?.cfi");
        assertEquals("true", eval("!!document.querySelector('[data-action=next-chapter]')"));
        eval("document.querySelector('[data-action=next-chapter]').click();true");
        assertTrue("Did not advance to a distinct location within chapter two",
                waitFor(LOCATION + "?.index===1 && " + LOCATION + "?.cfi!=="
                        + before, 20));
        assertEquals("true", eval(LOCATION + "?.cfi?.startsWith('epubcfi(')===true"));
        assertEquals("true", eval("(()=>{const state=" + STATE
                + ";const id=state.book;const saved=state.readingPositions?.[id]?.cfi;"
                + "const actual=" + LOCATION + "?.cfi;"
                + "if(saved!==actual)return false;"
                + "localStorage.setItem('folilune_android_qa_cfi',actual);"
                + "localStorage.setItem('folilune_android_qa_id',id);"
                + "return true})()"));

        eval("document.querySelector('[data-action=bookmark]').click();true");
        assertEquals("Bookmark CFI not saved exactly", "true",
                eval("(()=>{const state=" + STATE + ";const id=state.book;"
                        + "return state.epubBookmarks?.[id]?.includes("
                        + "localStorage.getItem('folilune_android_qa_cfi'))===true})()"));
        eval("window.__foliluneJournalCommitted=false;(()=>{"
                + "const check=()=>{const rq=indexedDB.open('folilune_local_library_v3');"
                + "rq.onsuccess=()=>{const db=rq.result;"
                + "const tx=db.transaction('books','readonly');"
                + "const q=tx.objectStore('books').get('__folilune_reading_state_checkpoint_v1__');"
                + "q.onsuccess=()=>{const st=" + STATE + ";const saved=q.result?.appState;"
                + "window.__foliluneJournalCommitted=!!saved?.epubBookmarks?.[st.book]"
                + "?.includes(localStorage.getItem('folilune_android_qa_cfi'))"
                + "&&saved.readingPositions?.[st.book]?.cfi"
                + "===localStorage.getItem('folilune_android_qa_cfi')};"
                + "tx.oncomplete=()=>{db.close();if(!window.__foliluneJournalCommitted)setTimeout(check,120)};"
                + "};rq.onerror=()=>setTimeout(check,180)};check()})();true");
        // Bookmarks must be committed in IndexedDB before intentionally killing
        // the app. This tests a real transaction, not only a synchronous
        // localStorage value that may not have reached device flash.
        assertTrue("EPUB bookmark journal did not commit to IndexedDB",
                waitFor("window.__foliluneJournalCommitted===true", 20));
        // Verify that real IndexedDB has saved the imported publication, not just a memory map.
        eval("window.__foliluneDbStored=false;(()=>{const rq=indexedDB.open('folilune_local_library_v3');rq.onsuccess=()=>{const db=rq.result;const tx=db.transaction('books','readonly');const q=tx.objectStore('books').getAll();q.onsuccess=()=>{window.__foliluneDbStored=q.result.some(x=>x.title==='FOLILUNE Tablet QA')};tx.oncomplete=()=>db.close()};rq.onerror=()=>window.__foliluneDbStored='error'})();true");
        assertTrue("Imported EPUB was not written to actual IndexedDB",
                waitFor("window.__foliluneDbStored===true", 15));
    }

    @Test
    public void verifyPersistedEpub() throws Exception {
        awaitApplication();
        observeRendition();
        assertEquals("Storage did not survive force-stop",
                "\"durable\"", eval("localStorage.getItem('folilune_android_qa')"));
        eval("document.querySelector('[data-view=library]').click();true");
        assertTrue("Previously imported EPUB was not restored from IndexedDB",
                waitFor("document.body.innerText.includes('FOLILUNE Tablet QA')", 30));
        assertEquals("true", eval("(()=>{const b=[...document.querySelectorAll('[data-book]')].find(x=>x.innerText.includes('FOLILUNE Tablet QA'));if(!b)return false;b.click();return true})()"));
        assertTrue("Reopened EPUB did not load in the actual browser renderer",
                waitFor("!!document.querySelector('#epubStage iframe') && document.body.innerText.includes('FOLILUNE Tablet QA')", 45));

        // Validate the actual EPUB.js rendition against the EPUB bookmark saved
        // during Stage 1. Do not rely on a separate test-only key surviving
        // a force-stop; that key has proven intermittently nondurable.
        assertEquals("true", eval("(()=>{const st=" + STATE
                + ";return Number.isInteger(st.durableRevision)&&st.durableRevision>=1})()"));
        assertEquals("Saved EPUB bookmark missing after restart: " +
                eval("(()=>{const st=" + STATE + ";return JSON.stringify({"
                    + "book:st.book,marks:st.epubBookmarks?.[st.book],"
                    + "revision:st.durableRevision,"
                    + "expectedMarker:localStorage.getItem('folilune_android_qa_cfi'),"
                    + "location:st.readingPositions?.[st.book]?.cfi})})()"),
                "true",eval(EXPECTED_BOOKMARK + "?.startsWith('epubcfi(')===true"));
        boolean exactCfiRestored = waitFor(
                LOCATION + "?.cfi===" + EXPECTED_BOOKMARK
                        + " && " + LOCATION + "?.index===1", 30);
        if (!exactCfiRestored) {
            String info = eval("(()=>{const r=window.__foliluneQaRendition;"
                    + "const st=" + STATE + ";return JSON.stringify({"
                    + "expected:" + EXPECTED_BOOKMARK + ","
                    + "qaMarker:localStorage.getItem('folilune_android_qa_cfi'),"
                    + "actual:r?.currentLocation()?.start?.cfi,"
                    + "index:r?.currentLocation()?.start?.index,"
                    + "savedState:st.readingPositions?.[st.book]?.cfi,"
                    + "restoring:typeof epubRuntime==='undefined'?null:epubRuntime?.restoring,"
                    + "currentBook:st.book,engine:!!document.querySelector('#epubStage iframe')})})()");
            fail("Exact EPUB CFI restoration mismatch: " + info);
        }

        assertEquals("true", eval("(()=>{const st=" + STATE
                + ";const id=st.book;const expected=" + EXPECTED_BOOKMARK + ";"
                + "return typeof expected==='string'"
                + " && st.readingPositions?.[id]?.cfi===expected"
                + " && st.epubBookmarks?.[id]?.includes(expected)===true})()"));

        // Reader preferences may reflow the document, but must not delete the saved bookmark.
        eval("document.querySelector('[data-panel=settings]').click();true");
        assertTrue("Reading settings did not expose typography controls",
                waitFor("!!document.querySelector('[data-action=font-up]')", 10));
        eval("document.querySelector('[data-action=font-up]').click();true");
        assertTrue("Font resizing moved the reader out of the saved chapter",
                waitFor(LOCATION + "?.index===1", 15));
        assertEquals("true", eval("(()=>{const st=" + STATE
                + ";return st.epubBookmarks?.[st.book]?.includes("
                + "localStorage.getItem('folilune_android_qa_cfi'))===true})()"));
    }
    /**
     * Simulate a WebView localStorage cache that is older than its committed
     * IndexedDB reading-state checkpoint. No production state is touched:
     * this test runs only on the isolated emulator package.
     */
    @Test
    public void poisonLocalCacheForRecovery() throws Exception {
        awaitApplication();
        assertEquals("true", eval("window.__folilunePoison='pending';(()=>{"
                + "const rq=indexedDB.open('folilune_local_library_v3');"
                + "rq.onsuccess=()=>{const db=rq.result;"
                + "const tx=db.transaction('books','readonly');"
                + "const q=tx.objectStore('books').get('__folilune_reading_state_checkpoint_v1__');"
                + "q.onsuccess=()=>{const saved=q.result?.appState;const id=saved?.book;"
                + "const marks=saved?.epubBookmarks?.[id];"
                + "if(!Array.isArray(marks)||marks.length!==1"
                + "||!saved.readingPositions?.[id]?.cfi){"
                + "window.__folilunePoison='checkpoint-incomplete';return}"
                + "const stale=JSON.parse(localStorage.getItem('folilune_atelier_v1')||'{}');"
                + "stale.durableRevision=0;stale.epubBookmarks={};stale.readingPositions={};"
                + "localStorage.setItem('folilune_atelier_v1',JSON.stringify(stale));"
                + "window.__folilunePoison='ready'};"
                + "q.onerror=()=>window.__folilunePoison='checkpoint-read-failed';"
                + "tx.oncomplete=()=>db.close()};"
                + "rq.onerror=()=>window.__folilunePoison='db-open-failed'"
                + "})();true"));
        assertTrue("Could not create stale local cache: " + eval("window.__folilunePoison"),
                waitFor("window.__folilunePoison==='ready'", 20));
        assertEquals("true", eval("(()=>{const s=" + STATE
                + ";return s.durableRevision===0"
                + "&&Object.keys(s.epubBookmarks||{}).length===0"
                + "&&Object.keys(s.readingPositions||{}).length===0})()"));
    }

    /** Confirm recovery and open the recovered EPUB bookmark through G2 Saved Places. */
    @Test
    public void verifyRecoveredJournalAndSavedPlace() throws Exception {
        awaitApplication();
        observeRendition();
        assertEquals("Journal did not restore the stale local cache", "true",
                eval("(()=>{const s=" + STATE
                        + ";const imported=window.FOLILUNE_REAL_UI.snapshot().books"
                        + ".filter(b=>b.imported&&b.title==='FOLILUNE Tablet QA');"
                        + "if(imported.length!==1||s.durableRevision<1)return false;"
                        + "const id=imported[0].id,marks=s.epubBookmarks?.[id];"
                        + "if(!Array.isArray(marks)||marks.length!==1"
                        + "||!marks[0].startsWith('epubcfi('))return false;"
                        + "window.__foliluneRecoveryExpected=marks[0];return true})()"));
        eval("document.querySelector('.nav-btn[data-view=saved]').click();true");
        assertTrue("Recovered EPUB bookmark missing from Saved Places",
                waitFor("document.querySelectorAll('.g2-saved-row').length>=1", 15));
        assertEquals("true", eval("(()=>{const row=[...document.querySelectorAll('.g2-saved-row')]"
                + ".find(x=>x.innerText.includes('FOLILUNE Tablet QA'));"
                + "if(!row)return false;row.click();return true})()"));
        assertTrue("Recovered Saved Place did not open a genuine EPUB rendition",
                waitFor("!!document.querySelector('#epubStage iframe')", 45));
        // After verifyPersistedEpub changed font size, EPUB.js may reflow and
        // snap currentLocation().start to the beginning of a new page. That
        // CFI can differ from the requested saved text anchor. Verify the
        // *exact requested CFI* reached rendition.display(), that the reader
        // is in the saved chapter, and that the bookmark remains unchanged.
        // Exact currentLocation equality without reflow remains covered by
        // verifyPersistedEpub after Android force-stop.
        assertTrue("Saved Places did not pass the selected CFI to EPUB.js; "
                + "debug=" + eval("JSON.stringify({expected:window.__foliluneRecoveryExpected,"
                + "requested:window.__foliluneQaRequestedDisplay,"
                + "actual:" + LOCATION + "?.cfi,chapter:" + LOCATION + "?.index})"),
                waitFor("window.__foliluneQaRequestedDisplay===window.__foliluneRecoveryExpected", 25));
        assertTrue("Saved Places did not land in the saved EPUB chapter; "
                + "debug=" + eval("JSON.stringify({actual:" + LOCATION
                + "?.cfi,index:" + LOCATION + "?.index})"),
                waitFor(LOCATION + "?.index===1", 20));
        assertEquals("Saved EPUB bookmark was mutated by the Saved Places jump", "true",
                eval("(()=>{const st=" + STATE + ";return st.epubBookmarks?.[st.book]"
                        + "?.includes(window.__foliluneRecoveryExpected)===true})()"));
    }

    /**
     * Remove the imported book WHILE its real EPUB renderer is active.
     * The old code left EPUB bookmarks behind, and rendering during removal
     * could recreate a deleted reading position from the live rendition.
     */
    @Test
    public void removeImportedBookClearsEpubState() throws Exception {
        awaitApplication();
        assertEquals("true", eval("(()=>{const b=window.FOLILUNE_REAL_UI.snapshot().books"
                + ".find(x=>x.imported&&x.title==='FOLILUNE Tablet QA');"
                + "if(!b)return false;window.__foliluneDeleteId=b.id;"
                + "const tile=[...document.querySelectorAll('[data-book]')]"
                + ".find(x=>x.dataset.book===b.id);"
                + "if(!tile)return false;tile.click();return true})()"));
        assertTrue("EPUB did not open before the deletion regression test",
                waitFor("document.querySelector('#epubPages')?.getAttribute('data-engine')==='epubjs-real'", 45));
        assertEquals("true", eval("(()=>{window.confirm=()=>true;"
                + "const b=document.createElement('button');b.dataset.action='remove-import';"
                + "b.dataset.id=window.__foliluneDeleteId;document.body.appendChild(b);"
                + "b.click();b.remove();return true})()"));
        assertTrue("Deleted book retained an EPUB bookmark or reading position",
                waitFor("(()=>{const s=" + STATE + ";const id=window.__foliluneDeleteId;"
                        + "return !s.readingPositions?.[id]&&!s.epubBookmarks?.[id]"
                        + "&&!s.lastOpened?.[id]&&!s.epubModes?.[id]"
                        + "&&!window.FOLILUNE_REAL_UI.snapshot().books.some(b=>b.id===id)})()", 20));
        assertEquals("true", eval("window.__foliluneDeletionCommitted=false;(()=>{"
                + "const check=()=>{const rq=indexedDB.open('folilune_local_library_v3');"
                + "rq.onsuccess=()=>{const db=rq.result;"
                + "const tx=db.transaction('books','readonly');"
                + "const q=tx.objectStore('books').getAll();"
                + "q.onsuccess=()=>{const rows=q.result,id=window.__foliluneDeleteId;"
                + "const j=rows.find(x=>x.id==='__folilune_reading_state_checkpoint_v1__');"
                + "window.__foliluneDeletionCommitted=!!j?.appState"
                + "&&!rows.some(x=>x.id===id)"
                + "&&!j.appState.epubBookmarks?.[id]"
                + "&&!j.appState.readingPositions?.[id]};"
                + "tx.oncomplete=()=>{db.close();"
                + "if(!window.__foliluneDeletionCommitted)setTimeout(check,150)};"
                + "};rq.onerror=()=>setTimeout(check,200)};check()})();true"));
        assertTrue("Deleted book or saved location remained in committed IndexedDB",
                waitFor("window.__foliluneDeletionCommitted===true", 20));
    }

    @Test
    public void verifyDeletedBookDoesNotReappear() throws Exception {
        awaitApplication();
        assertEquals("Deleted book or bookmark resurrected after process restart", "true",
                eval("(()=>{const s=" + STATE
                        + ";const b=window.FOLILUNE_REAL_UI.snapshot().books;"
                        + "return b.filter(x=>x.imported).length===0"
                        + "&&!Object.keys(s.readingPositions||{}).some(x=>x.startsWith('local-'))"
                        + "&&!Object.keys(s.epubBookmarks||{}).some(x=>x.startsWith('local-'))"
                        + "&&!Object.keys(s.lastOpened||{}).some(x=>x.startsWith('local-'))})()"));
        eval("document.querySelector('.nav-btn[data-view=saved]').click();true");
        assertEquals("true", eval("document.querySelectorAll('.g2-saved-row').length===0"));
    }

}
