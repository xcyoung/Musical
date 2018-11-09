package com.xcyoung.musical.client

import androidx.lifecycle.MutableLiveData
import com.xcyoung.cyberframe.base.BaseViewModel
import com.xcyoung.cyberframe.http.Exception
import com.xcyoung.cyberframe.http.HttpRelustObserver
import com.xcyoung.musical.controller.MusicInfoController
import com.xcyoung.musical.data.bean.SongList

/**
 * @author ChorYeung
 * @since 2018/11/6
 */
class MusicInfoViewModel :BaseViewModel() {
    val loadMusicLiveData = MutableLiveData<SongList>()

    fun loadSongList(){
        addDisposable(MusicInfoController().loadSongList()
                .subscribeWith(object : HttpRelustObserver<SongList>(){
                    override fun onSuccess(resultMap: SongList?) {
                        loadMusicLiveData.value = resultMap
                    }

                    override fun onFailed(exception: Exception) {

                    }
                }))
    }
}