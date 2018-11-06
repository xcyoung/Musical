package com.xcyoung.musical.controller

import android.media.browse.MediaBrowser
import com.xcyoung.cyberframe.base.BaseController
import com.xcyoung.cyberframe.http.Exception
import com.xcyoung.cyberframe.http.HttpRelustObserver
import com.xcyoung.cyberframe.http.Response
import com.xcyoung.musical.MusicLibrary
import com.xcyoung.musical.data.bean.SongList
import com.xcyoung.musical.data.source.MusicInfoDataSource
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

class MusicInfoController : BaseController(){
    private val musicInfoDataSource = MusicInfoDataSource()
    /**
     * 获取歌单
     */
    fun loadSongList(): Observable<Response<SongList>> {
        return musicInfoDataSource.LoadSongList()
    }
}