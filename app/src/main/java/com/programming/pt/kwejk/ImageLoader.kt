package com.programming.pt.kwejk

import android.content.Context
import android.support.v7.app.ActionBar
import com.squareup.picasso.Picasso
import ooo.oxo.library.widget.TouchImageView

class ImageLoader(private val context: Context,
                  private val imageView: TouchImageView,
                  private val titleBar: ActionBar?)
{
    fun load(img: Image?) {
        titleBar?.subtitle = img?.title
        Picasso.with(context)
                .load(img?.source)
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.ic_no_image)
                .resize(imageView.width, imageView.height)
                .onlyScaleDown()
                .centerInside()
                .into(imageView)
    }
}