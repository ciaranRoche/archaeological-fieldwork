package org.wit.archaeologicalfieldwork.activities.profile

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_user.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivityForResult
import org.wit.archaeologicalfieldwork.R
import org.wit.archaeologicalfieldwork.activities.profile.fragments.LogInFragment
import org.wit.archaeologicalfieldwork.activities.profile.fragments.SignupFragment
import org.wit.archaeologicalfieldwork.main.MainApp


val userLogged = false

class UserActivity : AppCompatActivity(), AnkoLogger {
  lateinit var app: MainApp

  override fun onCreate(savedInstanceState: Bundle?) {
    setTheme(R.style.AppTheme)
    super.onCreate(savedInstanceState)
    if(userLogged){
      startActivityForResult<ProfileActivity>(0)
    }else {
      setContentView(R.layout.activity_user)
      toolbarAuth.title = "Auth"
      setSupportActionBar(toolbarAuth)
      supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    app = application as MainApp

    logIn?.setOnClickListener {
      info("Log In Clicked")
      val logInFragment = LogInFragment()
      val manager = supportFragmentManager
      val transaction = manager.beginTransaction()
      transaction.replace(R.id.fragment_container, logInFragment)
      transaction.addToBackStack(null)
      transaction.commit()
    }

    signUp?.setOnClickListener {
      info("Sign Up Clicked")
      val signupFragment = SignupFragment()
      val manager = supportFragmentManager
      val transaction = manager.beginTransaction()
      transaction.replace(R.id.fragment_container, signupFragment)
      transaction.addToBackStack(null)
      transaction.commit()
    }
  }
}
