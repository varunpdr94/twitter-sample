package com.example.twittersample.utils

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.squareup.picasso.Picasso


class DatabindingUtility {


    companion object {
        
        @JvmStatic
        @BindingAdapter("bind:imageUrl")
        fun setImageUrl(view: ImageView, url: String) {
            url?.let {
                Picasso.with(view.context).load(url).into(view)
            }
        }
    }

}
