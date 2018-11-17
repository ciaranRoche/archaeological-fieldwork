package org.wit.archaeologicalfieldwork.views.home

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import org.jetbrains.anko.startActivity
import org.wit.archaeologicalfieldwork.R
import org.wit.archaeologicalfieldwork.views.startup.StartUpView
import org.wit.archaeologicalfieldwork.views.startup.userLogged

class HomePresenter(val view: HomeView) {

    fun doLogout() {
        userLogged = false
        view.startActivity<StartUpView>()
    }

    fun doCheckUser() {
        if (!userLogged) {
            view.startActivity<StartUpView>()
        }
    }

    fun openFragment(fragment: Fragment, support: FragmentManager) {
        val transaction = support.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}