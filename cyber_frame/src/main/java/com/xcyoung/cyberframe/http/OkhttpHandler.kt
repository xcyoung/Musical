package com.xcyoung.cyberframe.http

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

/**
 * @author ChorYeung
 * @since 2018/10/26
 */
object OkhttpHandler {

    private const val DEFALUT_CONNECT_TIMEOUT: Long= 10
    private const val DEFALUT_READ_TIMEOUT: Long= 30
    private const val DEFALUT_WRITE_TIMEOUT: Long= 30

    fun getOkhttpBuilder(): OkHttpClient.Builder{
        val builder = OkHttpClient.Builder()

        builder.connectTimeout(DEFALUT_CONNECT_TIMEOUT,TimeUnit.SECONDS)
        builder.readTimeout(DEFALUT_READ_TIMEOUT,TimeUnit.SECONDS)
        builder.writeTimeout(DEFALUT_WRITE_TIMEOUT,TimeUnit.SECONDS)

        return builder
    }
}