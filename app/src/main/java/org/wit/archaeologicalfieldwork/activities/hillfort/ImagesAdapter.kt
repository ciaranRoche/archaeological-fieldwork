package org.wit.archaeologicalfieldwork.activities.hillfort

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import org.wit.archaeologicalfieldwork.R
import org.wit.archaeologicalfieldwork.helpers.ImageHolder

class ImageAdapter(images: List<String>) : RecyclerView.Adapter<ImageHolder>() {

  private val imageUrls: List<String> = images

  override fun getItemCount(): Int {
    return imageUrls.size;
  }

  override fun onBindViewHolder(holder: ImageHolder, position: Int) {
    var imageUrl = imageUrls[position]
    holder?.updateWithUrl(imageUrl)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
    var imageItem = LayoutInflater.from(parent?.context).inflate(R.layout.image_item, parent, false)
    return ImageHolder(imageItem)
  }
}