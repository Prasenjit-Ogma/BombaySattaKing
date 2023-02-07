package com.ogmatechlab.bombaysattaking

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ogmatechlab.bombaysattaking.databinding.ActivityPlayGameBinding

class PlayGame : AppCompatActivity() {
    private lateinit var playGameBinding: ActivityPlayGameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        playGameBinding = ActivityPlayGameBinding.inflate(layoutInflater)
        setContentView(playGameBinding.root)
    }
}