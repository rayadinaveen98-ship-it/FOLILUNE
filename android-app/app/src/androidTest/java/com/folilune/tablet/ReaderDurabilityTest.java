package com.folilune.tablet;

import android.app.Activity;
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
        scenario.getScenario().onActivity((Activity activity) -> {
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
    }

    private static String quoted(String text) {
        return JSONObject.quote(text);
    }

    @Test
    public void seedImportedEpub() throws Exception {
        awaitApplication();
        final String container = "<?xml version='1.0'?><container version='1.0' xmlns='urn:oasis:names:tc:opendocument:xmlns:container'><rootfiles><rootfile full-path='OEBPS/package.opf' media-type='application/oebps-package+xml'/></rootfiles></container>";
        final String opf = "<?xml version='1.0'?><package xmlns='http://www.idpf.org/2007/opf' unique-identifier='uuid' version='3.0'><metadata xmlns:dc='http://purl.org/dc/elements/1.1/'><dc:identifier id='uuid'>urn:uuid:834a1d38-1c74-4bf2-a664-e6788cc7dc3a</dc:identifier><dc:title>FOLILUNE Tablet QA</dc:title><dc:creator>FOLILUNE Lab</dc:creator><dc:language>en</dc:language><meta property='dcterms:modified'>2026-11-11T00:00:00Z</meta></metadata><manifest><item id='nav' href='nav.xhtml' media-type='application/xhtml+xml' properties='nav'/><item id='ch1' href='one.xhtml' media-type='application/xhtml+xml'/><item id='ch2' href='two.xhtml' media-type='application/xhtml+xml'/></manifest><spine><itemref idref='ch1'/><itemref idref='ch2'/></spine></package>";
        final String nav = "<html xmlns='http://www.w3.org/1999/xhtml' xmlns:epub='http://www.idpf.org/2007/ops'><head><title>Contents</title></head><body><nav epub:type='toc'><ol><li><a href='one.xhtml'>Beginning</a></li><li><a href='two.xhtml'>Afterwards</a></li></ol></nav></body></html>";
        final String chapter1 = "<html xmlns='http://www.w3.org/1999/xhtml'><head><title>Beginning</title></head><body><h1>Tablet QA Chapter One</h1><p>FOLILUNE Android offline reading test.</p></body></html>";
        final String chapter2 = "<html xmlns='http://www.w3.org/1999/xhtml'><head><title>Afterwards</title></head><body><h1>Tablet QA Chapter Two</h1><p>This book should reopen after Android process termination.</p></body></html>";
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
        assertEquals("true", eval("!!document.querySelector('[data-action=bookmark]')"));
        eval("document.querySelector('[data-action=bookmark]').click();true");
        // Verify that real IndexedDB has saved the imported publication, not just a memory map.
        eval("window.__foliluneDbStored=false;(()=>{const rq=indexedDB.open('folilune_local_library_v3');rq.onsuccess=()=>{const db=rq.result;const tx=db.transaction('books','readonly');const q=tx.objectStore('books').getAll();q.onsuccess=()=>{window.__foliluneDbStored=q.result.some(x=>x.title==='FOLILUNE Tablet QA')};tx.oncomplete=()=>db.close()};rq.onerror=()=>window.__foliluneDbStored='error'})();true");
        assertTrue("Imported EPUB was not written to actual IndexedDB",
                waitFor("window.__foliluneDbStored===true", 15));
    }

    @Test
    public void verifyPersistedEpub() throws Exception {
        awaitApplication();
        assertEquals("Storage did not survive force-stop",
                "\"durable\"", eval("localStorage.getItem('folilune_android_qa')"));
        eval("document.querySelector('[data-view=library]').click();true");
        assertTrue("Previously imported EPUB was not restored from IndexedDB",
                waitFor("document.body.innerText.includes('FOLILUNE Tablet QA')", 30));
        assertEquals("true", eval("(()=>{const b=[...document.querySelectorAll('[data-book]')].find(x=>x.innerText.includes('FOLILUNE Tablet QA'));if(!b)return false;b.click();return true})()"));
        assertTrue("Reopened EPUB did not load in the actual browser renderer",
                waitFor("!!document.querySelector('#epubStage iframe') && document.body.innerText.includes('FOLILUNE Tablet QA')", 45));
    }
}
