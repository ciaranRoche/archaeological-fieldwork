package org.wit.archaeologicalfieldwork.activities.profile

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_profile.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast
import org.wit.archaeologicalfieldwork.R
import org.wit.archaeologicalfieldwork.main.MainApp
import org.wit.archaeologicalfieldwork.models.UserModel

class ProfileActivity : AppCompatActivity(), AnkoLogger {

  lateinit var app : MainApp
  var user = UserModel()

  override fun onCreate(savedInstanceState: Bundle?) {
    setTheme(R.style.AppTheme)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_profile)

    toolbarProfile.title = "Profile"

    setSupportActionBar(toolbarProfile)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)

    app = application as MainApp

    saveUser.setOnClickListener {
      user.name = userName.text.toString()
      user.email = userEmail.text.toString()
      user.password = userPassword.text.toString()
      if(user.email.isNotEmpty() and user.password.isNotEmpty()){
        app.users.create(user.copy())
        setResult(AppCompatActivity.RESULT_OK)
        finish()
      }else{
        toast("Please fill out email and password")
      }
    }
  }
}
