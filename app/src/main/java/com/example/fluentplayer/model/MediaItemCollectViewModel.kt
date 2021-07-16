package com.example.fluentplayer.model

import android.app.Application
import android.content.ContentUris
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.fluentplayer.entity.Video
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.ref.WeakReference
import java.util.concurrent.TimeUnit

/**
 * 收集本地媒体文件的ViewModel
 * https://developer.android.com/training/data-storage/shared/media?hl=zh-cn
 */
class MediaItemCollectViewModel(application: Application) : AndroidViewModel(application) {

    private val contentResolver =
        WeakReference(application.applicationContext).get()?.contentResolver

    val mldMediaItemsVideos = MutableLiveData<ArrayList<Video>>()

    /**
     * 协程，异步操作准备MediaItems
     */
    suspend fun prepareVideoMediaItems() {
        withContext(Dispatchers.IO) {
            val projection = arrayOf(
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.DURATION,
                MediaStore.Video.Media.SIZE
            )

            val selection = "${MediaStore.Video.Media.DURATION} >= ?"

            // 视频时间限制
            val selectionArgs = arrayOf(
                TimeUnit.MILLISECONDS.convert(1, TimeUnit.SECONDS).toString()
            )

            val sortOrder = "${MediaStore.Video.Media.DISPLAY_NAME} ASC"

            val query = contentResolver?.query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                sortOrder
            )

            val videosList = ArrayList<Video>()
            query?.use { cursor ->
                // Cache column indices.
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
                val nameColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)
                val durationColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)
                val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)

                while (cursor.moveToNext()) {
                    // Get values of columns for a given video.
                    val id = cursor.getLong(idColumn)
                    val name = cursor.getString(nameColumn)
                    val duration = cursor.getInt(durationColumn)
                    val size = cursor.getInt(sizeColumn)

                    val contentUri: Uri = ContentUris.withAppendedId(
                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                        id
                    )

                    // Stores column values and the contentUri in a local object
                    // stat represents the media file.
                    videosList.add(
                        Video(contentUri, name, duration, size)
                    )
                }
            }
            mldMediaItemsVideos.postValue(videosList)
        }
    }
}