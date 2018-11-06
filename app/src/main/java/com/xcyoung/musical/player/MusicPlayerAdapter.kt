package com.xcyoung.musical.player

import android.content.Context
import android.net.Uri
import android.os.SystemClock
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.PlaybackStateCompat
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.xcyoung.musical.service.MusicService

/**
 * @author ChorYeung
 * @since 2018/11/2
 */
class MusicPlayerAdapter(context: Context,val listener: MusicService.MediaPlayerListener):PlayerAdapter(context) {
    private var curMediaMetadata : MediaMetadataCompat ?= null
    private val exoPlayer:SimpleExoPlayer by lazy {                   //ExoPlayer播放器
        ExoPlayerFactory.newSimpleInstance(
                context,
                DefaultRenderersFactory(context),
                DefaultTrackSelector(),
                DefaultLoadControl())
    }
    private var mState = 0
    private var mCurrentMediaPlayedToCompletion = false
    private var mSeekWhileNotPlaying = -1
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
        setNewState(PlaybackStateCompat.STATE_PLAYING)
    }

    override fun onPause() {
        if (!isPlaying()) return
        exoPlayer.playWhenReady = false
        setNewState(PlaybackStateCompat.STATE_PAUSED)
    }

    override fun onStop() {
        exoPlayer.release()
        setNewState(PlaybackStateCompat.STATE_STOPPED)
    }

    override fun playFromMedia(metadata: MediaMetadataCompat) {
        this.curMediaMetadata = metadata
        playMediaSource(metadata.description.mediaUri)
//        playMediaSource(Uri.parse("https://api.bzqll.com/music/netease/url?id=486814412&key=579621905"))
    }

    override fun getCurrentMedia(): MediaMetadataCompat? {
        return curMediaMetadata
    }

    // This is the main reducer for the player state machine.
    private fun setNewState(@PlaybackStateCompat.State newPlayerState: Int) {
        mState = newPlayerState

        // Whether playback goes to completion, or whether it is stopped, the
        // mCurrentMediaPlayedToCompletion is set to true.
        if (mState == PlaybackStateCompat.STATE_STOPPED) {
            mCurrentMediaPlayedToCompletion = true
        }

        // Work around for MediaPlayer.getCurrentPosition() when it changes while not playing.
        val reportPosition: Long
        if (mSeekWhileNotPlaying >= 0) {
            reportPosition = mSeekWhileNotPlaying.toLong()

            if (mState == PlaybackStateCompat.STATE_PLAYING) {
                mSeekWhileNotPlaying = -1
            }
        } else {
            reportPosition = (exoPlayer.currentPosition).toLong()
        }

        val stateBuilder = PlaybackStateCompat.Builder()
        stateBuilder.setActions(getAvailableActions())
        stateBuilder.setState(mState,
                reportPosition,
                1.0f,
                SystemClock.elapsedRealtime())
        listener.onPlaybackStateChange(stateBuilder.build())
    }

    @PlaybackStateCompat.Actions
    private fun getAvailableActions(): Long {
        var actions = (PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID
                or PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH
                or PlaybackStateCompat.ACTION_SKIP_TO_NEXT
                or PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS)
        when (mState) {
            PlaybackStateCompat.STATE_STOPPED -> actions = actions or (PlaybackStateCompat.ACTION_PLAY or PlaybackStateCompat.ACTION_PAUSE)
            PlaybackStateCompat.STATE_PLAYING -> actions = actions or (PlaybackStateCompat.ACTION_STOP
                    or PlaybackStateCompat.ACTION_PAUSE
                    or PlaybackStateCompat.ACTION_SEEK_TO)
            PlaybackStateCompat.STATE_PAUSED -> actions = actions or (PlaybackStateCompat.ACTION_PLAY or PlaybackStateCompat.ACTION_STOP)
            else -> actions = actions or (PlaybackStateCompat.ACTION_PLAY
                    or PlaybackStateCompat.ACTION_PLAY_PAUSE
                    or PlaybackStateCompat.ACTION_STOP
                    or PlaybackStateCompat.ACTION_PAUSE)
        }
        return actions
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