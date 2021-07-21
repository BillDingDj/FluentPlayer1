package com.example.fluentplayer.views

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.view.updateLayoutParams
import com.bumptech.glide.Glide
import com.example.fluentplayer.R
import com.example.fluentplayer.utils.DeviceUtils

class VideoItemView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = -1,
) : FrameLayout(context, attributeSet, defStyleAttr) {
    var mImageView: ImageView?  = null
    init {
        LayoutInflater.from(context).inflate(R.layout.view_video_item, this, true)
        mImageView = findViewById(R.id.imageView)
        mImageView?.updateLayoutParams<LayoutParams> {
            val length = DeviceUtils.getScreenWidth(context) / 3
            height = length
            width = length
        }
    }

    fun setUri(uri: Uri) {
        mImageView?.let {
            Glide.with(context)
                .load(uri)
                .fitCenter()
                .into(it)
        }
    }
}