package com.example.fluentplayer.entity

import android.net.Uri
import android.os.Parcelable

data class Video : Parcelable(
    val uri: Uri,
    val name: String,
    val duration: Int,
    val size: Int,
    val mimeType: String
)