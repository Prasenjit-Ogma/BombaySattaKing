package com.ogmatechlab.bombaysattaking.api.api_interface

import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.GET

interface APIInterface {
    @GET("/get_memes")
    fun fetchData(): Call<JsonElement>
}