package org.wit.archaeologicalfieldwork.views.hillfortprofile

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.AnkoLogger
import org.wit.archaeologicalfieldwork.R
import org.wit.archaeologicalfieldwork.adapters.ViewPagerAdapter
import org.wit.archaeologicalfieldwork.models.data.DataFireStore
import org.wit.archaeologicalfieldwork.models.data.DataModel
import android.content.Intent

class HillFortProfileFragment : Fragment(), AnkoLogger {
    lateinit var pagerAdapter: ViewPagerAdapter
    lateinit var ratingBar: RatingBar
    lateinit var hillfort: DataModel
    lateinit var fireStore: DataFireStore
    lateinit var shareBtn: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_hill_fort_profile, container, false)
        val nameTextview = view.findViewById<TextView>(R.id.hillfortProfileName)
        val descriptionTextView = view.findViewById<TextView>(R.id.hillfortProfileDescription)
        val imagePager = view.findViewById<ViewPager>(R.id.view_pager)
        shareBtn = view.findViewById(R.id.shareHillfort)
        ratingBar = view.findViewById(R.id.ratingBar)

        fireStore = DataFireStore(context!!)

        hillfort = arguments!!.getParcelable("hillfort") as DataModel

        addListenerOnRatingBar()

        nameTextview.text = hillfort.title
        descriptionTextView.text = hillfort.description
        ratingBar.rating = hillfort.rating
        pagerAdapter = ViewPagerAdapter(hillfort.images)
        imagePager.adapter = pagerAdapter

        shareBtn.setOnClickListener {
            val sharingIntent = Intent(android.content.Intent.ACTION_SEND)
            sharingIntent.type = "text/plain"
            val shareBody = "Hey I found this awesome hillfort here, lat : ${hillfort.location.lat} , lng : ${hillfort.location.lng}"
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "I found this cool hillfort")
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody)
            startActivity(Intent.createChooser(sharingIntent, "Share via"))
        }

        return view
    }

    fun addListenerOnRatingBar() {
        ratingBar.setOnRatingBarChangeListener { ratingBar, fl, b ->
            hillfort.rating = fl
            async(UI) {
                fireStore.update(hillfort)
            }
        }
    }

    companion object {
        fun newInstance(hillfort: DataModel): HillFortProfileFragment {
            val args = Bundle()
            args.putParcelable("hillfort", hillfort)
            val fragment = HillFortProfileFragment()
            fragment.arguments = args
            return fragment
        }
    }
}