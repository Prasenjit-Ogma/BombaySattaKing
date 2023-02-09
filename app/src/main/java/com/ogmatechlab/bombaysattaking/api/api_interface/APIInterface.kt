package com.ogmatechlab.bombaysattaking.api.api_interface

import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.POST

interface APIInterface {
    @POST("/get_memes")
    fun fetchData(): Call<JsonElement>
}