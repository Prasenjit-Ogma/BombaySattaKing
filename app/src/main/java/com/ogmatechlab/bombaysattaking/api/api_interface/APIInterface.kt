package com.ogmatechlab.bombaysattaking.api.api_interface

import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIInterface {
    @GET("/mumbaiking/api.php?action=getWinnerNumber")
    fun fetchData(@Query("datetime") datetime: String): Call<JsonElement>
}