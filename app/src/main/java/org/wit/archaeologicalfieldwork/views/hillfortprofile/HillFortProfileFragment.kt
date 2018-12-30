package org.wit.archaeologicalfieldwork.views.hillfortprofile

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.wit.archaeologicalfieldwork.R
import org.wit.archaeologicalfieldwork.adapters.ViewPagerAdapter
import org.wit.archaeologicalfieldwork.models.hillfort.HillfortModel

class HillFortProfileFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_hill_fort_profile, container, false)
        val nameTextview = view.findViewById<TextView>(R.id.hillfortProfileName)
        val descriptionTextView = view.findViewById<TextView>(R.id.hillfortProfileDescription)
        val imagePager = view.findViewById<ViewPager>(R.id.view_pager)

        val hillfort = arguments!!.getParcelable("hillfort") as HillfortModel

        nameTextview.text = hillfort.name
        descriptionTextView.text = hillfort.description
        imagePager.adapter = ViewPagerAdapter(hillfort.images)

        return view
    }

    companion object {
        fun newInstance(hillfort: HillfortModel): HillFortProfileFragment {
            val args = Bundle()
            args.putParcelable("hillfort", hillfort)
            val fragment = HillFortProfileFragment()
            fragment.arguments = args
            return fragment
        }
    }
}