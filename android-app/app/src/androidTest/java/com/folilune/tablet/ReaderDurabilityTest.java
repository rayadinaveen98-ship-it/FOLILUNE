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
    }

    private static String quoted(String text) {
        return JSONObject.quote(text);
    }

    /**
     * Observe the real rendition created by EPUB.js without replacing its rendering methods.
     * Used only in instrumentation; never included as an app UI feature.
     */
    private void observeRendition() throws Exception {
        assertEquals("true", eval("(()=>{const original=window.ePub;if(typeof original!=='function')return false;window.ePub=function(...args){const book=original(...args);const renderTo=book.renderTo.bind(book);book.renderTo=function(...renderArgs){const rendition=renderTo(...renderArgs);window.__foliluneQaRendition=rendition;return rendition};return book};return true})()"));
    }

    private static final String STATE =
            "JSON.parse(localStorage.getItem('folilune_atelier_v1')||'{}')";
    private static final String LOCATION =
            "window.__foliluneQaRendition?.currentLocation()?.start";

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

        // Validate the actual EPUB.js rendition location, not just a saved value in localStorage.
        boolean exactCfiRestored = waitFor(
                LOCATION + "?.cfi===localStorage.getItem('folilune_android_qa_cfi')"
                        + " && " + LOCATION + "?.index===1", 30);
        if (!exactCfiRestored) {
            String info = eval("(()=>{const r=window.__foliluneQaRendition;"
                    + "const st=" + STATE + ";return JSON.stringify({"
                    + "expected:localStorage.getItem('folilune_android_qa_cfi'),"
                    + "actual:r?.currentLocation()?.start?.cfi,"
                    + "index:r?.currentLocation()?.start?.index,"
                    + "savedState:st.readingPositions?.[st.book]?.cfi,"
                    + "restoring:typeof epubRuntime==='undefined'?null:epubRuntime?.restoring,"
                    + "currentBook:st.book,engine:!!document.querySelector('#epubStage iframe')})})()");
            fail("Exact EPUB CFI restoration mismatch: " + info);
        }

        assertEquals("true", eval("(()=>{const st=" + STATE
                + ";const id=localStorage.getItem('folilune_android_qa_id');"
                + "return st.book===id && st.readingPositions?.[id]?.cfi==="
                + "localStorage.getItem('folilune_android_qa_cfi')"
                + " && st.epubBookmarks?.[id]?.includes("
                + "localStorage.getItem('folilune_android_qa_cfi'))===true})()"));

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
}
