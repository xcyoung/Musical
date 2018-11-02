package com.xcyoung.cyberframe.http

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author ChorYeung
 * @since 2018/10/26
 */
object RetrofitHandler {

    private lateinit var mOkHttpClient: OkHttpClient
    private val gson : Gson = GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss").create()

    fun setOkHttpClient(mOkHttpClient: OkHttpClient){
        this.mOkHttpClient = mOkHttpClient
    }

    fun <S> createService(clazz:Class<S>,baseUrl:String):S{
        if(mOkHttpClient == null) throw IllegalAccessException("OkhttpClient not set up!!!")
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(clazz)
    }
}