package com.example.fluentplayer.model

import android.app.Application
import android.content.ContentUris
import android.provider.MediaStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.fluentplayer.utils.ContentResolverUtils.getString
import com.google.android.exoplayer2.MediaItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.ref.WeakReference

class MediaItemCollectViewModel(application: Application) : AndroidViewModel(application) {

    companion object{
        private val VIDEO_PROJECTION = arrayOf(// 查询图片需要的数据列
            MediaStore.Video.VideoColumns.DISPLAY_NAME,
            MediaStore.Video.VideoColumns.SIZE,
            MediaStore.Video.VideoColumns.WIDTH,
            MediaStore.Video.VideoColumns.HEIGHT,
            MediaStore.Video.VideoColumns.MIME_TYPE,
            MediaStore.Video.VideoColumns.DURATION,
            MediaStore.Video.VideoColumns.ARTIST,
            MediaStore.Video.VideoColumns.DATE_ADDED,
            MediaStore.Files.FileColumns._ID
        )

        private const val ALL_VIDEO_SELECTION = "bucket_id != ? AND " + MediaStore.Video.Media.MIME_TYPE + " like ?"
    }

    private val contextRef = WeakReference(application.applicationContext)

    val mldMediaItemsVideos = MutableLiveData<ArrayList<MediaItem>>()

    /**
     * 协程，异步操作准备MediaItems
     */
    suspend fun prepareVideoMediaItems() {
        withContext(Dispatchers.IO) {
            val queryUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            val resolver = contextRef.get()?.contentResolver ?: return@withContext

            val cursor = resolver.query(
                queryUri,
                VIDEO_PROJECTION,
                ALL_VIDEO_SELECTION,
                arrayOf(
                    "video%"
                ),
                MediaStore.Files.FileColumns.DATE_ADDED + " desc"
            )

            val videosList = ArrayList<MediaItem>()
            cursor?.use { data ->
                while (data.moveToNext()) {
//                    val name = getString(data, MediaStore.Video.VideoColumns.DISPLAY_NAME)
                    val id = getString(data, MediaStore.Files.FileColumns._ID)
                    val uri = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id.toLong())

                    val mimeType = getString(data, MediaStore.Video.VideoColumns.MIME_TYPE)

//                    val size = getLong(data, MediaStore.Video.VideoColumns.SIZE)
//                    val width = getInt(data, MediaStore.Video.VideoColumns.WIDTH)
//                    val height = getInt(data, MediaStore.Video.VideoColumns.HEIGHT)
//                    val addTime = getLong(data, MediaStore.Video.VideoColumns.DATE_ADDED)
//                    val duration = getLong(data, MediaStore.Video.VideoColumns.DURATION)
//                    val artist = getString(data, MediaStore.Video.VideoColumns.ARTIST)

                    // 封装实体
                    val videoItemBuilder = MediaItem.Builder()
                    videoItemBuilder.setUri(uri)
                    videoItemBuilder.setMimeType(mimeType)
                    videoItemBuilder.setMediaId(id)

                    val item = videoItemBuilder.build()
                    videosList.add(item)
                }
            }
            mldMediaItemsVideos.postValue(videosList)
        }
    }

}