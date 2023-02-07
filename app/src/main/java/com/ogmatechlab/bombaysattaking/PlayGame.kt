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
        Glide.with(this).asGif().load(R.raw.gif_down).into(playGameBinding.imgAnimation1)
        Glide.with(this).asGif().load(R.raw.gif_up).into(playGameBinding.imgAnimation2)
        Glide.with(this).asGif().load(R.raw.gif_down).into(playGameBinding.imgAnimation3)
        Glide.with(this).asGif().load(R.raw.gif_up).into(playGameBinding.imgAnimation4)
        Glide.with(this).asGif().load(R.raw.gif_down).into(playGameBinding.imgAnimation5)
        Glide.with(this).asGif().load(R.raw.gif_up).into(playGameBinding.imgAnimation6)

        playGameBinding.btn.setOnClickListener {
            Glide.with(this).asBitmap().load(R.drawable.five).into(playGameBinding.imgAnimation1)
            Glide.with(this).asBitmap().load(R.raw.gif_up).into(playGameBinding.imgAnimation2)
            Glide.with(this).asBitmap().load(R.raw.gif_down).into(playGameBinding.imgAnimation3)
            Glide.with(this).asBitmap().load(R.raw.gif_up).into(playGameBinding.imgAnimation4)
            Glide.with(this).asBitmap().load(R.raw.gif_down).into(playGameBinding.imgAnimation5)
            Glide.with(this).asBitmap().load(R.raw.gif_up).into(playGameBinding.imgAnimation6)
            /*Glide.with(this)
                .asGif()
                .load(R.raw.gif_down)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .listener(object : RequestListener<GifDrawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<GifDrawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(
                        resource: GifDrawable?,
                        model: Any?,
                        target: Target<GifDrawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {

                        return false
                    }

                }).into(playGameBinding.imgAnimation1)*/
        }

    }
}