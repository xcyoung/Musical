package com.xcyoung.musical.player

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.AudioManager
import android.support.v4.media.MediaMetadataCompat
import com.xcyoung.cyberframe.Lib
import com.xcyoung.musical.MEDIA_VOLUME

/**
 * @author ChorYeung
 * @since 2018/11/2
 */
abstract class PlayerAdapter(val context: Context) {
    private var isPlayOnFocus =false                            //用于控制不同情况下的音频焦点处理
    protected val audioManager:AudioManager = Lib.application.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    private val audioFocusChange:AudioFocusChange = AudioFocusChange()
    private val intentFilter:IntentFilter = IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY)
    protected fun play(){
        if(audioFocusChange.requestAudioFocus()){
            registerNosiyReceiver()
            onPlay()
        }
    }

    fun pause(){
        if(!isPlayOnFocus){
            audioFocusChange.abandonAudioFocus()
        }
        unRegisterNosiyReceiver()
        onPause()
    }

    fun stop(){
        audioFocusChange.abandonAudioFocus()
        unRegisterNosiyReceiver()
        onStop()
    }

    private fun registerNosiyReceiver(){
        Lib.application.registerReceiver(audioNosiyReceiver,intentFilter)
    }

    private fun unRegisterNosiyReceiver(){
        Lib.application.unregisterReceiver(audioNosiyReceiver)
    }

    protected abstract fun isPlaying():Boolean

    protected abstract fun setVolume(volume:Float)

    protected abstract fun seekTo(progress:Long)

    protected abstract fun onPlay()

    protected abstract fun onPause()

    protected abstract fun onStop()

    abstract fun playFromMedia(metadata: MediaMetadataCompat)

    abstract fun getCurrentMedia() : MediaMetadataCompat?

    /**
     *  监听音频资源是否被抢占的监听器
     */
    private inner class AudioFocusChange : AudioManager.OnAudioFocusChangeListener {

        fun requestAudioFocus():Boolean{
            val result = audioManager.requestAudioFocus(this,
                    AudioManager.STREAM_MUSIC,
                    AudioManager.AUDIOFOCUS_GAIN)
            return result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED
        }

        fun abandonAudioFocus(){
            audioManager.abandonAudioFocus(this@AudioFocusChange)
        }

        /**
         *  AUDIOFOCUS_GAIN：获得了Audio Focus；
         *
         *  AUDIOFOCUS_LOSS：失去了Audio Focus，并将会持续很长的时间。这里因为可能会停掉很长时间，所以不仅仅要停止Audio的播放，
         *  最好直接释放掉Media资源。而因为停止播放Audio的时间会很长.
         *
         *  AUDIOFOCUS_LOSS_TRANSIENT：暂时失去Audio Focus，并会很快再次获得。必须停止Audio的播放，
         *  但是因为可能会很快再次获得AudioFocus，这里可以不释放Media资源；
         *
         *  AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK：暂时失去AudioFocus，但是可以继续播放，不过要在降低音量。
         */
        override fun onAudioFocusChange(focusChange: Int) {
            when(focusChange){
                AudioManager.AUDIOFOCUS_GAIN -> {
                    //用于处理AUDIOFOCUS_LOSS_TRANSIENT
                    if(isPlayOnFocus && !isPlaying()) play()
                    //用于处理AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK
                    else if(isPlaying())  setVolume(MEDIA_VOLUME.DEFAULT)
                    isPlayOnFocus = false
                }

                AudioManager.AUDIOFOCUS_LOSS -> {
                    audioManager.abandonAudioFocus(this@AudioFocusChange)
                    isPlayOnFocus = false
                    stop()
                }

                AudioManager.AUDIOFOCUS_GAIN_TRANSIENT -> {
                    if(isPlaying()){
                        isPlayOnFocus = true
                        pause()
                    }
                }

                AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK -> {
                    setVolume(MEDIA_VOLUME.DUCK)
                }
            }
        }
    }

    /**
     *  噪音广播监听
     */
    private val audioNosiyReceiver= object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            if(AudioManager.ACTION_AUDIO_BECOMING_NOISY.equals(intent?.action)){
                if(isPlaying()){            //此情况符合听歌过程中有外来音频进入的情况（如：有来电）
                    pause()
                }
            }
        }
    }
}