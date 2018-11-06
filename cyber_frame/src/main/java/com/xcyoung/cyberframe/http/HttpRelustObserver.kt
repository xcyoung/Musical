package com.xcyoung.cyberframe.http

import com.xcyoung.cyberframe.StatusCode
import io.reactivex.observers.ResourceObserver
import timber.log.Timber

/**
 * @author ChorYeung
 * @since 2018/10/26
 */
abstract class HttpRelustObserver<T> : ResourceObserver<Response<T>>() {
    override fun onComplete() {
        
    }

    override fun onNext(t: Response<T>) {
        if(t.code == StatusCode.SUCCESS){
            onSuccess(t.data)
        }else{
            onFailed(com.xcyoung.cyberframe.http.Exception(t.code,t.result))
        }
    }

    override fun onError(e: Throwable) {
        if(e.message == null) Timber.e("HttpError:NULL")
        else Timber.e("HttpError:${e.message}")
        onFailed(com.xcyoung.cyberframe.http.Exception("-1","网络请求异常，请稍后重试"))
    }

    abstract fun onSuccess(resultMap: T?)

    abstract fun onFailed(exception: Exception)
}