package com.xcyoung.cyberframe.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder

/**
 * @author ChorYeung
 * @since 2018/10/26
 */
object GsonHandler {
    val gson : Gson = GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss").create()
}