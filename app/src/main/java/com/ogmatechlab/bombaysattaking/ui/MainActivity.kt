package com.ogmatechlab.bombaysattaking.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.ogmatechlab.bombaysattaking.api.APIClient
import com.ogmatechlab.bombaysattaking.databinding.ActivityMainBinding
import com.ogmatechlab.bombaysattaking.utils.Constants
import com.ogmatechlab.bombaysattaking.utils.NetworkInfo
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {


    private lateinit var mainBinding: ActivityMainBinding

    companion object {
        const val LUCKY_NUM = "LUCKY_NUM"
        const val WINNER_NUM = "WINNER_NUM"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        if (NetworkInfo.isNetworkAvailable(this)) {
            getTimeFromServer()
            mainBinding.cardLoader.visibility = View.VISIBLE
        } else {
            showAlertMsg("No Internet Connection")
        }
    }

    private fun getTimeFromServer() {
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
                    fetchedTimeFromServer(jsonObjectDateTime)

                } else {
                    showAlertMsg("Server is under maintenance. Please try after sometime.")
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                showAlertMsg("Server is under maintenance. Please try after sometime.")
            }

        })
    }

    private fun fetchedTimeFromServer(serverDateTime: String) {
        val getCurrentMinutes = Constants.getDesiredDateTimeFormat(
            dateTimeString = serverDateTime,
            inputFormatDateTime = Constants.DATE_INPUT_FORMAT,
            outputFormatDateTime = Constants.TIME_IN_MIN
        )
        if (getCurrentMinutes == "00" || getCurrentMinutes == "30") {
            val getSeconds = Constants.getDesiredDateTimeFormat(
                dateTimeString = serverDateTime,
                inputFormatDateTime = Constants.DATE_INPUT_FORMAT,
                outputFormatDateTime = Constants.TIME_IN_SEC
            )
            //Log.e("PRINT", "$getSeconds sec")
            if (getSeconds.toInt() < 8) {
                val convertedToMilis = (8 - getSeconds.toLong()) * 1000
                //Log.e("SHOW", "$convertedToMilis")
                Handler(Looper.getMainLooper()).postDelayed({
                    fetchDataServer(serverDateTime)
                }, convertedToMilis)
            } else {
                fetchDataServer(serverDateTime)
            }
        } else {
            fetchDataServer(serverDateTime)
        }
    }

    private fun fetchDataServer(serverDateTime: String) {
        if (NetworkInfo.isNetworkAvailable(this)) {
            callAPI(serverDateTime)
        } else {
            showAlertMsg("No Internet Connection")
        }
    }

    private fun showAlertMsg(alertMsg: String) {
        MaterialAlertDialogBuilder(this@MainActivity).apply {
            setTitle("Alert")
            setMessage(alertMsg)
            setPositiveButton(android.R.string.ok) { _, _ ->
                finish()
            }
            setCancelable(false)
            show()
        }
    }

    private fun callAPI(serverDateTime: String) {
        val apiInterface = APIClient.callClient().fetchData(
            datetime = Constants.getDesiredDateTimeFormat(
                dateTimeString = serverDateTime,
                inputFormatDateTime = Constants.DATE_INPUT_FORMAT,
                outputFormatDateTime = Constants.DATE_OUTPUT_FORMAT
            )
        )
        apiInterface.enqueue(object : Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                //Log.e("CHECK", "${response.raw()}")
                if (response.isSuccessful) {
                    val jsonObject = JSONObject(
                        GsonBuilder().serializeNulls().create().toJson(response.body())
                    )
                    Log.e("PRINT", jsonObject.toString())
                    val jsonObjectTwo = jsonObject.getJSONObject("data")
                    val randomNumber = jsonObjectTwo.getString("random_number")
                    var winnerNumber = jsonObjectTwo.getString("winner_number")
                    val status = jsonObject.getString("status")

                    if (winnerNumber == "null") {
                        winnerNumber = "00"
                    } else if (winnerNumber.toInt() <= 9) {
                        winnerNumber = "0$winnerNumber"
                    }
                    if (status.equals("1")) {
                        mainBinding.cardLoader.visibility = View.GONE
                        Intent(applicationContext, PlayGame::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            putExtra(LUCKY_NUM, randomNumber)
                            putExtra(WINNER_NUM, winnerNumber)
                        }.also {
                            startActivity(it)
                        }
                    } else {
                        showAlertMsg("Server is under maintenance. Please try after sometime.")
                    }
                } else {
                    showAlertMsg("Server is under maintenance. Please try after sometime.")
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                showAlertMsg("Server is under maintenance. Please try after sometime.")
            }

        })
    }

}