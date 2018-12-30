package org.wit.archaeologicalfieldwork.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.archaeologicalfieldwork.models.hillfort.HillfortModel
import org.wit.archaeologicalfieldwork.views.hillfortprofile.HillFortProfileFragment

class HillfortPagerAdapter(fragmentManager: FragmentManager, private val hillforts: ArrayList<HillfortModel>) : FragmentStatePagerAdapter(fragmentManager), AnkoLogger {

    override fun getCount(): Int {
        return hillforts.size
    }

    override fun getItem(position: Int): Fragment {
        info("boop $hillforts")
        return HillFortProfileFragment.newInstance(hillforts[position])
    }
}