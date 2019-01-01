package org.wit.archaeologicalfieldwork.views.startup.startupfragments

import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import org.wit.archaeologicalfieldwork.models.data.DataFireStore
import org.wit.archaeologicalfieldwork.models.user.UserFireStore
import org.wit.archaeologicalfieldwork.models.user.UserModel
import org.wit.archaeologicalfieldwork.views.home.HomeView
import org.wit.archaeologicalfieldwork.views.startup.loggeduser
import org.wit.archaeologicalfieldwork.views.startup.userLogged

class LoginPresenter(val view: LogInFragment) : AnkoLogger {

    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var userFireStore: UserFireStore? = null
    var fireStore: DataFireStore? = null
    var user = UserModel()

    init {
        fireStore = DataFireStore(view.context!!)
        userFireStore = UserFireStore(view.context!!)
    }

    fun doLogin(email: String, password: String) {
        view.showProgress()
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(view.activity!!) { task ->
            if (task.isSuccessful) {
                if (fireStore != null) {
                    fireStore!!.fetchHillforts {
                        if (userFireStore != null) {
                            userFireStore!!.fetchUserProfile {
                                user = userFireStore!!.getUser(email)
                                view.hideProgress()
                                userLogged = true
                                loggeduser = user
                                view.login(loggeduser!!)
                            }
                        }
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