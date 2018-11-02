package com.xcyoung.cyberframe.base

import android.app.Activity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

/**
 * @author ChorYeung
 * @since 2018/10/26
 */
abstract class BaseActivity : AppCompatActivity(){
    companion object {
        private val activityStack = HashSet<Activity>()                 //存放所有activity的集合
        private val tempActivityStack = HashSet<Activity>()             //存放某些临时activity的集合
        fun finishAll(){
            activityStack.forEach { it.finish() }
            tempActivityStack.forEach { it.finish() }
            activityStack.clear()
            tempActivityStack.clear()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        activityStack.add(this)

    }

    override fun onDestroy() {
        super.onDestroy()
        activityStack.remove(this)
    }

    fun addTempActivityStack() {
        tempActivityStack.add(this)
    }

    fun finishAllTemp(){
        tempActivityStack.forEach { it.finish() }
        tempActivityStack.clear()
    }

    protected fun <T : BaseViewModel> getViewModel(clazz:Class<T>, factory: ViewModelProvider.Factory? = null) : T{
        return if (factory == null) {
            ViewModelProviders.of(this).get(clazz)
        } else {
            ViewModelProviders.of(this, factory).get(clazz)
        }
    }
}