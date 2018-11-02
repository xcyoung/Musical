package com.xcyoung.cyberframe.utils.image

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import java.io.File

/**
 * @author ChorYeung
 * @since 2018/10/31
 */
object GlideV4Engine : ImageEngine {
    override fun loadImage(imageView: ImageView, url: String, requestBuilder: RequestBuilder<Drawable>) {
        Glide.with(imageView.context)
                .asBitmap()
                .load(url)
                .transition(withCrossFade())
                .apply(RequestOptions()
                        .placeholder(ColorDrawable(Color.parseColor("#C0C4CC")))
                        .error(ColorDrawable(Color.parseColor("#C0C4CC")))
                        .centerCrop())
                .into(imageView)

    }

    override fun loadImage(imageView: ImageView, drawableId: Int, requestBuilder: RequestBuilder<Drawable>) {
        Glide.with(imageView.context)
                .asBitmap()
                .load(drawableId)
                .transition(withCrossFade())
                .apply(RequestOptions()
                        .placeholder(ColorDrawable(Color.parseColor("#C0C4CC")))
                        .error(ColorDrawable(Color.parseColor("#C0C4CC")))
                        .centerCrop())
                .into(imageView)
    }

    override fun loadImage(imageView: ImageView, file: File, requestBuilder: RequestBuilder<Drawable>) {
        Glide.with(imageView.context)
                .asBitmap()
                .load(file)
                .transition(withCrossFade())
                .apply(RequestOptions()
                        .placeholder(ColorDrawable(Color.parseColor("#C0C4CC")))
                        .error(ColorDrawable(Color.parseColor("#C0C4CC")))
                        .centerCrop())
                .into(imageView)
    }

    override fun loadImage(imageView: ImageView, uri: Uri, requestBuilder: RequestBuilder<Drawable>) {
        Glide.with(imageView.context)
                .asBitmap()
                .load(uri)
                .transition(withCrossFade())
                .apply(RequestOptions()
                        .placeholder(ColorDrawable(Color.parseColor("#C0C4CC")))
                        .error(ColorDrawable(Color.parseColor("#C0C4CC")))
                        .centerCrop())
                .into(imageView)
    }

}