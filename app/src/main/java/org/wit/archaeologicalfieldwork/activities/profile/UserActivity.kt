package org.wit.archaeologicalfieldwork.activities.profile

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_user.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivityForResult
import org.wit.archaeologicalfieldwork.R
import org.wit.archaeologicalfieldwork.activities.profile.fragments.LogInFragment
import org.wit.archaeologicalfieldwork.activities.profile.fragments.SignupFragment
import org.wit.archaeologicalfieldwork.activities.profile.fragments.WelcomeFragment
import org.wit.archaeologicalfieldwork.main.MainApp

var userLogged = false

class UserActivity : AppCompatActivity(), AnkoLogger {
  lateinit var app: MainApp

  @SuppressLint("ResourceAsColor")
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
