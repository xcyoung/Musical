package com.xcyoung.musical

import android.graphics.Color
import android.os.Bundle
import android.support.v4.media.session.MediaSessionCompat.ACTION_FOLLOW
import android.view.View
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.xcyoung.cyberframe.base.BaseActivity
import com.xcyoung.cyberframe.utils.image.GlideV4Engine
import com.xcyoung.musical.client.MediaBrowerViewModel
import com.xcyoung.musical.client.MusicInfoViewModel
import com.xcyoung.musical.service.MusicService
import kotlinx.android.synthetic.main.activity_music.*
import kotlinx.android.synthetic.main.fragment_item_list.*

class MusicActivity : BaseActivity() {

    private lateinit var mediaBrowerViewModel: MediaBrowerViewModel
    private lateinit var musicInfoViewModel: MusicInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)
        setBar()

        fab.isEnabled = false
        val factory = MediaBrowerViewModel.Factory(this, MusicService::class.java)
        mediaBrowerViewModel = getViewModel(MediaBrowerViewModel::class.java,factory)

        musicInfoViewModel = getViewModel(MusicInfoViewModel::class.java)

        musicInfoViewModel.loadMusicLiveData.observe(this, Observer {
            if(it!=null){
                list.layoutManager = LinearLayoutManager(this)
                list.adapter =  MyItemRecyclerViewAdapter(MusicLibrary.metadataList)
                GlideV4Engine.loadImage(ablum,it.songListPic)
                mediaBrowerViewModel.onStart()
                fab.isEnabled = true
            }
        })

        fab.setOnClickListener {
            mediaBrowerViewModel.mediaControllerCompat!!.transportControls.play()
            Snackbar.make(it, "开始播放", BaseTransientBottomBar.LENGTH_SHORT)
                    .setAction("Action", null).show()
        }

        musicInfoViewModel.loadSongList()
    }

    private fun setBar() {
        setSupportActionBar(toolbar)
        val decorView = window.decorView
        val option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        decorView.systemUiVisibility = option
        window.statusBarColor = Color.TRANSPARENT
    }

    override fun onStop() {
        super.onStop()
        mediaBrowerViewModel.onStop()
    }
}
