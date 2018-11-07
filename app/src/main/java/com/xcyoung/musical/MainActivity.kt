package com.xcyoung.musical

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.lifecycle.Observer
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.xcyoung.cyberframe.base.BaseActivity
import com.xcyoung.musical.client.MediaBrowerViewModel
import com.xcyoung.musical.client.MusicInfoViewModel
import com.xcyoung.musical.service.MusicService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    private lateinit var mediaBrowerViewModel:MediaBrowerViewModel
    private lateinit var musicInfoViewModel:MusicInfoViewModel

    private val exoPlayer: SimpleExoPlayer by lazy {                   //ExoPlayer播放器
        ExoPlayerFactory.newSimpleInstance(
                this,
                DefaultRenderersFactory(this),
                DefaultTrackSelector(),
                DefaultLoadControl())
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val factory = MediaBrowerViewModel.Factory(this,MusicService::class.java)
        mediaBrowerViewModel = getViewModel(MediaBrowerViewModel::class.java,factory)

        musicInfoViewModel = getViewModel(MusicInfoViewModel::class.java)

        musicInfoViewModel.loadMusicLiveData.observe(this, Observer {
            if(it) mediaBrowerViewModel.onStart()
            play.isEnabled = it
        })

        play.setOnClickListener { mediaBrowerViewModel.mediaControllerCompat!!.transportControls.play() }
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

    private fun playMediaSource(uri: Uri?):ExtractorMediaSource {
        // 测量播放带宽，如果不需要可以传null
        val bandwidthMeter = DefaultBandwidthMeter()
        // 创建加载数据的工厂
        val dataSourceFactory = DefaultDataSourceFactory(this, Util.getUserAgent(this,"musical"),bandwidthMeter)
        // 传入Uri、加载数据的工厂、解析数据的工厂，就能创建出MediaSource
        val audioSource = ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(uri)
//        // Prepare
        exoPlayer.prepare(audioSource)
        return audioSource
    }
}
