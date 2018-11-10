package org.wit.archaeologicalfieldwork.views.startup

import android.support.v4.app.FragmentManager
import android.widget.Button
import org.wit.archaeologicalfieldwork.R
import org.wit.archaeologicalfieldwork.views.startup.startupfragments.LogInFragment
import org.wit.archaeologicalfieldwork.views.startup.startupfragments.SignupFragment

class StartUpPresenter(val view: StartUpView){

    var signup = false

    fun loadFragment(signupBtn: Button, support: FragmentManager){
        if(signup){
            loadLogin(support)
            signupBtn.text = "Sign Up"
            signup = false
        } else {
            loadSignup(support)
            signupBtn.text = "Login"
            signup = true
        }
    }

    fun loadLogin(support: FragmentManager){
        val logInFragment = LogInFragment()
        val manager = support
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.fragment_container, logInFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun loadSignup(support: FragmentManager){
        val signupFragment = SignupFragment()
        val manager = support
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.fragment_container, signupFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}