package com.xcyoung.cyberframe.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

/**
 * @author ChorYeung
 * @since 2018/10/26
 */
class SPHandler {
    companion object {
        fun getInt(context: Context,key:String,defaultValue:Int):Int{
            val sp:SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return sp.getInt(key,defaultValue)
        }

        fun putInt(context: Context,key:String,value:Int){
            val sp:SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            sp.edit().putInt(key,value).apply()
        }

        fun getFloat(context: Context,key:String,defaultValue:Float):Float{
            val sp:SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return sp.getFloat(key,defaultValue)
        }

        fun putFloat(context: Context,key:String,value:Float){
            val sp:SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            sp.edit().putFloat(key,value).apply()
        }

        fun getLong(context: Context,key:String,defaultValue:Long):Long{
            val sp:SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return sp.getLong(key,defaultValue)
        }

        fun putLong(context: Context,key:String,value:Long){
            val sp:SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            sp.edit().putLong(key,value).apply()
        }

        fun getString(context: Context,key:String,defaultValue:String):String{
            val sp:SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return sp.getString(key,defaultValue)
        }

        fun putString(context: Context,key:String,value:String){
            val sp:SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            sp.edit().putString(key,value).apply()
        }

        fun getBoolean(context: Context,key:String,defaultValue:Boolean):Boolean{
            val sp:SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return sp.getBoolean(key,defaultValue)
        }

        fun putBoolean(context: Context,key:String,value:Boolean){
            val sp:SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            sp.edit().putBoolean(key,value).apply()
        }
    }
}