package org.wit.archaeologicalfieldwork.adapters

import androidx.viewpager.widget.PagerAdapter
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso

class ViewPagerAdapter(images: List<String>) : PagerAdapter() {
    var imageUrls = images

    override fun isViewFromObject(view: View, any: Any): Boolean {
        return view == any
    }

    override fun getCount(): Int {
        return imageUrls.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageview: ImageView = ImageView(container.context)
        Picasso.get()
            .load(imageUrls[position])
            .fit()
            .into(imageview)
        container.addView(imageview)
        return imageview
    }
}