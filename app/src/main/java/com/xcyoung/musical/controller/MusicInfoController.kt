package com.xcyoung.musical.controller

import com.xcyoung.cyberframe.base.BaseController
import com.xcyoung.musical.data.source.MusicInfoDataSource

class MusicInfoController : BaseController(){
    private val musicInfoDataSource = MusicInfoDataSource()
    /**
     * 获取歌单
     */
    fun loadSongList(){
        musicInfoDataSource.LoadSongList()
    }
}