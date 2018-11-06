package com.xcyoung.musical.client

import android.content.ComponentName
import android.content.Context
import android.os.RemoteException
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.media.MediaBrowserServiceCompat
import com.xcyoung.cyberframe.base.BaseViewModel
import timber.log.Timber

class MediaBrowerViewModel(val context: Context,val serviceClass:Class<out MediaBrowserServiceCompat>) : BaseViewModel() {
    var mediaControllerCompat : MediaControllerCompat?=null
    private var mediaBrowserCompat : MediaBrowserCompat ?= null

    fun onStart(){
        if (mediaBrowserCompat == null){
            mediaBrowserCompat = MediaBrowserCompat(context
                    , ComponentName(context,serviceClass)
                    ,mediaBrowserConnectionCallback,null).apply { connect() }
        }
    }

    fun onStop(){
        if(mediaControllerCompat!=null){
            mediaControllerCompat!!.unregisterCallback(mediaControlCallback)
            mediaControllerCompat = null
        }
        if(mediaBrowserCompat!=null && mediaBrowserCompat!!.isConnected){
            mediaBrowserCompat!!.disconnect()
            mediaBrowserCompat = null
        }
    }

    val mediaBrowserConnectionCallback = object : MediaBrowserCompat.ConnectionCallback(){
        override fun onConnected() {
            super.onConnected()
            try {
                mediaControllerCompat = MediaControllerCompat(context,mediaBrowserCompat!!.sessionToken)
                        .apply { registerCallback(mediaControlCallback)
                                mediaControlCallback.onPlaybackStateChanged(playbackState)
                                mediaControlCallback.onMetadataChanged(metadata)
                        }

                mediaBrowserCompat!!.subscribe(mediaBrowserCompat!!.root,mediaBrowserSubscriptionCallback)
            }catch (e:RemoteException){
                e.printStackTrace()
            }
        }
    }

    val mediaBrowserSubscriptionCallback = object : MediaBrowserCompat.SubscriptionCallback(){
        override fun onChildrenLoaded(parentId: String, children: MutableList<MediaBrowserCompat.MediaItem>) {
            super.onChildrenLoaded(parentId, children)

            children.forEach { mediaItem ->
                mediaControllerCompat?.addQueueItem(mediaItem.description)
            }

            mediaControllerCompat?.transportControls!!.prepare()
////            if(children.isNotEmpty())
//            mediaControllerCompat?.transportControls!!.play()
        }
    }

    val mediaControlCallback = object :MediaControllerCompat.Callback(){
        override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
            super.onPlaybackStateChanged(state)
            Timber.i("aab${state}")
        }

        override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
            super.onMetadataChanged(metadata)
            if(metadata == null) return
            Timber.i("aaa${metadata.description!!.title}")
        }

        override fun onSessionDestroyed() {
            super.onSessionDestroyed()

        }
    }

    class Factory(val context: Context,val serviceClass:Class<out MediaBrowserServiceCompat>) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MediaBrowerViewModel(context,serviceClass) as T
        }
    }
}