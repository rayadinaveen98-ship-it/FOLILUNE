package com.folilune.vnextpreview;

import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class PreviewSmokeTest {
    @Rule
    public ActivityScenarioRule<MainActivity> scenario =
            new ActivityScenarioRule<>(MainActivity.class);

    private String js(String expression) throws Exception {
        CountDownLatch finished = new CountDownLatch(1);
        AtomicReference<String> result = new AtomicReference<>();
        scenario.getScenario().onActivity(a -> {
            ViewGroup root = a.findViewById(android.R.id.content);
            WebView web = (WebView) root.getChildAt(0);
            web.evaluateJavascript(expression, value -> {
                result.set(value);
                finished.countDown();
            });
        });
        assertTrue("WebView did not return JS evaluation", finished.await(20, TimeUnit.SECONDS));
        return result.get();
    }

    private void waitFor(String expression) throws Exception {
        long until = System.currentTimeMillis() + 30000;
        while (System.currentTimeMillis() < until) {
            if ("true".equals(js(expression))) return;
            Thread.sleep(250);
        }
        fail("Visual preview did not reach expected WebView state: " + expression);
    }

    @Test
    public void seedOfflineWelcomeAndStorage() throws Exception {
        waitFor("document.readyState==='complete' && !!window.FOLILUNE_PREVIEW"
                + " && !!document.querySelector('#eWelcome iframe')");
        waitFor("window.__foliluneWelcomeFrameReady===true");
        assertEquals("true", js("location.origin==='https://appassets.androidplatform.net'"));
        assertEquals("true", js("document.querySelector('#eWelcome').hidden===false"));
        assertEquals("true", js("(()=>{const f=document.querySelector('#eWelcome iframe');"
                + "return f.getAttribute('sandbox')==='allow-scripts'"
                + " && f.clientWidth>150 && f.clientHeight>200"
                + " && f.getAttribute('srcdoc')?.includes('Skip intro')})()"));
        assertEquals("true", js("document.title.includes('FOLILUNE')"));
        assertEquals("true", js("(()=>{localStorage.setItem('folilune_preview_android_qa','survives');"
                + "return localStorage.getItem('folilune_preview_android_qa')==='survives'})()"));
    }

    @Test
    public void verifyStorageAndMainAppStillLoads() throws Exception {
        waitFor("document.readyState==='complete' && !!window.FOLILUNE_PREVIEW");
        waitFor("window.__foliluneWelcomeFrameReady===true");
        assertEquals("\"survives\"", js("localStorage.getItem('folilune_preview_android_qa')"));
        assertEquals("true", js("!!document.querySelector('#app') && !!document.querySelector('.top-right')"));
        assertEquals("true", js("!!document.querySelector('#eReplay')"));
        assertEquals("true", js("!!document.querySelector('#eWelcome iframe')"));
    }
}
