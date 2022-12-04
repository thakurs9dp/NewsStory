package com.s9dp.newsstory.views.utils.extention

import android.graphics.drawable.Drawable
import android.view.RoundedCorner
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.s9dp.newsstory.R

fun ImageView.loadRectImage(
    imageUrl: String,
) {
    Glide.with(this)
        .load(imageUrl)
        .transform(RoundedCorners(16))
        .placeholder(R.drawable.ic_launcher_background)
        .error(R.drawable.ic_launcher_background)
        .listener(object : RequestListener<Drawable?> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable?>?,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }
            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable?>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }
        })
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .thumbnail(0.5f)
        .into(this)
}