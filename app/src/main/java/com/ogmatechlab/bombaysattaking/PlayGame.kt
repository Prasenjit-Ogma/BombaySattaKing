package com.ogmatechlab.bombaysattaking

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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        playGameBinding = ActivityPlayGameBinding.inflate(layoutInflater)
        setContentView(playGameBinding.root)
        Glide.with(this).asGif().load(R.raw.gif_down).into(playGameBinding.imgAnimation1)
        Glide.with(this).asGif().load(R.raw.gif_up).into(playGameBinding.imgAnimation2)
        Glide.with(this).asGif().load(R.raw.gif_down).into(playGameBinding.imgAnimation3)
        Glide.with(this).asGif().load(R.raw.gif_up).into(playGameBinding.imgAnimation4)
        Glide.with(this).asGif().load(R.raw.gif_down).into(playGameBinding.imgAnimation5)
        Glide.with(this).asGif().load(R.raw.gif_up).into(playGameBinding.imgAnimation6)
        Glide.with(this).asGif().load(R.raw.gif_color_down).into(playGameBinding.imgAnimation7)
        Glide.with(this).asGif().load(R.raw.gif_color_up).into(playGameBinding.imgAnimation8)

        playGameBinding.btn.setOnClickListener {
            Glide.with(this).asBitmap().load(R.drawable.num_five)
                .into(playGameBinding.imgAnimation1)
            Glide.with(this).asBitmap().load(R.raw.gif_up).into(playGameBinding.imgAnimation2)
            Glide.with(this).asBitmap().load(R.raw.gif_down).into(playGameBinding.imgAnimation3)
            Glide.with(this).asBitmap().load(R.raw.gif_up).into(playGameBinding.imgAnimation4)
            Glide.with(this).asBitmap().load(R.raw.gif_down).into(playGameBinding.imgAnimation5)
            Glide.with(this).asBitmap().load(R.raw.gif_up).into(playGameBinding.imgAnimation6)
        }

        fetchTimeFromDevice()

    }

    private fun fetchTimeFromDevice() {
        val timer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
                current = LocalDateTime.now().format(formatter)
                playGameBinding.txtTimeStamp.text = current
                Log.e("PRINT", current)
                //checkCurrentTime()
            }

            override fun onFinish() {
                fetchTimeFromDevice()
            }
        }
        timer.start()
    }

}