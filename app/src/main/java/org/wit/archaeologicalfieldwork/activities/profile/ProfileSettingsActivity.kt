package org.wit.archaeologicalfieldwork.activities.profile

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_profile_settings.*
import org.jetbrains.anko.startActivityForResult
import org.wit.archaeologicalfieldwork.R
import org.wit.archaeologicalfieldwork.activities.HillfortActivity
import org.wit.archaeologicalfieldwork.activities.HillfortListActivity
import org.wit.archaeologicalfieldwork.main.MainApp
import org.wit.archaeologicalfieldwork.models.UserModel

class ProfileSettingsActivity : AppCompatActivity() {

  lateinit var app : MainApp
  var user = UserModel()

  override fun onCreate(savedInstanceState: Bundle?) {
    setTheme(R.style.AppTheme)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_profile_settings)

    toolbarSettings.title = "Profile Settings"

    setSupportActionBar(toolbarSettings)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)

    app = application as MainApp

    if (intent.hasExtra("edit_user")){
      user = intent.extras.getParcelable<UserModel>("edit_user")
      settingsUserName.setText(user.name)
      settingsUserEmail.setText(user.email)
      settingsUserPassword.setText(user.password)
      settingsUserVerifyPassword.setText(user.password)
    }

    updateUser.setOnClickListener {
      user.name = settingsUserName.text.toString()
      user.email = settingsUserEmail.text.toString()
      user.password = settingsUserPassword.text.toString()
      if (user.password.equals(settingsUserVerifyPassword.text.toString())){
        if (user.name.isNotEmpty() and user.email.isNotEmpty() and user.password.isNotEmpty()){
          app.users.update(user.copy())
          setResult(AppCompatActivity.RESULT_OK)
          startActivityForResult<ProfileActivity>(0)
        }
      }
    }

    deleteUser.setOnClickListener {
      app.users.delete(user.copy())
      setResult(AppCompatActivity.RESULT_OK)
      userLogged = false
      userid = 0L
      startActivityForResult<HillfortListActivity>(0)
    }
  }


}
