package com.ogmatechlab.bombaysattaking.utils

import android.content.Context
import android.content.SharedPreferences

object SharedStorage {
    private const val LUCKY_NUM = "LUCKY_NUMBER"
    private const val LUCKY_PRIZE = "LUCKY_PRIZE"
    private lateinit var sharedPref: SharedPreferences

    fun storeLuckyNum(context: Context, luckyNumber: String) {
        sharedPref = context.getSharedPreferences(LUCKY_NUM, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString(LUCKY_NUM, luckyNumber)
        editor.apply()
    }

    fun getStoredLuckyNum(context: Context): String? {
        sharedPref = context.getSharedPreferences(LUCKY_NUM, Context.MODE_PRIVATE)
        return sharedPref.getString(LUCKY_NUM, "000000")
    }

    fun storeLuckyPrizeNum(context: Context, luckyPrize: String) {
        sharedPref = context.getSharedPreferences(LUCKY_PRIZE, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString(LUCKY_PRIZE, luckyPrize)
        editor.apply()
    }

    fun getStoredLuckyPrizeNum(context: Context): String? {
        sharedPref = context.getSharedPreferences(LUCKY_PRIZE, Context.MODE_PRIVATE)
        return sharedPref.getString(LUCKY_PRIZE, "00")
    }

}