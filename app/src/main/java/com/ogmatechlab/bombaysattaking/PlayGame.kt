package com.ogmatechlab.bombaysattaking

import android.content.Intent
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
    private lateinit var rand6Digit: String
    private val arraylist = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        playGameBinding = ActivityPlayGameBinding.inflate(layoutInflater)
        setContentView(playGameBinding.root)

        val str = "834215"

        Log.e("PRINT", "${str.toWords()}")

        for (i in str.toWords()) {
            Log.e("PRINT2", i)
            when (i.toInt()) {
                0 -> {
                    when (str.toWords().indexOf(i)) {
                        0 -> {
                            Glide.with(this).load(R.drawable.num_zero)
                                .into(playGameBinding.imgAnimation1)
                        }
                        1 -> {
                            Glide.with(this).load(R.drawable.num_zero)
                                .into(playGameBinding.imgAnimation2)
                        }
                        2 -> {
                            Glide.with(this).load(R.drawable.num_zero)
                                .into(playGameBinding.imgAnimation3)
                        }
                        3 -> {
                            Glide.with(this).load(R.drawable.num_zero)
                                .into(playGameBinding.imgAnimation4)
                        }
                        4 -> {
                            Glide.with(this).load(R.drawable.num_zero)
                                .into(playGameBinding.imgAnimation5)
                        }
                        5 -> {
                            Glide.with(this).load(R.drawable.num_zero)
                                .into(playGameBinding.imgAnimation6)
                        }
                    }
                }
                1 -> {
                    when (str.toWords().indexOf(i)) {
                        0 -> {
                            Glide.with(this).load(R.drawable.num_one)
                                .into(playGameBinding.imgAnimation1)
                        }
                        1 -> {
                            Glide.with(this).load(R.drawable.num_one)
                                .into(playGameBinding.imgAnimation2)
                        }
                        2 -> {
                            Glide.with(this).load(R.drawable.num_one)
                                .into(playGameBinding.imgAnimation3)
                        }
                        3 -> {
                            Glide.with(this).load(R.drawable.num_one)
                                .into(playGameBinding.imgAnimation4)
                        }
                        4 -> {
                            Glide.with(this).load(R.drawable.num_one)
                                .into(playGameBinding.imgAnimation5)
                        }
                        5 -> {
                            Glide.with(this).load(R.drawable.num_one)
                                .into(playGameBinding.imgAnimation6)
                        }
                    }
                }
                2 -> {
                    when (str.toWords().indexOf(i)) {
                        0 -> {
                            Glide.with(this).load(R.drawable.num_two)
                                .into(playGameBinding.imgAnimation1)
                        }
                        1 -> {
                            Glide.with(this).load(R.drawable.num_two)
                                .into(playGameBinding.imgAnimation2)
                        }
                        2 -> {
                            Glide.with(this).load(R.drawable.num_two)
                                .into(playGameBinding.imgAnimation3)
                        }
                        3 -> {
                            Glide.with(this).load(R.drawable.num_two)
                                .into(playGameBinding.imgAnimation4)
                        }
                        4 -> {
                            Glide.with(this).load(R.drawable.num_two)
                                .into(playGameBinding.imgAnimation5)
                        }
                        5 -> {
                            Glide.with(this).load(R.drawable.num_two)
                                .into(playGameBinding.imgAnimation6)
                        }
                    }
                }
                3 -> {
                    when (str.toWords().indexOf(i)) {
                        0 -> {
                            Glide.with(this).load(R.drawable.num_three)
                                .into(playGameBinding.imgAnimation1)
                        }
                        1 -> {
                            Glide.with(this).load(R.drawable.num_three)
                                .into(playGameBinding.imgAnimation2)
                        }
                        2 -> {
                            Glide.with(this).load(R.drawable.num_three)
                                .into(playGameBinding.imgAnimation3)
                        }
                        3 -> {
                            Glide.with(this).load(R.drawable.num_three)
                                .into(playGameBinding.imgAnimation4)
                        }
                        4 -> {
                            Glide.with(this).load(R.drawable.num_three)
                                .into(playGameBinding.imgAnimation5)
                        }
                        5 -> {
                            Glide.with(this).load(R.drawable.num_three)
                                .into(playGameBinding.imgAnimation6)
                        }
                    }
                }
                4 -> {
                    when (str.toWords().indexOf(i)) {
                        0 -> {
                            Glide.with(this).load(R.drawable.num_four)
                                .into(playGameBinding.imgAnimation1)
                        }
                        1 -> {
                            Glide.with(this).load(R.drawable.num_four)
                                .into(playGameBinding.imgAnimation2)
                        }
                        2 -> {
                            Glide.with(this).load(R.drawable.num_four)
                                .into(playGameBinding.imgAnimation3)
                        }
                        3 -> {
                            Glide.with(this).load(R.drawable.num_four)
                                .into(playGameBinding.imgAnimation4)
                        }
                        4 -> {
                            Glide.with(this).load(R.drawable.num_four)
                                .into(playGameBinding.imgAnimation5)
                        }
                        5 -> {
                            Glide.with(this).load(R.drawable.num_four)
                                .into(playGameBinding.imgAnimation6)
                        }
                    }
                }
                5 -> {
                    when (str.toWords().indexOf(i)) {
                        0 -> {
                            Glide.with(this).load(R.drawable.num_five)
                                .into(playGameBinding.imgAnimation1)
                        }
                        1 -> {
                            Glide.with(this).load(R.drawable.num_five)
                                .into(playGameBinding.imgAnimation2)
                        }
                        2 -> {
                            Glide.with(this).load(R.drawable.num_five)
                                .into(playGameBinding.imgAnimation3)
                        }
                        3 -> {
                            Glide.with(this).load(R.drawable.num_five)
                                .into(playGameBinding.imgAnimation4)
                        }
                        4 -> {
                            Glide.with(this).load(R.drawable.num_five)
                                .into(playGameBinding.imgAnimation5)
                        }
                        5 -> {
                            Glide.with(this).load(R.drawable.num_five)
                                .into(playGameBinding.imgAnimation6)
                        }
                    }
                }
                6 -> {
                    when (str.toWords().indexOf(i)) {
                        0 -> {
                            Glide.with(this).load(R.drawable.num_six)
                                .into(playGameBinding.imgAnimation1)
                        }
                        1 -> {
                            Glide.with(this).load(R.drawable.num_six)
                                .into(playGameBinding.imgAnimation2)
                        }
                        2 -> {
                            Glide.with(this).load(R.drawable.num_six)
                                .into(playGameBinding.imgAnimation3)
                        }
                        3 -> {
                            Glide.with(this).load(R.drawable.num_six)
                                .into(playGameBinding.imgAnimation4)
                        }
                        4 -> {
                            Glide.with(this).load(R.drawable.num_six)
                                .into(playGameBinding.imgAnimation5)
                        }
                        5 -> {
                            Glide.with(this).load(R.drawable.num_six)
                                .into(playGameBinding.imgAnimation6)
                        }
                    }
                }
                7 -> {
                    when (str.toWords().indexOf(i)) {
                        0 -> {
                            Glide.with(this).load(R.drawable.num_seven)
                                .into(playGameBinding.imgAnimation1)
                        }
                        1 -> {
                            Glide.with(this).load(R.drawable.num_seven)
                                .into(playGameBinding.imgAnimation2)
                        }
                        2 -> {
                            Glide.with(this).load(R.drawable.num_seven)
                                .into(playGameBinding.imgAnimation3)
                        }
                        3 -> {
                            Glide.with(this).load(R.drawable.num_seven)
                                .into(playGameBinding.imgAnimation4)
                        }
                        4 -> {
                            Glide.with(this).load(R.drawable.num_seven)
                                .into(playGameBinding.imgAnimation5)
                        }
                        5 -> {
                            Glide.with(this).load(R.drawable.num_seven)
                                .into(playGameBinding.imgAnimation6)
                        }
                    }
                }
                8 -> {
                    when (str.toWords().indexOf(i)) {
                        0 -> {
                            Glide.with(this).load(R.drawable.num_eight)
                                .into(playGameBinding.imgAnimation1)
                        }
                        1 -> {
                            Glide.with(this).load(R.drawable.num_eight)
                                .into(playGameBinding.imgAnimation2)
                        }
                        2 -> {
                            Glide.with(this).load(R.drawable.num_eight)
                                .into(playGameBinding.imgAnimation3)
                        }
                        3 -> {
                            Glide.with(this).load(R.drawable.num_eight)
                                .into(playGameBinding.imgAnimation4)
                        }
                        4 -> {
                            Glide.with(this).load(R.drawable.num_eight)
                                .into(playGameBinding.imgAnimation5)
                        }
                        5 -> {
                            Glide.with(this).load(R.drawable.num_eight)
                                .into(playGameBinding.imgAnimation6)
                        }
                    }
                }
                9 -> {
                    when (str.toWords().indexOf(i)) {
                        0 -> {
                            Glide.with(this).load(R.drawable.num_nine)
                                .into(playGameBinding.imgAnimation1)
                        }
                        1 -> {
                            Glide.with(this).load(R.drawable.num_nine)
                                .into(playGameBinding.imgAnimation2)
                        }
                        2 -> {
                            Glide.with(this).load(R.drawable.num_nine)
                                .into(playGameBinding.imgAnimation3)
                        }
                        3 -> {
                            Glide.with(this).load(R.drawable.num_nine)
                                .into(playGameBinding.imgAnimation4)
                        }
                        4 -> {
                            Glide.with(this).load(R.drawable.num_nine)
                                .into(playGameBinding.imgAnimation5)
                        }
                        5 -> {
                            Glide.with(this).load(R.drawable.num_nine)
                                .into(playGameBinding.imgAnimation6)
                        }
                    }
                }
            }
        }

        /*// split string by no space

        // split string by no space
        val strSplit = str.split("".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()

        // Now convert string into ArrayList

        // Now convert string into ArrayList
        val strList= ArrayList(
            listOf(strSplit)
        )

        // Now print the ArrayList

        // Now print the ArrayList
        for (s in strList){
            Log.e("PRINT","$s")
        }*/


        //pauseRolling()
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
            Intent(applicationContext, WebViewActivity::class.java).also {
                startActivity(it)
            }
        }

        fetchTimeFromDevice()

    }

    private fun String.toWords() = trim().splitToSequence("").filter { it.isNotEmpty() }.toList()

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
            if (current2 == "$i:00:00" || current2 == "$i:05:00" && current2 != "20:30:00") {
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