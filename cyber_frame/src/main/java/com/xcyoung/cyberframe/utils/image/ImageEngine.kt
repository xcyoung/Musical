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
    fun loadImage(imageView: ImageView, url: String, requestBuilder: RequestBuilder<Drawable>)

    fun loadImage(imageView: ImageView, drawableId: Int,requestBuilder: RequestBuilder<Drawable>)

    fun loadImage(imageView: ImageView, file: File,requestBuilder: RequestBuilder<Drawable>)

    fun loadImage(imageView: ImageView, uri: Uri,requestBuilder: RequestBuilder<Drawable>)
}