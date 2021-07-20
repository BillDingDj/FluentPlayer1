package com.example.fluentplayer

import android.net.Uri
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.example.fluentplayer.entity.Video

class MediaItemAdapter : RecyclerView.Adapter<MediaItemAdapter.MediaItemViewHolder>() {
    private val mDataSource = ArrayList<Video>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaItemViewHolder {
        val context = parent.context
        val imageView = ImageView(context)
//
//        imageView.updateLayoutParams<ViewGroup.MarginLayoutParams> {
//            height = 500
//        }

        return MediaItemViewHolder(imageView)
    }

    override fun onBindViewHolder(holder: MediaItemViewHolder, position: Int) {
        val videoItem = mDataSource.getOrNull(position) ?: return
        holder.setUri(videoItem.uri)
    }

    override fun getItemCount(): Int = mDataSource.size

    fun setDataSource(list: ArrayList<Video>) {
        mDataSource.clear()
        mDataSource.addAll(list)
        notifyDataSetChanged()
    }

    inner class MediaItemViewHolder(private val view: ImageView) : RecyclerView.ViewHolder(view) {
        fun setUri(uri: Uri?) {
            if (uri == null) return
            view.setImageURI(uri)
        }
    }
}