package com.xcyoung.musical.data.api

import com.xcyoung.cyberframe.http.Response
import com.xcyoung.musical.data.bean.SongList
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Query

interface MusicApi {
    @GET("songList")
    fun getSongListByKey(@Query (value = "key")key:String,@Query (value = "id")id:String):Observable<Response<SongList>>
}