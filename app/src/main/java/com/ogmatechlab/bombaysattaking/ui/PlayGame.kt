package com.ogmatechlab.bombaysattaking.ui

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.imageview.ShapeableImageView
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.ogmatechlab.bombaysattaking.R
import com.ogmatechlab.bombaysattaking.api.APIClient
import com.ogmatechlab.bombaysattaking.databinding.ActivityPlayGameBinding
import com.ogmatechlab.bombaysattaking.utils.NetworkInfo
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
    private lateinit var formatter3: DateTimeFormatter
    private lateinit var current3: String
    private lateinit var player: MediaPlayer

    private val imagesOfNumber = IntArray(10)
    private val luckyImagesOfNumber = IntArray(10)
    private val imageviewsPrizeNumber: Array<ShapeableImageView?> = arrayOfNulls(6)
    private val imageviewsLuckyNumber: Array<ShapeableImageView?> = arrayOfNulls(2)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        playGameBinding = ActivityPlayGameBinding.inflate(layoutInflater)
        setContentView(playGameBinding.root)

        imagesOfNumber[0] = R.drawable.num_zero
        imagesOfNumber[1] = R.drawable.num_one
        imagesOfNumber[2] = R.drawable.num_two
        imagesOfNumber[3] = R.drawable.num_three
        imagesOfNumber[4] = R.drawable.num_four
        imagesOfNumber[5] = R.drawable.num_five
        imagesOfNumber[6] = R.drawable.num_six
        imagesOfNumber[7] = R.drawable.num_seven
        imagesOfNumber[8] = R.drawable.num_eight
        imagesOfNumber[9] = R.drawable.num_nine

        luckyImagesOfNumber[0] = R.drawable.lg_num_zero
        luckyImagesOfNumber[1] = R.drawable.lg_num_one
        luckyImagesOfNumber[2] = R.drawable.lg_num_two
        luckyImagesOfNumber[3] = R.drawable.lg_num_three
        luckyImagesOfNumber[4] = R.drawable.lg_num_four
        luckyImagesOfNumber[5] = R.drawable.lg_num_five
        luckyImagesOfNumber[6] = R.drawable.lg_num_six
        luckyImagesOfNumber[7] = R.drawable.lg_num_seven
        luckyImagesOfNumber[8] = R.drawable.lg_num_eight
        luckyImagesOfNumber[9] = R.drawable.lg_num_nine

        imageviewsPrizeNumber[0] = playGameBinding.imgAnimation1
        imageviewsPrizeNumber[1] = playGameBinding.imgAnimation2
        imageviewsPrizeNumber[2] = playGameBinding.imgAnimation3
        imageviewsPrizeNumber[3] = playGameBinding.imgAnimation4
        imageviewsPrizeNumber[4] = playGameBinding.imgAnimation5
        imageviewsPrizeNumber[5] = playGameBinding.imgAnimation6

        imageviewsLuckyNumber[0] = playGameBinding.imgAnimation7
        imageviewsLuckyNumber[1] = playGameBinding.imgAnimation8

        playGameBinding.btn.setOnClickListener {
            Intent(applicationContext, WebViewActivity::class.java).also {
                startActivity(it)
            }
        }

        fetchTimeFromDevice()

        fetchDataServer(false)

        playGameBinding.imgReload.setOnClickListener {
            fetchDataServer(false)
        }

    }

    private fun fetchDataServer(isRolling: Boolean) {
        if (NetworkInfo.isNetworkAvailable(this)) {
            if (!isRolling) {
                playGameBinding.cardLoader.visibility = View.VISIBLE
            }
            callAPI()
        } else {
            showAlertMsg("Internet Connection no found")
        }
    }

    private fun showAlertMsg(alertMsg: String) {
        MaterialAlertDialogBuilder(this@PlayGame).apply {
            setTitle("Alert")
            setMessage(alertMsg)
            setPositiveButton(android.R.string.ok) { _, _ ->
                finish()
            }
            setCancelable(false)
            show()
        }
    }

    private fun fetchTimeFromDevice() {
        val timer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
                current = LocalDateTime.now().format(formatter)
                formatter2 = DateTimeFormatter.ofPattern("HH:mm:ss")
                current2 = LocalDateTime.now().format(formatter2)
                formatter3 = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss")
                current3 = LocalDateTime.now().format(formatter3)
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
                fetchDataServer(true)
            }
        }
        timer.start()
    }

    private fun pauseRolling(randomNumber: String, winnerNumber: String) {
        randomNumber.let {
            for (j in it.indices) {
                for (i in imagesOfNumber.indices) {
                    if (it[j].digitToInt() == i) {
                        imageviewsPrizeNumber[j]?.let { imgView ->
                            Glide.with(this).asBitmap().load(imagesOfNumber[i]).into(imgView)
                        }
                    }
                }
            }
        }

        winnerNumber.let {
            for (j in it.indices) {
                for (i in luckyImagesOfNumber.indices) {
                    if (it[j].digitToInt() == i) {
                        imageviewsLuckyNumber[j]?.let { luckyImgView ->
                            Glide.with(this).asBitmap().load(luckyImagesOfNumber[i])
                                .into(luckyImgView)
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
        val timer = object : CountDownTimer(22000, 1000) {
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
        val apiInterface = APIClient.callClient().fetchData(
            datetime = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss"))
        )
        apiInterface.enqueue(object : Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                //Log.e("CHECK", response.code().toString())
                if (response.isSuccessful) {
                    val jsonObject = JSONObject(
                        GsonBuilder().serializeNulls().create().toJson(response.body())
                    )
                    val jsonObjectTwo = jsonObject.getJSONObject("data")
                    val randomNumber = jsonObjectTwo.getString("random_number")
                    var winnerNumber = jsonObjectTwo.getString("winner_number")
                    if (winnerNumber == "null") {
                        winnerNumber = "00"
                    }

                    pauseRolling(randomNumber, winnerNumber)

                    playGameBinding.cardLoader.visibility = View.GONE

                } else {
                    showAlertMsg("Server Issue!! Unable to fetch data")
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                showAlertMsg("Server Issue!! Unable to fetch data")
            }

        })
    }

}