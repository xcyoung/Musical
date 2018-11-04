package com.xcyoung.musical.player

import android.content.Context
import android.net.Uri
import android.support.v4.media.MediaMetadataCompat
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util

/**
 * @author ChorYeung
 * @since 2018/11/2
 */
class MusicPlayerAdapter(context: Context):PlayerAdapter(context) {
    private var curMediaMetadata : MediaMetadataCompat ?= null
    private val exoPlayer:SimpleExoPlayer by lazy {                   //ExoPlayer播放器
        ExoPlayerFactory.newSimpleInstance(
                context,
                DefaultRenderersFactory(context),
                DefaultTrackSelector(),
                DefaultLoadControl())
    }

    override fun isPlaying(): Boolean {
        return exoPlayer.playWhenReady
    }

    override fun setVolume(volume: Float) {
        exoPlayer.setVolume(volume)
    }

    override fun seekTo(progress: Long) {
        exoPlayer.seekTo(progress)
    }

    override fun onPlay() {
        if (isPlaying())  return
        exoPlayer.playWhenReady = true
    }

    override fun onPause() {
        if (!isPlaying()) return
        exoPlayer.playWhenReady = false
    }

    override fun onStop() {
        exoPlayer.release()
    }

    override fun playFromMedia(metadata: MediaMetadataCompat) {
        this.curMediaMetadata = metadata
        playMediaSource(metadata.description.mediaUri)
    }

    override fun getCurrentMedia(): MediaMetadataCompat? {
        return curMediaMetadata
    }


    private fun playMediaSource(uri: Uri?) {
        // 测量播放带宽，如果不需要可以传null
        val bandwidthMeter = DefaultBandwidthMeter()
        // 创建加载数据的工厂
        val dataSourceFactory = DefaultDataSourceFactory(context, Util.getUserAgent(context,"musical"),bandwidthMeter)
        // 传入Uri、加载数据的工厂、解析数据的工厂，就能创建出MediaSource
        val audioSource = ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(uri)
        // Prepare
        exoPlayer.prepare(audioSource)
        play()
    }
}