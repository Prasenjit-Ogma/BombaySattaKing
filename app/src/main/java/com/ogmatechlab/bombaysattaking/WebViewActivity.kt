package com.ogmatechlab.bombaysattaking

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import com.ogmatechlab.bombaysattaking.databinding.ActivityWebViewBinding
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class WebViewActivity : AppCompatActivity() {
    companion object {
        private const val EXTRA_URL = "https://www.ogmatechlab.com/mumbaiking/view_result.php"
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
            if (current == "$i:00:02" || current == "$i:30:02" && current != "20:30:01") {
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
        val timer = object : CountDownTimer(14500, 1000) {
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