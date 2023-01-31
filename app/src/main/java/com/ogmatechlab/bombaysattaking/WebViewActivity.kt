package com.ogmatechlab.bombaysattaking

import android.annotation.SuppressLint
import android.media.MediaPlayer
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
    private lateinit var player: MediaPlayer

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
        webViewBinding.webView.settings.mediaPlaybackRequiresUserGesture = true
        webViewBinding.webView.loadUrl(EXTRA_URL)

        window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        webViewBinding.webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                super.onPageFinished(view, url)
            }
        }
        playMusic()

    }

    override fun onPause() {
        super.onPause()
        player.stop()
    }

    private fun playMusic() {
        player = MediaPlayer.create(this, R.raw.machine_sound)
        player.also {
            it.start()
            it.isLooping = false
            it.setVolume(50F, 50F)
        }
    }

}