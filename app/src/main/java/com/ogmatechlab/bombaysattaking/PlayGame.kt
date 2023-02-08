package com.ogmatechlab.bombaysattaking

import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.ogmatechlab.bombaysattaking.databinding.ActivityPlayGameBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class PlayGame : AppCompatActivity() {
    private lateinit var playGameBinding: ActivityPlayGameBinding
    private lateinit var formatter: DateTimeFormatter
    private lateinit var current: String
    private lateinit var formatter2: DateTimeFormatter
    private lateinit var current2: String
    private lateinit var player: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        playGameBinding = ActivityPlayGameBinding.inflate(layoutInflater)
        setContentView(playGameBinding.root)

        pauseRolling()
        Glide.with(this).asGif().load(R.raw.gif_color_down).into(playGameBinding.imgAnimation7)
        Glide.with(this).asGif().load(R.raw.gif_color_up).into(playGameBinding.imgAnimation8)

        playGameBinding.btn.setOnClickListener {
            /* Glide.with(this).asBitmap().load(R.drawable.num_five)
                 .into(playGameBinding.imgAnimation1)
             Glide.with(this).asBitmap().load(R.raw.gif_up).into(playGameBinding.imgAnimation2)
             Glide.with(this).asBitmap().load(R.raw.gif_down).into(playGameBinding.imgAnimation3)
             Glide.with(this).asBitmap().load(R.raw.gif_up).into(playGameBinding.imgAnimation4)
             Glide.with(this).asBitmap().load(R.raw.gif_down).into(playGameBinding.imgAnimation5)
             Glide.with(this).asBitmap().load(R.raw.gif_up).into(playGameBinding.imgAnimation6)*/
            /*Intent(applicationContext, WebViewActivity::class.java).also {
                startActivity(it)
            }*/
        }

        fetchTimeFromDevice()

    }

    private fun fetchTimeFromDevice() {
        val timer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
                current = LocalDateTime.now().format(formatter)
                formatter2 = DateTimeFormatter.ofPattern("HH:mm:ss")
                current2 = LocalDateTime.now().format(formatter2)
                playGameBinding.txtTimeStamp.text = current
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
            if (current2 == "$i:00:00" || current2 == "$i:22:00" && current2 != "20:30:00") {
                startRolling()
            }
        }
    }

    private fun startRolling() {
        Log.e("PRINT", "CALLED")
        val timer = object : CountDownTimer(17000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                Glide.with(this@PlayGame).asGif().load(R.raw.gif_down)
                    .into(playGameBinding.imgAnimation1)
                Glide.with(this@PlayGame).asGif().load(R.raw.gif_up)
                    .into(playGameBinding.imgAnimation2)
                Glide.with(this@PlayGame).asGif().load(R.raw.gif_down)
                    .into(playGameBinding.imgAnimation3)
                Glide.with(this@PlayGame).asGif().load(R.raw.gif_up)
                    .into(playGameBinding.imgAnimation4)
                Glide.with(this@PlayGame).asGif().load(R.raw.gif_down)
                    .into(playGameBinding.imgAnimation5)
                Glide.with(this@PlayGame).asGif().load(R.raw.gif_up)
                    .into(playGameBinding.imgAnimation6)
                playMusic()
            }

            override fun onFinish() {
                pauseRolling()
            }
        }
        timer.start()
    }

    private fun pauseRolling() {
        Glide.with(this).load(R.drawable.num_five).into(playGameBinding.imgAnimation1)
        Glide.with(this).load(R.drawable.num_six).into(playGameBinding.imgAnimation2)
        Glide.with(this).load(R.drawable.num_four).into(playGameBinding.imgAnimation3)
        Glide.with(this).load(R.drawable.num_two).into(playGameBinding.imgAnimation4)
        Glide.with(this).load(R.drawable.num_zero).into(playGameBinding.imgAnimation5)
        Glide.with(this).load(R.drawable.num_nine).into(playGameBinding.imgAnimation6)
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
        val timer = object : CountDownTimer(17000, 1000) {
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