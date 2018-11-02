package com.xcyoung.cyberframe.base

import android.content.Context
import com.google.gson.Gson
import com.xcyoung.cyberframe.Lib
import com.xcyoung.cyberframe.utils.GsonHandler
import com.xcyoung.cyberframe.utils.SPHandler
import java.lang.reflect.Type

/**
 * @author ChorYeung
 * @since 2018/10/26
 */
abstract class BaseController {

    val context:Context = Lib.application

    fun <T> getObjectFromSP(key:String, type: Type) : T? {
        val value = SPHandler.getString(context,key,"")
        if(value.isNotEmpty()) return GsonHandler.gson.fromJson(value,type)
        return null
    }

    fun getObjectFromSP(key: String) : String? {
        val value = SPHandler.getString(context,key,"")
        if(value.isNotEmpty()) return value
        return null
    }

    fun putObjectToSP(key:String,value:String){
        SPHandler.putString(context,key,value)
    }

    fun putObjectToSP(key:String,value: Any){
        SPHandler.putString(context,key,GsonHandler.gson.toJson(value))
    }
}