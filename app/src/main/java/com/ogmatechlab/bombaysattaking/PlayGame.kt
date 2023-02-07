package com.ogmatechlab.bombaysattaking

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.ogmatechlab.bombaysattaking.databinding.ActivityPlayGameBinding

class PlayGame : AppCompatActivity() {
    private lateinit var playGameBinding: ActivityPlayGameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        playGameBinding = ActivityPlayGameBinding.inflate(layoutInflater)
        setContentView(playGameBinding.root)
        Glide.with(this).load(R.raw.gif_down).into(playGameBinding.imgAnimation1)
        Glide.with(this).load(R.raw.gif_up).into(playGameBinding.imgAnimation2)
        Glide.with(this).load(R.raw.gif_down).into(playGameBinding.imgAnimation3)
        Glide.with(this).load(R.raw.gif_up).into(playGameBinding.imgAnimation4)
        Glide.with(this).load(R.raw.gif_down).into(playGameBinding.imgAnimation5)
        Glide.with(this).load(R.raw.gif_up).into(playGameBinding.imgAnimation6)
    }
}