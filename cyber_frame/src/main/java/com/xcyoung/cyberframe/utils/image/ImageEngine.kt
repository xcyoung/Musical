package com.xcyoung.cyberframe.utils.image

import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.RequestBuilder
import java.io.File

/**
 * @author ChorYeung
 * @since 2018/10/31
 */
interface ImageEngine {
    fun loadImage(imageView: ImageView, url: String)

    fun loadImage(imageView: ImageView, drawableId: Int)

    fun loadImage(imageView: ImageView, file: File)

    fun loadImage(imageView: ImageView, uri: Uri)
}