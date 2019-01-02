package org.wit.archaeologicalfieldwork.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import org.jetbrains.anko.AnkoLogger
import org.wit.archaeologicalfieldwork.models.data.DataModel
import org.wit.archaeologicalfieldwork.views.hillfortprofile.HillFortProfileFragment

class HillfortPagerAdapter(fragmentManager: FragmentManager, private val hillforts: ArrayList<DataModel>) : FragmentStatePagerAdapter(fragmentManager), AnkoLogger {

    private val MAX_VALUE = 200

    override fun getCount(): Int {
        return hillforts.size * MAX_VALUE
    }

    override fun getItem(position: Int): Fragment {
        return HillFortProfileFragment.newInstance(hillforts[position % hillforts.size])
    }

    override fun getPageTitle(position: Int): CharSequence {
        return hillforts[position % hillforts.size].title
    }
}