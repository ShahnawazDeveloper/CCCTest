package com.example.ccctest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ccctest.R
import com.example.ccctest.api.respone.gallery.Photo
import com.hayati.staffapp.utils.loadImage
import kotlinx.android.synthetic.main.gallery_adapter_item.view.*

class GalleryAdapter(private val context: Context) :
    RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {

    var photos: List<Photo>? = null
    fun doRefresh(images: List<Photo>) {
        photos = images
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        return GalleryViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.gallery_adapter_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return photos?.size ?: 0
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        photos?.get(position)?.let {
            //  https://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}.jpg
            holder.itemView.image.loadImage(
                context, context.getString(
                    R.string.image_url,
                    it.farm, it.server, it.id, it.secret
                )
            )
        }
    }


    class GalleryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


}