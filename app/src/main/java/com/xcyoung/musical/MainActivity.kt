package com.xcyoung.musical

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.xcyoung.cyberframe.base.BaseActivity
import com.xcyoung.musical.client.MediaBrowerViewModel
import com.xcyoung.musical.client.MusicInfoViewModel
import com.xcyoung.musical.service.MusicService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    private lateinit var mediaBrowerViewModel:MediaBrowerViewModel
    private lateinit var musicInfoViewModel:MusicInfoViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val factory = MediaBrowerViewModel.Factory(this,MusicService::class.java)
        mediaBrowerViewModel = getViewModel(MediaBrowerViewModel::class.java,factory)

        musicInfoViewModel = getViewModel(MusicInfoViewModel::class.java)

        musicInfoViewModel.loadMusicLiveData.observe(this, Observer {
            btn.isEnabled = it
        })

        btn.setOnClickListener { mediaBrowerViewModel.mediaControllerCompat!!.transportControls.play() }
    }

    override fun onStart() {
        super.onStart()
        mediaBrowerViewModel.onStart()
    }

    override fun onResume() {
        super.onResume()
        musicInfoViewModel.loadSongList()
    }

    override fun onStop() {
        super.onStop()
        mediaBrowerViewModel.onStop()
    }
}
