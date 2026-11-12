package com.folilune.tablet;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.ConsoleMessage;
import android.webkit.DownloadListener;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.nio.charset.StandardCharsets;

public final class MainActivity extends Activity {
    private static final String TAG = "FOLILUNE";
    private static final int PICK_BOOK = 5041;
    // Same stable first-party HTTPS origin used by AndroidX WebViewAssetLoader.
    // This build serves the single bundled index.html via shouldInterceptRequest instead.
    private static final String BASE = "https://appassets.androidplatform.net/assets/index.html";
    private static final String HOST = "appassets.androidplatform.net";
    private WebView reader;
    private android.webkit.ValueCallback<Uri[]> selectedFile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.rgb(245, 242, 234));
        getWindow().setNavigationBarColor(Color.rgb(245, 242, 234));
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);

        reader = new WebView(this);
        reader.setBackgroundColor(Color.rgb(245, 242, 234));
        setContentView(reader);

        WebSettings settings = reader.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setAllowFileAccess(false);
        settings.setAllowContentAccess(true); // Android Documents provider URIs from user picker.
        settings.setAllowFileAccessFromFileURLs(false);
        settings.setAllowUniversalAccessFromFileURLs(false);
        settings.setJavaScriptCanOpenWindowsAutomatically(false);
        settings.setSupportMultipleWindows(false);
        settings.setMediaPlaybackRequiresUserGesture(true);
        settings.setMixedContentMode(WebSettings.MIXED_CONTENT_NEVER_ALLOW);
        settings.setDefaultTextEncodingName("UTF-8");
        // Marker prevents the HTML from incorrectly declaring a blob: backup download successful.
        settings.setUserAgentString(settings.getUserAgentString() + " FOLILUNE-Android-G1/13");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            settings.setSafeBrowsingEnabled(true);
        }
        CookieManager.getInstance().setAcceptThirdPartyCookies(reader, false);

        reader.setWebViewClient(new WebViewClient() {
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                Uri uri = request.getUrl();
                String scheme = uri.getScheme();
                if (!"http".equalsIgnoreCase(scheme) && !"https".equalsIgnoreCase(scheme)) {
                    return null; // blob:, data:, and internal URLs remain browser-managed.
                }
                if ("https".equalsIgnoreCase(scheme)
                        && HOST.equalsIgnoreCase(uri.getHost())
                        && "/assets/index.html".equals(uri.getPath())) {
                    try {
                        InputStream stream = getAssets().open("index.html");
                        WebResourceResponse response = new WebResourceResponse("text/html", "UTF-8", stream);
                        response.setResponseHeaders(Collections.singletonMap("Cache-Control", "no-store"));
                        return response;
                    } catch (IOException e) {
                        Log.e(TAG, "Bundled page not found", e);
                    }
                }
                // Additional first-party vNext presentation files. Fixed allowlist only;
                // all book URLs and unrelated network requests are still denied.
                if ("https".equalsIgnoreCase(scheme)
                        && HOST.equalsIgnoreCase(uri.getHost())) {
                    String assetName = null;
                    String mime = null;
                    if ("/assets/vnext-integrated.css".equals(uri.getPath())) {
                        assetName = "vnext-integrated.css";
                        mime = "text/css";
                    } else if ("/assets/vnext-integrated.js".equals(uri.getPath())) {
                        assetName = "vnext-integrated.js";
                        mime = "application/javascript";
                    } else if ("/assets/vnext-g2.css".equals(uri.getPath())) {
                        assetName = "vnext-g2.css";
                        mime = "text/css";
                    } else if ("/assets/vnext-g2.js".equals(uri.getPath())) {
                        assetName = "vnext-g2.js";
                        mime = "application/javascript";
                    }
                    if (assetName != null) {
                        try {
                            InputStream stream = getAssets().open(assetName);
                            WebResourceResponse response = new WebResourceResponse(mime, "UTF-8", stream);
                            response.setResponseHeaders(Collections.singletonMap("Cache-Control", "no-store"));
                            return response;
                        } catch (IOException e) {
                            Log.e(TAG, "vNext presentation asset missing: " + assetName, e);
                        }
                    }
                }
                // All remote HTTP(S) requests are blocked in this prototype.
                WebResourceResponse denied = new WebResourceResponse(
                        "text/plain", "UTF-8", new ByteArrayInputStream(new byte[0]));
                denied.setStatusCodeAndReasonPhrase(403, "Blocked");
                return denied;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Uri uri = request.getUrl();
                if ("https".equalsIgnoreCase(uri.getScheme())
                        && HOST.equalsIgnoreCase(uri.getHost())
                        && "/assets/index.html".equals(uri.getPath())) {
                    return false;
                }
                if ("http".equalsIgnoreCase(uri.getScheme()) || "https".equalsIgnoreCase(uri.getScheme())) {
                    // Don't expose a browser surface to third-party URLs inside the reading app.
                    Toast.makeText(MainActivity.this,
                            "External links are disabled in this offline test.", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest req,
                                        android.webkit.WebResourceError error) {
                if (req.isForMainFrame()) {
                    Log.e(TAG, "Main page error: " + error.getDescription());
                }
            }
        });

        reader.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onShowFileChooser(WebView webView,
                                             android.webkit.ValueCallback<Uri[]> callback,
                                             FileChooserParams fileChooserParams) {
                if (selectedFile != null) selectedFile.onReceiveValue(null);
                selectedFile = callback;
                try {
                    Intent picker = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    picker.addCategory(Intent.CATEGORY_OPENABLE);
                    // EPUB may be represented as application/zip or octet-stream by a provider.
                    // Use a broad picker; reader import validation remains in the app's parser.
                    picker.setType("*/*");
                    picker.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
                    startActivityForResult(picker, PICK_BOOK);
                    return true;
                } catch (ActivityNotFoundException ex) {
                    selectedFile.onReceiveValue(null);
                    selectedFile = null;
                    Toast.makeText(MainActivity.this,
                            "No document picker found on this device.", Toast.LENGTH_LONG).show();
                    return false;
                }
            }

            @Override
            public boolean onConsoleMessage(ConsoleMessage message) {
                Log.d(TAG, "JS " + message.messageLevel() + ": " + message.message());
                return true;
            }

            @Override
            public void onPermissionRequest(PermissionRequest request) {
                request.deny(); // No camera, mic, or other web permissions in this prototype.
            }
        });

        reader.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition,
                                        String mimetype, long contentLength) {
                Toast.makeText(MainActivity.this,
                        "Backup file downloads are not available in this test APK.",
                        Toast.LENGTH_LONG).show();
            }
        });

        reader.loadUrl(BASE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != PICK_BOOK) return;
        android.webkit.ValueCallback<Uri[]> callback = selectedFile;
        selectedFile = null;
        if (callback == null) return;
        Uri[] result = resultCode == RESULT_OK
                ? WebChromeClient.FileChooserParams.parseResult(resultCode, data)
                : null;
        callback.onReceiveValue(result);
    }

    @Override
    public void onBackPressed() {
        if (reader != null && reader.canGoBack()) {
            reader.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPause() {
        if (reader != null) reader.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (reader != null) reader.onResume();
    }

    @Override
    protected void onDestroy() {
        if (selectedFile != null) {
            selectedFile.onReceiveValue(null);
            selectedFile = null;
        }
        if (reader != null) {
            reader.destroy();
            reader = null;
        }
        super.onDestroy();
    }
}
