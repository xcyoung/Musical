package com.xcyoung.musical

import android.net.Uri
import android.support.v4.media.MediaMetadataCompat
import com.xcyoung.musical.data.bean.SongList

/**
 * @author ChorYeung
 * @since 2018/11/6
 */
class MusicLibrary{
    companion object {
        var metadataList:List<MediaMetadataCompat> = emptyList()
        /**
         * 数据读取成功后将数据转成MediaMetadataCompat并保存列表
         */
        fun onDataArrival(mediaId:String,songs:ArrayList<SongList.Song>){
            metadataList = songs.map {
                MediaMetadataCompat.Builder()
                        .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, it.id)
                        .putString(MediaMetadataCompat.METADATA_KEY_ALBUM, "")
                        .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, it.singer)
                        .putString(MediaMetadataCompat.METADATA_KEY_GENRE, "云音乐热歌榜")
                        .putString(
                                MediaMetadataCompat.METADATA_KEY_ALBUM_ART_URI,
                                it.pic)
                        .putString(
                                MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON_URI,
                                it.pic)
                        .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI,
                                it.url)
                        .putString(MediaMetadataCompat.METADATA_KEY_TITLE, it.name)
                        .build()
            }
        }

        fun getMetadata(mediaId: String): MediaMetadataCompat? {
            return metadataList.find { it -> it.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID) == mediaId }
        }
    }
}