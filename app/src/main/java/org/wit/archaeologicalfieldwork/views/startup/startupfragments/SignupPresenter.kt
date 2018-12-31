package org.wit.archaeologicalfieldwork.views.startup.startupfragments

import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import org.wit.archaeologicalfieldwork.models.data.DataFireStore
import org.wit.archaeologicalfieldwork.views.home.HomeView
import org.wit.archaeologicalfieldwork.views.startup.userLogged

class SignupPresenter(val view: SignupFragment) {

    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var fireStore: DataFireStore? = null

    init {
        fireStore = DataFireStore(view.context!!)
    }

    fun doSignUp(email: String, password: String) {
        view.showProgress()
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(view.activity!!) { task ->
            if (task.isSuccessful) {
                if (fireStore != null) {
                    view.hideProgress()
                    userLogged = true
                    view.startActivity<HomeView>()
                }
            } else {
                view.hideProgress()
                view.toast("Sign Up Failed : ${task.exception?.message}")
            }
        }
    }
}