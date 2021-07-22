package com.example.fluentplayer

import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fluentplayer.PlayerFragment.Companion.PARA_URI
import com.example.fluentplayer.databinding.FragmentMediaDisplayBinding
import com.example.fluentplayer.entity.Video
import com.example.fluentplayer.utils.dp2px

class MediaDisplayFragment : Fragment() {
    companion object {
        const val BUNDLE_MEDIA_ITEMS = "media_items"
    }

    private val mCallback: (videoUri: Uri) -> Unit = {
        handleVideoClick(it)
    }

    private var _binding: FragmentMediaDisplayBinding? = null
    private val binding get() = _binding!!

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: MediaItemAdapter
    private val mMediaList = ArrayList<Video>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        val videoList =
            bundle?.getParcelableArrayList<Video>(BUNDLE_MEDIA_ITEMS)?.toList() ?: emptyList()
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
        mAdapter = MediaItemAdapter(mCallback)
        mRecyclerView.apply {
            adapter = mAdapter
            layoutManager = GridLayoutManager(requireContext(), 3)
            mAdapter.setDataSource(mMediaList)
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    outRect.bottom = 5.dp2px(context)
                    if (parent.getChildAdapterPosition(view) % 3 != 2) {
                        outRect.right = 5.dp2px(context)
                    }
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun handleVideoClick(uri: Uri) {
        val bundle = Bundle().apply {
            putParcelable(PARA_URI, uri)
        }
        findNavController().navigate(R.id.action_MediaDisplayFragment_to_PlayerFragment, bundle)
    }
}