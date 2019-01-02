package org.wit.archaeologicalfieldwork.adapters

import androidx.viewpager.widget.PagerAdapter
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso
import androidx.viewpager.widget.ViewPager

class ViewPagerAdapter(images: List<String>) : PagerAdapter() {

    var imageUrls = images
    private val MAX_VALUE = 200

    override fun isViewFromObject(view: View, any: Any): Boolean {
        return view == any
    }

    override fun getCount(): Int {
        return imageUrls.size * MAX_VALUE
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageview: ImageView = ImageView(container.context)
        Picasso.get()
            .load(imageUrls[position % imageUrls.size])
            .fit()
            .into(imageview)
        container.addView(imageview)
        return imageview
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        (container as ViewPager).removeView(`object` as View)
    }
}