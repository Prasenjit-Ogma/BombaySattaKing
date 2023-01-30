package com.ogmatechlab.bombaysattaking

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.WindowManager
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.ogmatechlab.bombaysattaking.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {
    companion object {
        private const val EXTRA_URL = "https://www.ogmatechlab.com/mumbaiking/"
    }

    private lateinit var webViewBinding: ActivityWebViewBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        webViewBinding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(webViewBinding.root)

        webViewBinding.webView.webChromeClient = WebChromeClient()
        webViewBinding.webView.webViewClient = WebViewClient()
        webViewBinding.webView.settings.javaScriptEnabled = true
        webViewBinding.webView.settings.allowContentAccess = true
        webViewBinding.webView.settings.allowFileAccess = true
        webViewBinding.webView.settings.domStorageEnabled = true
        webViewBinding.webView.loadUrl(EXTRA_URL)

        window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        webViewBinding.webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                super.onPageFinished(view, url)
            }
        }

    }
}