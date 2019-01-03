package org.wit.archaeologicalfieldwork.views.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.startActivity
import org.wit.archaeologicalfieldwork.R
import org.wit.archaeologicalfieldwork.views.startup.StartUpView
import org.wit.archaeologicalfieldwork.views.startup.userLogged

class HomePresenter(val view: HomeView) {

    fun doLogout() {
        userLogged = false
        FirebaseAuth.getInstance().signOut()
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