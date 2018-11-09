package org.wit.archaeologicalfieldwork.activities.user

import android.annotation.SuppressLint
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_user.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.startActivityForResult
import org.wit.archaeologicalfieldwork.R
import org.wit.archaeologicalfieldwork.activities.HomeActivity
import org.wit.archaeologicalfieldwork.activities.user.fragments.LogInFragment
import org.wit.archaeologicalfieldwork.activities.user.fragments.SignupFragment
import org.wit.archaeologicalfieldwork.activities.user.fragments.WelcomeFragment
import org.wit.archaeologicalfieldwork.main.MainApp

var userLogged = false

class UserActivity : HomeActivity(), AnkoLogger {
    lateinit var app: MainApp

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        if (userLogged) {
            startActivityForResult<ProfileActivity>(0)
        } else {
            val contentView = layoutInflater.inflate(R.layout.activity_user, null, false)
            drawer_layout.addView(contentView, 0)
            toolbarAuth.title = "Auth"
            setSupportActionBar(toolbarAuth)

            val welcomeFragment = WelcomeFragment()
            val manager = supportFragmentManager
            val transaction = manager.beginTransaction()
            transaction.replace(R.id.fragment_container, welcomeFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        app = application as MainApp

        logIn?.setOnClickListener {
            val logInFragment = LogInFragment()
            val manager = supportFragmentManager
            val transaction = manager.beginTransaction()
            transaction.replace(R.id.fragment_container, logInFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        signUp?.setOnClickListener {
            val signupFragment = SignupFragment()
            val manager = supportFragmentManager
            val transaction = manager.beginTransaction()
            transaction.replace(R.id.fragment_container, signupFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }
}
