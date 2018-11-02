package com.xcyoung.cyberframe.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * @author ChorYeung
 * @since 2018/10/26
 */
abstract class BaseFragment : Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getCurrentViewLayoutId(),container,false)
    }

    abstract fun getCurrentViewLayoutId() : Int
}