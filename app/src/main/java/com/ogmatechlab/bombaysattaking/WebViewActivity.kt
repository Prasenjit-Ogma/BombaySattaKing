package com.ogmatechlab.bombaysattaking

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.WindowManager
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.ogmatechlab.bombaysattaking.databinding.ActivityWebViewBinding
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class WebViewActivity : AppCompatActivity() {
    companion object {
        private const val EXTRA_URL = "https://www.ogmatechlab.com/mumbaiking/"
    }

    private lateinit var webViewBinding: ActivityWebViewBinding
    private lateinit var player: MediaPlayer
    private lateinit var formatter: DateTimeFormatter
    private lateinit var current: String

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


        fetchTimeFromDevice()

    }

    private fun fetchTimeFromDevice() {
        val timer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
                current = LocalTime.now().format(formatter)
                Log.e("PRINT", current)
                checkCurrentTime()
            }

            override fun onFinish() {
                fetchTimeFromDevice()
            }
        }
        timer.start()
    }

    fun checkCurrentTime() {
        for (i in 10..20) {
            if (current == "$i:00:01" || current == "$i:30:01" && current != "20:30:01") {
                playMusic()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        player = MediaPlayer.create(this, R.raw.machine_sound)
    }

    override fun onPause() {
        super.onPause()
        player.stop()
    }

    private fun playMusic() {
        val timer = object : CountDownTimer(19000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                player.also {
                    it.start()
                    it.isLooping = false
                }
            }

            override fun onFinish() {
                player.stop()
            }
        }
        timer.start()
    }

}