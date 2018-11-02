package com.xcyoung.musical.player

import android.content.Context
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector

/**
 * @author ChorYeung
 * @since 2018/11/2
 */
class MusicPlayerAdapter(context: Context):PlayerAdapter(context) {

    private val exoPlayer:ExoPlayer by lazy {                   //ExoPlayer播放器
        ExoPlayerFactory.newSimpleInstance(
                context,
                DefaultRenderersFactory(context),
                DefaultTrackSelector(),
                DefaultLoadControl())
    }

    override fun isPlaying(): Boolean {
        return exoPlayer.isPlayingAd
    }

    override fun setVolume(volume: Float) {

    }

    override fun seekTo(progress: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPlay() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPause() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStop() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}