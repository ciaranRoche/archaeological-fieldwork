package org.wit.archaeologicalfieldwork.views.startup.startupfragments

import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import org.wit.archaeologicalfieldwork.models.data.DataFireStore
import org.wit.archaeologicalfieldwork.models.user.UserFireStore
import org.wit.archaeologicalfieldwork.models.user.UserModel
import org.wit.archaeologicalfieldwork.views.home.HomeView
import org.wit.archaeologicalfieldwork.views.startup.userLogged

class SignupPresenter(val view: SignupFragment) {

    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var fireStore: DataFireStore? = null
    var userFireStore: UserFireStore? = null
    var newUser = UserModel()

    init {
        fireStore = DataFireStore(view.context!!)
        userFireStore = UserFireStore(view.context!!)
    }

    fun doSignUp(email: String, password: String, name: String) {
        view.showProgress()
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(view.activity!!) { task ->
            if (task.isSuccessful) {
                if (fireStore != null) {
                    if (userFireStore != null) {
                        newUser.email = email
                        newUser.name = name
                        userFireStore!!.create(newUser.copy())
                        view.hideProgress()
                        userLogged = true
                        view.startActivity<HomeView>()
                    }
                }
            } else {
                view.hideProgress()
                view.toast("Sign Up Failed : ${task.exception?.message}")
            }
        }
    }
}