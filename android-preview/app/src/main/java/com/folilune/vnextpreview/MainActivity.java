package com.folilune.vnextpreview;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.CookieManager;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

/**
 * Isolated FOLILUNE vNext visual preview.
 * Distinct application ID from com.folilune.tablet means no v12 books or bookmarks can be overwritten.
 * All remote HTTP(S) content is denied. Uses a bundled, original HTML presentation only.
 */
public final class MainActivity extends Activity {
    private static final String HOST = "appassets.androidplatform.net";
    private static final String PAGE = "https://" + HOST + "/preview/index.html";
    private WebView web;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        getWindow().setStatusBarColor(Color.rgb(245,242,234));
        getWindow().setNavigationBarColor(Color.rgb(245,242,234));
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        web = new WebView(this);
        web.setBackgroundColor(Color.rgb(245,242,234));
        setContentView(web);

        WebSettings settings = web.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setAllowFileAccess(false);
        settings.setAllowContentAccess(false);
        settings.setAllowFileAccessFromFileURLs(false);
        settings.setAllowUniversalAccessFromFileURLs(false);
        settings.setJavaScriptCanOpenWindowsAutomatically(false);
        settings.setSupportMultipleWindows(false);
        settings.setMixedContentMode(WebSettings.MIXED_CONTENT_NEVER_ALLOW);
        settings.setMediaPlaybackRequiresUserGesture(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) settings.setSafeBrowsingEnabled(true);
        CookieManager.getInstance().setAcceptThirdPartyCookies(web, false);

        web.setWebViewClient(new WebViewClient() {
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                Uri uri = request.getUrl();
                if ("https".equalsIgnoreCase(uri.getScheme())
                        && HOST.equalsIgnoreCase(uri.getHost())
                        && "/preview/index.html".equals(uri.getPath())) {
                    try {
                        InputStream html = getAssets().open("index.html");
                        WebResourceResponse response = new WebResourceResponse("text/html", "UTF-8", html);
                        response.setResponseHeaders(Collections.singletonMap("Cache-Control", "no-store"));
                        return response;
                    } catch (IOException e) {
                        Log.e("FOLILUNEPreview", "Bundled visual prototype missing", e);
                    }
                }
                String scheme = uri.getScheme();
                if ("https".equalsIgnoreCase(scheme) || "http".equalsIgnoreCase(scheme)) {
                    WebResourceResponse denied = new WebResourceResponse(
                            "text/plain", "UTF-8", new ByteArrayInputStream(new byte[0]));
                    denied.setStatusCodeAndReasonPhrase(403, "Offline preview");
                    return denied;
                }
                return null; // Browser-managed about:blank, data:, blob: and srcdoc iframes.
            }
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Uri uri = request.getUrl();
                // Only block external http(s). A sandboxed iframe uses about:srcdoc;
                // blocking non-network browser schemes makes the welcome screen blank.
                String scheme = uri.getScheme();
                if ("http".equalsIgnoreCase(scheme) || "https".equalsIgnoreCase(scheme)) {
                    return !PAGE.equals(uri.toString());
                }
                return false; // about:blank, about:srcdoc, blob: and data: are browser-managed.
            }
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request,
                    android.webkit.WebResourceError error) {
                if (request.isForMainFrame()) Log.e("FOLILUNEPreview", "Main page error: " + error.getDescription());
            }
        });

        web.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onConsoleMessage(ConsoleMessage message) {
                Log.d("FOLILUNEPreview", message.messageLevel() + ": " + message.message());
                return true;
            }
            @Override
            public void onPermissionRequest(PermissionRequest request) {
                request.deny();
            }
        });

        web.loadUrl(PAGE);
    }

    @Override
    public void onBackPressed() {
        // Visual prototype uses its own in-app Back/Library navigation.
        if (web != null && web.canGoBack()) web.goBack();
        else super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        if (web != null) { web.destroy(); web = null; }
        super.onDestroy();
    }
}
