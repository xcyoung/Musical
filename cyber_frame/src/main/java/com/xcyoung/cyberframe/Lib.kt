package com.xcyoung.cyberframe

import android.app.Application
import com.xcyoung.cyberframe.http.OkhttpHandler
import com.xcyoung.cyberframe.http.RetrofitHandler

/**
 * @author ChorYeung
 * @since 2018/10/26
 */
open class Lib {
    companion object {
        lateinit var application:Application
        open fun init(application: Application){
            this.application = application
            initRetrofit()
        }

        private fun initRetrofit(){
            val okHttpClient = OkhttpHandler.getOkhttpBuilder().build()
            RetrofitHandler.setOkHttpClient(okHttpClient)
        }
    }


}