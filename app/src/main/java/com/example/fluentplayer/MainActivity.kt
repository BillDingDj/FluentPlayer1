package com.example.fluentplayer

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.fluentplayer.databinding.ActivityMainBinding
import com.example.fluentplayer.model.MediaItemCollectViewModel
import com.example.fluentplayer.utils.DeviceUtils
import com.example.fluentplayer.utils.JniUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        const val TAG = "MainActivity"
        const val REQUEST_CODE_PERMISSION = 10001
    }

    private val viewModel: MediaItemCollectViewModel by viewModels()

    private lateinit var mBlankView: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mBlankView = findViewById(R.id.top_blank_view)
        mBlankView?.updateLayoutParams {
            height = DeviceUtils.getStatusBarHeight(this@MainActivity)
        }
        onLiveDataObserved()
        checkPermission()
    }

    private fun collectAllVideoItems() {
        val uiScope = CoroutineScope(Dispatchers.Main)

        uiScope.launch {
            viewModel.prepareVideoMediaItems()
        }
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            collectAllVideoItems()
        } else {
            requestPermission()
        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            REQUEST_CODE_PERMISSION
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSION) {
            Log.d(TAG, "READ_EXTERNAL_STORAGE permission success.")
            collectAllVideoItems()
        }
    }

    private fun onLiveDataObserved() {
        viewModel.mldMediaItemsVideos.observe(this, {
            Log.d(TAG, "Receive ${it.size} videoItems")
            val jniHint = JniUtils.GetFFmpegVersion()
            Log.d(TAG, "$jniHint")
            Toast.makeText(this, jniHint, Toast.LENGTH_LONG)
            val bundle = Bundle().apply {
                putParcelableArrayList(MediaDisplayFragment.BUNDLE_MEDIA_ITEMS, it)
            }
            findNavController(R.id.nav_host_fragment_content_main).apply {
                navigate(R.id.action_SplashFragment_to_MediaDisplayFragment, bundle)
            }
        })
    }
}