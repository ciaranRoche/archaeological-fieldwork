package org.wit.archaeologicalfieldwork.views.startup

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.startActivityForResult
import org.wit.archaeologicalfieldwork.R
import org.wit.archaeologicalfieldwork.activities.user.ProfileActivity
import org.wit.archaeologicalfieldwork.views.startup.startupfragments.LogInFragment
import org.wit.archaeologicalfieldwork.main.MainApp

var userLogged = false

class StartUpView : AppCompatActivity(), AnkoLogger {
    lateinit var app: MainApp

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        if (userLogged) {
            startActivityForResult<ProfileActivity>(0)
        } else {
            setContentView(R.layout.view_start_up)

            val welcomeFragment = LogInFragment()
            val manager = supportFragmentManager
            val transaction = manager.beginTransaction()
            transaction.replace(R.id.fragment_container, welcomeFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        app = application as MainApp

//        logIn?.setOnClickListener {
//            val logInFragment = LogInFragment()
//            val manager = supportFragmentManager
//            val transaction = manager.beginTransaction()
//            transaction.replace(R.id.fragment_container, logInFragment)
//            transaction.addToBackStack(null)
//            transaction.commit()
//        }

//        signUp?.setOnClickListener {
//            val signupFragment = SignupFragment()
//            val manager = supportFragmentManager
//            val transaction = manager.beginTransaction()
//            transaction.replace(R.id.fragment_container, signupFragment)
//            transaction.addToBackStack(null)
//            transaction.commit()
//        }
    }
}
