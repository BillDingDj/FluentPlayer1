package com.example.fluentplayer.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.android.exoplayer2.MediaItem
import java.lang.ref.WeakReference

class MediaItemCollectViewModel(application: Application): AndroidViewModel(application) {

    private val contextRef = WeakReference(application.applicationContext)

    val mldMediaItemsVideo = MutableLiveData<MediaItem>()

    fun prepareVideoMediaItems() {
        val contentResolver = contextRef.get()?.contentResolver ?: return
        val cursor = contentResolver.query(

        )

    }
}