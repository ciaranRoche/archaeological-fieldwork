package org.wit.archaeologicalfieldwork.views.startup.startupfragments

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.support.v4.toast
import org.wit.archaeologicalfieldwork.helpers.getDate
import org.wit.archaeologicalfieldwork.main.MainApp
import org.wit.archaeologicalfieldwork.models.data.DataFireStore
import org.wit.archaeologicalfieldwork.models.user.UserFireStore
import org.wit.archaeologicalfieldwork.models.user.UserModel
import org.wit.archaeologicalfieldwork.views.startup.loggeduser
import org.wit.archaeologicalfieldwork.views.startup.userLogged

class SignupPresenter(val view: SignupFragment) : AnkoLogger {

    var app: MainApp = view?.context!!.applicationContext as MainApp

    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var fireStore: DataFireStore? = null
    var userFireStore: UserFireStore? = null
    var newUser = UserModel()
    var user = UserModel()

    init {
        fireStore = app.hillforts as DataFireStore
        userFireStore = app.users as UserFireStore
    }

    fun doSignUp(email: String, password: String, name: String) {
        view.showProgress()
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(view.activity!!) { task ->
            if (task.isSuccessful) {
                if (fireStore != null) {
                    if (userFireStore != null) {
                        newUser.email = email
                        newUser.name = name
                        newUser.joined = getDate()
                        async(UI) {
                            userFireStore!!.create(newUser.copy())
                            userFireStore!!.fetchUserProfile {
                                async(UI) {
                                    user = userFireStore!!.getUser(email)
                                    view.hideProgress()
                                    userLogged = true
                                    loggeduser = user
                                    view.login(loggeduser!!)
                                }
                            }
                        }
                    }
                }
            } else {
                view.hideProgress()
                view.toast("Sign Up Failed : ${task.exception?.message}")
            }
        }
    }
}