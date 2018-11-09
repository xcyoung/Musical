package com.xcyoung.musical

import android.support.v4.media.MediaMetadataCompat
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.xcyoung.cyberframe.utils.image.GlideV4Engine
import com.xcyoung.musical.client.MediaBrowerViewModel
import com.xcyoung.musical.dummy.DummyContent.DummyItem
import kotlinx.android.synthetic.main.recycler_view_item.view.*

class MyItemRecyclerViewAdapter(
        private val metadataList:List<MediaMetadataCompat>)
    : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as MediaMetadataCompat
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_view_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = metadataList[position]
        holder.name.text = item.getString(MediaMetadataCompat.METADATA_KEY_TITLE)
        holder.singer.text = item.getString(MediaMetadataCompat.METADATA_KEY_ARTIST)
        GlideV4Engine.loadImage(holder.pic,item.getString(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON_URI))
        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = metadataList.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val name: TextView = mView.name
        val singer: TextView = mView.singer
        val pic: ImageView = mView.pic
    }
}
