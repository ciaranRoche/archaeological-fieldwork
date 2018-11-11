package org.wit.archaeologicalfieldwork.activities

import org.jetbrains.anko.startActivity
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
}