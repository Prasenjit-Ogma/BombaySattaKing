package com.ogmatechlab.bombaysattaking.ui

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.ogmatechlab.bombaysattaking.R
import com.ogmatechlab.bombaysattaking.api.APIClient
import com.ogmatechlab.bombaysattaking.databinding.ActivityPlayGameBinding
import com.ogmatechlab.bombaysattaking.utils.NetworkInfo
import com.ogmatechlab.bombaysattaking.utils.SharedStorage
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class PlayGame : AppCompatActivity() {
    private lateinit var playGameBinding: ActivityPlayGameBinding
    private lateinit var formatter: DateTimeFormatter
    private lateinit var current: String
    private lateinit var formatter2: DateTimeFormatter
    private lateinit var current2: String
    private lateinit var player: MediaPlayer

    val images_of_number = IntArray(10)
    val lucky_images_of_number = IntArray(10)
    val imageviews_prize_number: Array<ShapeableImageView?> = arrayOfNulls(6)
    val imageviews_lucky_number: Array<ShapeableImageView?> = arrayOfNulls(2)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        playGameBinding = ActivityPlayGameBinding.inflate(layoutInflater)
        setContentView(playGameBinding.root)

        images_of_number[0] = R.drawable.num_zero
        images_of_number[1] = R.drawable.num_one
        images_of_number[2] = R.drawable.num_two
        images_of_number[3] = R.drawable.num_three
        images_of_number[4] = R.drawable.num_four
        images_of_number[5] = R.drawable.num_five
        images_of_number[6] = R.drawable.num_six
        images_of_number[7] = R.drawable.num_seven
        images_of_number[8] = R.drawable.num_eight
        images_of_number[9] = R.drawable.num_nine

        lucky_images_of_number[0] = R.drawable.lg_num_zero
        lucky_images_of_number[1] = R.drawable.lg_num_one
        lucky_images_of_number[2] = R.drawable.lg_num_two
        lucky_images_of_number[3] = R.drawable.lg_num_three
        lucky_images_of_number[4] = R.drawable.lg_num_four
        lucky_images_of_number[5] = R.drawable.lg_num_five
        lucky_images_of_number[6] = R.drawable.lg_num_six
        lucky_images_of_number[7] = R.drawable.lg_num_seven
        lucky_images_of_number[8] = R.drawable.lg_num_eight
        lucky_images_of_number[9] = R.drawable.lg_num_nine

        imageviews_prize_number[0] = playGameBinding.imgAnimation1
        imageviews_prize_number[1] = playGameBinding.imgAnimation2
        imageviews_prize_number[2] = playGameBinding.imgAnimation3
        imageviews_prize_number[3] = playGameBinding.imgAnimation4
        imageviews_prize_number[4] = playGameBinding.imgAnimation5
        imageviews_prize_number[5] = playGameBinding.imgAnimation6

        imageviews_lucky_number[0] = playGameBinding.imgAnimation7
        imageviews_lucky_number[1] = playGameBinding.imgAnimation8

        SharedStorage.storeLuckyNum(this@PlayGame, "343299")
        SharedStorage.storeLuckyPrizeNum(this@PlayGame, "01")

        pauseRolling()

        playGameBinding.btn.setOnClickListener {
            Intent(applicationContext, WebViewActivity::class.java).also {
                startActivity(it)
            }
        }

        fetchTimeFromDevice()

        if (NetworkInfo.isNetworkAvailable(this)) {
            callAPI()
        }

        playGameBinding.imgReload.setOnClickListener {
            if (NetworkInfo.isNetworkAvailable(this)) {
                callAPI()
            }
        }

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
            if (current2 == "$i:00:00" || current2 == "$i:30:00" && current2 != "20:30:00") {
                startRolling()
            }
        }
    }

    private fun startRolling() {
        val timer = object : CountDownTimer(20000, 1000) {
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
                Glide.with(this@PlayGame).asGif().load(R.raw.gif_color_down)
                    .into(playGameBinding.imgAnimation7)
                Glide.with(this@PlayGame).asGif().load(R.raw.gif_color_up)
                    .into(playGameBinding.imgAnimation8)
                playMusic()
            }

            override fun onFinish() {
                SharedStorage.storeLuckyNum(this@PlayGame, "888888")
                SharedStorage.storeLuckyPrizeNum(this@PlayGame, "22")
                pauseRolling()
            }
        }
        timer.start()
    }

    private fun pauseRolling() {
        SharedStorage.getStoredLuckyNum(this@PlayGame)?.let {
            for (j in it.indices) {
                for (i in images_of_number.indices) {
                    if (it[j].digitToInt() == i) {
                        imageviews_prize_number[j]?.let {
                            Glide.with(this).asBitmap().load(images_of_number[i]).into(it)
                        }
                    }
                }
            }
        }

        SharedStorage.getStoredLuckyPrizeNum(this@PlayGame)?.let {
            for (j in it.indices) {
                for (i in lucky_images_of_number.indices) {
                    if (it[j].digitToInt() == i) {
                        imageviews_lucky_number[j]?.let {
                            Glide.with(this).asBitmap().load(lucky_images_of_number[i]).into(it)
                        }
                    }
                }
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
        val timer = object : CountDownTimer(20000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                player.also {
                    it.start()
                    it.isLooping = false
                }
            }

            override fun onFinish() {
                player.reset()

            }
        }
        timer.start()
    }

    private fun callAPI() {
        val apiInterface = APIClient.callClient().fetchData()
        apiInterface.enqueue(object : Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                //Log.e("CHECK", response.code().toString())
                if (response.isSuccessful) {
                    val jsonObject = JSONObject(
                        GsonBuilder().serializeNulls().create().toJson(response.body())
                    )


                } else {
                    Toast.makeText(this@PlayGame, "Server Issue", Toast.LENGTH_LONG).show()
                }

            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Toast.makeText(this@PlayGame, "Server Issue", Toast.LENGTH_LONG).show()
            }

        })
    }

}