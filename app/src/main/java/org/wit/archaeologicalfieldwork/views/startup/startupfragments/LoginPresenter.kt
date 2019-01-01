package org.wit.archaeologicalfieldwork.views.startup.startupfragments

import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import org.wit.archaeologicalfieldwork.models.data.DataFireStore
import org.wit.archaeologicalfieldwork.views.home.HomeView
import org.wit.archaeologicalfieldwork.views.startup.userLogged

class LoginPresenter(val view: LogInFragment) {

    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var fireStore: DataFireStore? = null

    init {
        fireStore = DataFireStore(view.context!!)
    }

    fun doLogin(email: String, password: String) {
        view.showProgress()
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(view.activity!!) { task ->
            if (task.isSuccessful) {
                if (fireStore != null) {
                    fireStore!!.fetchHillforts {
                        view.hideProgress()
                        userLogged = true
                        view.startActivity<HomeView>()
                    }
                } else {
                    view.hideProgress()
                    userLogged = true
                    view.startActivity<HomeView>()
                }
            } else {
                view.hideProgress()
                view.toast("Login Failed : ${task.exception?.message}")
            }
        }
    }
}