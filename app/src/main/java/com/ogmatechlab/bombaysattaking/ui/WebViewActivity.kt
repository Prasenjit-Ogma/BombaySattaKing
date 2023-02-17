package com.ogmatechlab.bombaysattaking.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import com.ogmatechlab.bombaysattaking.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {
    companion object {
        private const val EXTRA_URL = "https://www.ogmatechlab.com/mumbaiking/view_result_app.php"
    }

    private lateinit var webViewBinding: ActivityWebViewBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        webViewBinding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(webViewBinding.root)

        with(webViewBinding) {

            toolbar.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }

            progressBar.visibility = View.VISIBLE

            // Clear all the Application Cache, Web SQL Database and the HTML5 Web Storage
            WebStorage.getInstance().deleteAllData()

            // Clear all the cookies
            CookieManager.getInstance().removeAllCookies(null)
            CookieManager.getInstance().flush()

            webView.webChromeClient = WebChromeClient()
            webView.webViewClient = WebViewClient()
            webView.settings.javaScriptEnabled = true
            webView.settings.allowContentAccess = true
            webView.settings.allowFileAccess = true
            webView.settings.domStorageEnabled = false
            webView.settings.mediaPlaybackRequiresUserGesture = true
            webView.settings.cacheMode = WebSettings.LOAD_NO_CACHE
            webView.loadUrl(EXTRA_URL)


            window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            webView.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                    super.onPageFinished(view, url)
                    progressBar.visibility = View.GONE
                }
            }
        }

    }
}