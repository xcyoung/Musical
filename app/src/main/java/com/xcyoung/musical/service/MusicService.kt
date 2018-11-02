package com.xcyoung.musical.service

import android.content.Intent
import android.media.session.MediaSession
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import androidx.media.MediaBrowserServiceCompat

/**
 * @author ChorYeung
 * @since 2018/11/1
 */
class MusicService : MediaBrowserServiceCompat() {

    lateinit var mediaSessionCompat : MediaSessionCompat        //媒体会话


    override fun onCreate() {
        super.onCreate()

        mediaSessionCompat = MediaSessionCompat(this,"MusicService")

        mediaSessionCompat.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS or
                MediaSessionCompat.FLAG_HANDLES_QUEUE_COMMANDS or
                MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS)
        mediaSessionCompat.setCallback(mediaSessionCallback)        //设置播放控制回调
        sessionToken = mediaSessionCompat.sessionToken              //设置本次媒体浏览会话的token
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        stopSelf()
    }

    override fun onLoadChildren(parentId: String, result: Result<MutableList<MediaBrowserCompat.MediaItem>>) {

    }

    override fun onGetRoot(clientPackageName: String, clientUid: Int, rootHints: Bundle?): BrowserRoot? {
        return BrowserRoot("root",null)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaSessionCompat.release()                //销毁时需要把会话（session）释放
    }

    //MediaSessionCompat的播放控制回调
    private val mediaSessionCallback = object : MediaSessionCompat.Callback() {
        //用于存放播放队列
        private val playList = ArrayList<MediaSessionCompat.QueueItem>()
        private var curIndex = -1
        private var mediaMetadataCompat : MediaMetadataCompat ? = null        //用于设置播放媒体的相关信息，在onPrepare()被实例

        //添加需要播放的音乐到队尾
        override fun onAddQueueItem(description: MediaDescriptionCompat?) {
            super.onAddQueueItem(description)
            playList.add(MediaSessionCompat.QueueItem(description, description!!.hashCode().toLong()))
            if(curIndex == -1) curIndex = 0                     //当前为第一次添加则更新index为0
            mediaSessionCompat.setQueue(playList)
        }

        //移除队首的音乐
        override fun onRemoveQueueItem(description: MediaDescriptionCompat?) {
            super.onRemoveQueueItem(description)
            playList.remove(MediaSessionCompat.QueueItem(description, description!!.hashCode().toLong()))
            if(playList.isEmpty()) curIndex = -1                //移除后列表为空时，将index重设成-1
            mediaSessionCompat.setQueue(playList)
        }

        //通过MediaControllerCompat .getTransportControls().play();触发
        override fun onPlay() {
            super.onPlay()
            if(playList.isEmpty()) return       //播放列表为空不响应该方法

            if(mediaMetadataCompat == null) onPrepare()         //如果该值为null说明还没有onPrepare()

            //将mediaMetadataCompat设置到播放器
        }

        //暂停时触发
        //MediaControllerCompat.getTransportControls().pause();
        override fun onPause() {
            super.onPause()
            //播放器暂停
        }

        //准备时触发
        override fun onPrepare() {
            super.onPrepare()
            if(curIndex<0 && playList.isEmpty()) return       //如果当前位置<0并且播放列表为空说明当前无需要播放的音乐
            //设置MediaMetadataCompat
            val mediaId = playList.get(curIndex).description.mediaId          //通过获取mediaId来创建MediaMetadataCompat
            //创建MediaMetadataCompat
            //mediaMetadataCompat =
            mediaSessionCompat.setMetadata(mediaMetadataCompat)

            if (!mediaSessionCompat.isActive) mediaSessionCompat.isActive = true        //激活mediaSessionCompat
        }

        //设置到指定进度时触发
        //MediaControllerCompat.getTransportControls().seekTo(position);
        override fun onSeekTo(pos: Long) {
            super.onSeekTo(pos)
            //播放器seekTo
        }

        //停止播放时触发
        //MediaControllerCompat.getTransportControls().stop();
        override fun onStop() {
            super.onStop()
            //播放器停止
            mediaSessionCompat.isActive = false
        }

        //跳到下一首时触发
        //MediaControllerCompat.getTransportControls().skipToNext();
        override fun onSkipToNext() {
            super.onSkipToNext()
            curIndex = (++curIndex % playList.count())
            mediaMetadataCompat = null
            onPlay()
        }

        //跳到上一首时触发
        //MediaControllerCompat.getTransportControls().skipToPrevious();
        override fun onSkipToPrevious() {
            super.onSkipToPrevious()
            if(curIndex>0) curIndex-- else playList.count()-1
            mediaMetadataCompat = null
            onPlay()
        }

        //播放指定对列媒体时触发
        //通过MediaControllerCompat .getTransportControls().onSkipToQueueItem(queueId);触发
        override fun onSkipToQueueItem(id: Long) {
            super.onSkipToQueueItem(id)
//            val queueItem = playList.find { it -> it.queueId == id }        //查询列表中是否还有符合该id的queueItem
            curIndex = playList.indexOfFirst { it -> id == it.queueId }
            if(curIndex == -1) return
//            curIndex = playList.indexOfFirst { it -> queueItem == it }
            mediaMetadataCompat = null
            onPlay()
        }
    }
}