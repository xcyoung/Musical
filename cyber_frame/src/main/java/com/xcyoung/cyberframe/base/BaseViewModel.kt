package com.xcyoung.cyberframe.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * @author ChorYeung
 * @since 2018/10/26
 */
abstract class BaseViewModel : ViewModel(){
    private var compositeDisposable : CompositeDisposable = CompositeDisposable()   //用于rx订阅
    val exceptionLiveData = MutableLiveData<Exception>()        //处理异常的LiveData
    val isLoadingLiveData = MutableLiveData<Boolean>()          //处理loading效果的LiveData

    override fun onCleared() {
        super.onCleared()
        if(compositeDisposable.isDisposed){
            compositeDisposable.dispose()
        }
    }

    protected fun addDisposable(disposable: Disposable){
        compositeDisposable.add(disposable)
    }
}