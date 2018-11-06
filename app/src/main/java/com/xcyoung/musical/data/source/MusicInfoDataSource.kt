package com.xcyoung.musical.data.source

import android.media.browse.MediaBrowser
import com.xcyoung.cyberframe.StatusCode
import com.xcyoung.cyberframe.http.Response
import com.xcyoung.cyberframe.http.RetrofitHandler
import com.xcyoung.cyberframe.utils.GsonHandler
import com.xcyoung.musical.MusicLibrary
import com.xcyoung.musical.data.api.MusicApi
import com.xcyoung.musical.data.bean.SongList
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody

class MusicInfoDataSource {
    fun LoadSongList(): Observable<Response<SongList>> {
        val map = HashMap<String, Any>()
        map["key"] = "579621905"
        map["id"] = "3778678"
        val requestBody = RequestBody.create(MediaType.parse("application/json"), GsonHandler.gson.toJson(map))
        return getAppService()
                .getSongListByKey("579621905","3778678")
                .map {
                    if(it.data != null&&it.code == StatusCode.SUCCESS) {
                        MusicLibrary.onDataArrival(it.data!!.songListId, it.data!!.songs)
                    }
                it}
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getAppService():MusicApi{
        return RetrofitHandler.createService(MusicApi::class.java,"https://api.bzqll.com/music/netease/")
    }
}