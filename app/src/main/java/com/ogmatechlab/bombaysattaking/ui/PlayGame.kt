package com.ogmatechlab.bombaysattaking.ui

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
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
import com.ogmatechlab.bombaysattaking.utils.Constants
import com.ogmatechlab.bombaysattaking.utils.NetworkInfo
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PlayGame : AppCompatActivity() {
    private lateinit var playGameBinding: ActivityPlayGameBinding
    private var currentDateTime: String? = null
    private var currentTime: String? = null
    private var player: MediaPlayer? = null

    private val imagesOfNumber = IntArray(10)
    private val luckyImagesOfNumber = IntArray(10)
    private val imageviewsPrizeNumber: Array<ShapeableImageView?> = arrayOfNulls(6)
    private val imageviewsLuckyNumber: Array<ShapeableImageView?> = arrayOfNulls(2)
    private var timeMillis: Long? = null

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

        playGameBinding.imgReload.setOnClickListener {
            checkTimeNetwork(true)
        }

        checkTimeNetwork()

        pauseRolling("000000", "00")

    }

    private fun checkTimeNetwork(isReloaded: Boolean = false) {
        if (NetworkInfo.isNetworkAvailable(this)) {
            playGameBinding.cardLoader.visibility = View.VISIBLE
            getTimeFromServer(isReloaded)
        } else {
            showAlertMsg("No Internet Connection")
        }
    }

    private fun getTimeFromServer(isReloaded: Boolean) {
        val apiInterface = APIClient.callTimeAPIClient().fetchWorldTimeAPI()
        apiInterface.enqueue(object : Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                Log.e("CHECK", "${response.raw()}")
                if (response.isSuccessful) {
                    val jsonObject = JSONObject(
                        GsonBuilder().serializeNulls().create().toJson(response.body())
                    )
                    val jsonObjectDateTime =
                        jsonObject.getString("datetime").dropLast(13).replace("T", " ")
                            .replace("-", "/")
                    timeMillis =
                        Constants.getMillisFromDate(jsonObjectDateTime, Constants.DATE_INPUT_FORMAT)
                    if (isReloaded) fetchDataServer(timeMillis) else fetchedTimeFromServer()
                    val luckyNum = intent.getStringExtra(MainActivity.LUCKY_NUM)
                    val winnerNum = intent.getStringExtra(MainActivity.WINNER_NUM)

                    luckyNum?.let { luckNum ->
                        winnerNum?.let { winNum ->
                            pauseRolling(luckNum, winNum)
                        }
                    }
                    playGameBinding.cardLoader.visibility = View.GONE

                } else {
                    showAlertMsg("Server is under maintenance. Please try after sometime.")
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                showAlertMsg("Server is under maintenance. Please try after sometime.")
            }

        })
    }

    private fun fetchDataServer(timeMillis: Long?) {
        if (NetworkInfo.isNetworkAvailable(this)) {
            callAPI(timeMillis)
        } else {
            showAlertMsg("No Internet Connection")
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

    private fun fetchedTimeFromServer() {
        val timer = object : CountDownTimer(1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeMillis = timeMillis?.plus(1000)
                currentDateTime =
                    Constants.getDateTimeFromMillis(timeMillis, Constants.DATE_TIME_FORMAT)
                currentTime = Constants.getDateTimeFromMillis(timeMillis, Constants.TIME_FORMAT)
                playGameBinding.txtTimeStamp.text = currentDateTime
                checkCurrentTime()
            }

            override fun onFinish() {
                fetchedTimeFromServer()
            }
        }
        timer.start()
    }

    fun checkCurrentTime() {
        for (i in 10..20) {
            if (currentTime == "$i:00:00" || currentTime == "$i:30:00" && currentTime != "20:30:00") {
                startRolling(timeMillis)
            }
        }
    }

    private fun startRolling(timeMillis: Long?) {
        player = MediaPlayer.create(this, R.raw.machine_sound)

        val timer = object : CountDownTimer(8500, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                Glide.with(applicationContext).asGif().load(R.raw.gif_down)
                    .into(playGameBinding.imgAnimation1)
                Glide.with(applicationContext).asGif().load(R.raw.gif_up)
                    .into(playGameBinding.imgAnimation2)
                Glide.with(applicationContext).asGif().load(R.raw.gif_down)
                    .into(playGameBinding.imgAnimation3)
                Glide.with(applicationContext).asGif().load(R.raw.gif_up)
                    .into(playGameBinding.imgAnimation4)
                Glide.with(applicationContext).asGif().load(R.raw.gif_down)
                    .into(playGameBinding.imgAnimation5)
                Glide.with(applicationContext).asGif().load(R.raw.gif_up)
                    .into(playGameBinding.imgAnimation6)
                Glide.with(applicationContext).asGif().load(R.raw.gif_color_down)
                    .into(playGameBinding.imgAnimation7)
                Glide.with(applicationContext).asGif().load(R.raw.gif_color_up)
                    .into(playGameBinding.imgAnimation8)
                playMusic()
            }

            override fun onFinish() {
                fetchDataServer(timeMillis)
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
                            Glide.with(applicationContext).asBitmap().load(imagesOfNumber[i])
                                .into(imgView)
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
                            Glide.with(applicationContext).asBitmap().load(luckyImagesOfNumber[i])
                                .into(luckyImgView)
                        }
                    }
                }
            }
        }

    }

    private fun playMusic() {
        player?.let {
            it.also {
                it.start()
                it.isLooping = false
            }
        }
    }

    private fun callAPI(timeMillis: Long?) {
        val apiInterface = APIClient.callClient().fetchData(
            datetime = Constants.getDateTimeFromMillis(timeMillis, Constants.DATE_OUTPUT_FORMAT)!!
        )
        apiInterface.enqueue(object : Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                Log.e("CHECK", "${response.raw()}")
                if (response.isSuccessful) {
                    val jsonObject = JSONObject(
                        GsonBuilder().serializeNulls().create().toJson(response.body())
                    )
                    val jsonObjectTwo = jsonObject.getJSONObject("data")
                    val randomNumber = jsonObjectTwo.getString("random_number")
                    var winnerNumber = jsonObjectTwo.getString("winner_number")

                    if (winnerNumber == "null") {
                        winnerNumber = "00"
                    } else if (winnerNumber.toInt() <= 9) {
                        winnerNumber = "0$winnerNumber"
                    }

                    pauseRolling(randomNumber, winnerNumber)

                    player?.let {
                        it.also {
                            it.pause()
                            it.seekTo(0)
                            it.reset()
                        }
                    }

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

