package org.wit.archaeologicalfieldwork.views.hillfort

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import org.wit.archaeologicalfieldwork.R
import org.wit.archaeologicalfieldwork.helpers.showImagePicker
import org.wit.archaeologicalfieldwork.main.MainApp
import org.wit.archaeologicalfieldwork.models.hillfort.HillfortJSONStore
import org.wit.archaeologicalfieldwork.models.hillfort.HillfortModel
import org.wit.archaeologicalfieldwork.models.location.Location
import org.wit.archaeologicalfieldwork.models.stats.StatsModel
import org.wit.archaeologicalfieldwork.views.hillfortlist.HillfortListFragment

class HillfortPresenter(val view: HillfortFragment) {
    lateinit var app: MainApp
    var hillfortStore: HillfortJSONStore = HillfortJSONStore(view.context!!)
    var location = Location(52.245696, -7.139102, 15f)
    var hillfort = HillfortModel()
    var stat = StatsModel()
    var edit = false

    init {
        if (view.arguments != null) {
            hillfort = view.arguments!!.getParcelable("hillfort")
            edit = true
        }
    }

    fun doAddOrSave(hillfort: HillfortModel) {
        if (edit) {
            hillfortStore.update(hillfort)
        } else {
            hillfortStore.create(hillfort)
        }
    }

    fun doCancel() {
    }

    fun doDelete() {
        hillfortStore.delete(hillfort)
    }

    fun doSelectImage(parent: HillfortFragment, req: Int) {
        showImagePicker(parent, req)
    }

    fun redirectList(support: FragmentManager) {
        val list = HillfortListFragment.newInstance(hillfortStore.findAll() as ArrayList<HillfortModel>)
        openFragment(list, support)
    }

    fun openFragment(fragment: Fragment, support: FragmentManager) {
        val transaction = support.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}