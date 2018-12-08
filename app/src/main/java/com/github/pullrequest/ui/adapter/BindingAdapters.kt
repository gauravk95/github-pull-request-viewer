package com.github.pullrequest.ui.adapter

import android.databinding.BindingAdapter
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.signature.StringSignature
import com.github.pullrequest.R

@BindingAdapter("isGone")
fun bindIsGone(view: View, isGone: Boolean) {
    view.visibility = if (isGone) {
        View.GONE
    } else {
        View.VISIBLE
    }
}

@BindingAdapter("imageUrl")
fun bindLoadImage(image: ImageView, url: String?) {
    if (!url.isNullOrEmpty())
        Glide.with(image.context.applicationContext)
                .load(url)
                .signature(StringSignature(url))
                .dontAnimate()
                .placeholder(R.drawable.ic_image)
                .into(image)
}


