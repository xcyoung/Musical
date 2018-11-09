package com.xcyoung.musical

import android.os.Bundle
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

//        musicInfoViewModel.loadMusicLiveData.observe(this, Observer {
//            if(it) mediaBrowerViewModel.onStart()           //请求接口成功后建立媒体浏览连接 准备播放器
//            fab.isEnabled = it
//        })

        fab.setOnClickListener { mediaBrowerViewModel.mediaControllerCompat!!.transportControls.play() }
        stop.setOnClickListener { mediaBrowerViewModel.mediaControllerCompat!!.transportControls.prepare() }

//        playMediaSource(Uri.parse("https://api.bzqll.com/music/netease/url?id=486814412&key=579621905"))
//
//        play.setOnClickListener { exoPlayer.playWhenReady = true }
//        stop.setOnClickListener { exoPlayer.playWhenReady = false }
    }

    override fun onStart() {
        super.onStart()

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
