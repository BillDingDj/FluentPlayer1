package com.example.fluentplayer

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fluentplayer.databinding.FragmentMediaDisplayBinding
import com.example.fluentplayer.entity.Video

class MediaDisplayFragment : Fragment() {
    companion object{
        const val BUNDLE_MEDIA_ITEMS = "media_items"
    }

    private var _binding: FragmentMediaDisplayBinding? = null
    private val binding get() = _binding!!

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: MediaItemAdapter
    private val mMediaList = ArrayList<Video>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        val videoList = bundle?.getParcelableArrayList<Video>(BUNDLE_MEDIA_ITEMS)?.toList() ?: emptyList()
        mMediaList.addAll(videoList)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMediaDisplayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView(view)
    }

    private fun initRecyclerView(rootView: View) {
        mRecyclerView = rootView.findViewById(R.id.media_item_display_fragment)
        mAdapter = MediaItemAdapter()
        mRecyclerView.apply {
            adapter = mAdapter
            layoutManager = GridLayoutManager(context!!, 3)
            mAdapter.setDataSource(mMediaList)
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    outRect.bottom = 20
                    outRect.right = 20
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}