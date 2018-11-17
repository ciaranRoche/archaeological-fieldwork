package org.wit.archaeologicalfieldwork.views.hillfort

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.widget.Button
import org.jetbrains.anko.support.v4.toast
import org.wit.archaeologicalfieldwork.R
import org.wit.archaeologicalfieldwork.helpers.getDate
import org.wit.archaeologicalfieldwork.helpers.showImagePicker
import org.wit.archaeologicalfieldwork.main.MainApp
import org.wit.archaeologicalfieldwork.models.hillfort.HillfortJSONStore
import org.wit.archaeologicalfieldwork.models.hillfort.HillfortModel
import org.wit.archaeologicalfieldwork.models.location.Location
import org.wit.archaeologicalfieldwork.models.stats.StatsModel
import org.wit.archaeologicalfieldwork.models.user.UserJSONStore
import org.wit.archaeologicalfieldwork.models.user.UserModel
import org.wit.archaeologicalfieldwork.views.hillfortlist.HillfortListFragment

class HillfortPresenter(val view: HillfortFragment) {
    lateinit var app: MainApp
    var hillfortStore: HillfortJSONStore = HillfortJSONStore(view.context!!)
    var userStore: UserJSONStore = UserJSONStore(view.context!!)
    var location = Location(52.245696, -7.139102, 15f)
    var hillfort = HillfortModel()
    var stat = StatsModel()
    var edit = view.arguments!!.getBoolean("edit")

    init {
        if (edit) {
            hillfort = view.arguments!!.getParcelable("hillfort")
        }
    }

    fun doAddOrSave(hillfort: HillfortModel) {
        if (edit) {
            hillfortStore.update(hillfort)
        } else {
            hillfortStore.create(hillfort)
        }
    }

    fun edit() : Boolean{
        return edit
    }

    fun doDelete() {
        hillfortStore.delete(hillfort)
    }

    fun doSelectImage(parent: HillfortFragment, req: Int) {
        showImagePicker(parent, req)
    }

    fun doVisit(user: UserModel){
        val foundStats = user.stats
        val foundHillfort = foundStats!!.find { s -> s.hillfort == hillfort.id }
        if (foundHillfort == null) {
            stat.hillfort = hillfort.id
            stat.date = getDate()
            user.stats.add(stat)
        } else {
            stat.date = getDate()
            user.stats[user.stats.indexOf(stat)] = stat
        }
        userStore.update(user)
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