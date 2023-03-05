package com.ogmatechlab.bombaysattaking.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.ogmatechlab.bombaysattaking.api.APIClient
import com.ogmatechlab.bombaysattaking.databinding.ActivityMainBinding
import com.ogmatechlab.bombaysattaking.utils.NetworkInfo
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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

        fetchTimeFromDevice()
    }

    private fun fetchTimeFromDevice() {
        val getCurrentMinutes = LocalDateTime.now().format(DateTimeFormatter.ofPattern("mm"))
        if (getCurrentMinutes == "00" || getCurrentMinutes == "30") {
            val getSeconds = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ss"))
            Log.e("PRINT", "$getSeconds sec")
            if (getSeconds.toInt() < 8) {
                val convertedToMilis = (8 - getSeconds.toLong()) * 1000
                Log.e("SHOW", "$convertedToMilis")
                Handler(Looper.getMainLooper()).postDelayed({
                    fetchDataServer()
                }, convertedToMilis)
            } else {
                fetchDataServer()
            }
        } else {
            fetchDataServer()
        }
    }

    private fun fetchDataServer() {
        if (NetworkInfo.isNetworkAvailable(this)) {
            callAPI()
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
                    } else if (winnerNumber.toInt() <= 9) {
                        winnerNumber = "0$winnerNumber"
                    }

                    Intent(applicationContext, PlayGame::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        putExtra(LUCKY_NUM, randomNumber)
                        putExtra(WINNER_NUM, winnerNumber)
                    }.also {
                        startActivity(it)
                    }

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