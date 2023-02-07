package com.ogmatechlab.bombaysattaking

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.ogmatechlab.bombaysattaking.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    private lateinit var mainBinding: ActivityMainBinding

    companion object {
        private const val splashTimeout: Long = 1500
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            Intent(applicationContext, PlayGame::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }.also {
                startActivity(it)
            }
        }, splashTimeout)
    }

}