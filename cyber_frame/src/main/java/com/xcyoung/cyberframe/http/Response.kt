package com.xcyoung.cyberframe.http

/**
 * @author ChorYeung
 * @since 2018/10/26
 */
data class Response<T>(val code:String,val result:String,val data:T?){
    override fun toString(): String {
        return "Response(code='$code', result='$result', data=$data)"
    }
}