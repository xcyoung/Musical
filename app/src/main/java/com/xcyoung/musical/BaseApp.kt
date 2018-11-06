package com.xcyoung.musical

import android.app.Application
import com.xcyoung.cyberframe.Lib

/**
 * @author ChorYeung
 * @since 2018/11/5
 */
class BaseApp : Application(){
    override fun onCreate() {
        super.onCreate()
        Lib.init(this)
    }
}