package org.wit.archaeologicalfieldwork.views.startup.startupfragments

import android.view.View
import android.widget.ProgressBar
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import org.wit.archaeologicalfieldwork.views.home.HomeView

class LoginPresenter (val view: LogInFragment) {

    var auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun doLogin(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(view.activity!!) { task ->
            if(task.isSuccessful){
                view.startActivity<HomeView>()
            } else {
                view.toast("Incorrect Details")
            }
        }
    }

    fun showProgress(progressBar: ProgressBar) {
        progressBar.visibility = View.VISIBLE
    }

    fun hideProgress(progressBar: ProgressBar) {
        progressBar.visibility = View.GONE
    }

}