package com.xcyoung.musical.data.bean

/**
 * @author ChorYeung
 * @since 2018/11/5
 */
data class SongList(val songListId:String,
                    val songListName:String,
                    val songListPic:String,
                    val songListCount:String,
                    val songListPlayCount:String,
                    val songListDescription:String,
                    val songListUserId:String,
                    val songs:ArrayList<Song>){
    data class Song(val id:String,
                     val name:String,
                     val singer:String,
                     val pic:String,            //封面
                     val lrc:String,            //歌词
                     val url:String)            //音频url
}