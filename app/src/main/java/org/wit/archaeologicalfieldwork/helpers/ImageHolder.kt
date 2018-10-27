package org.wit.archaeologicalfieldwork.helpers

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.squareup.picasso.Picasso
import org.wit.archaeologicalfieldwork.R

class ImageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
  private val myImageView: ImageView = itemView.findViewById<ImageView>(R.id.myImageView)

  fun updateWithUrl(img: String) {
    Picasso.get().load(img).fit()
        .into(myImageView)
  }
}