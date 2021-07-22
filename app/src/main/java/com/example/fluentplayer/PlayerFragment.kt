package com.example.fluentplayer

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fluentplayer.databinding.FragmentPlayerBinding
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class PlayerFragment : Fragment() {
    companion object {
        const val PARA_URI = "uri"
    }

    private var _binding: FragmentPlayerBinding? = null

    private val binding get() = _binding!!

    private lateinit var mUri: Uri

    /**
     * Exo Player
     */
    private lateinit var mPlayer : SimpleExoPlayer

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mPlayer = SimpleExoPlayer.Builder(context).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments ?: return
        mUri = bundle.getParcelable(PARA_URI) ?: return
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.playerView.player = mPlayer

        val mediaItem = MediaItem.fromUri(mUri)
        mPlayer.setMediaItem(mediaItem)
        mPlayer.prepare()
        mPlayer.play()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mPlayer.stop()
    }
}