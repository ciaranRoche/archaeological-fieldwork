package org.wit.archaeologicalfieldwork.activities.profile

import android.content.Intent
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile_settings.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivityForResult
import org.wit.archaeologicalfieldwork.R
import org.wit.archaeologicalfieldwork.activities.hillfort.HillfortListActivity
import org.wit.archaeologicalfieldwork.helpers.showImagePicker
import org.wit.archaeologicalfieldwork.main.MainApp
import org.wit.archaeologicalfieldwork.models.UserModel

class ProfileSettingsActivity : AppCompatActivity(), AnkoLogger {

  lateinit var app : MainApp
  var user = UserModel()
  var IMAGE_REQUEST = 1
  var image = ""

  override fun onCreate(savedInstanceState: Bundle?) {
    setTheme(R.style.AppTheme)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_profile_settings)

    if(!userLogged){
      startActivityForResult<UserActivity>(0)
    }

    toolbarSettings.title = "Profile Settings"

    setSupportActionBar(toolbarSettings)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)

    app = application as MainApp

    // Todo Refactor to use only one, parceable or stored user var
    if (intent.hasExtra("edit_user")){
      user = intent.extras.getParcelable<UserModel>("edit_user")
      settingsUserName.setText(user.name)
      settingsUserEmail.setText(user.email)
      settingsUserPassword.setText(user.password)
      settingsUserVerifyPassword.setText(user.password)
      if(user.userImage.isNotEmpty()){
        profileImage.setText(R.string.button_updateProfileImage)
        Picasso.get().load(user.userImage)
            .config(Bitmap.Config.RGB_565)
            .resize(500,500)
            .centerCrop()
            .into(userImage)
      }
    } else {
      settingsUserName.setText(loggeduser.name)
      settingsUserEmail.setText(loggeduser.email)
      settingsUserPassword.setText(user.password)
      settingsUserVerifyPassword.setText(user.password)
      if(loggeduser.userImage.isNotEmpty()){
        profileImage.setText(R.string.button_updateProfileImage)
        Picasso.get().load(loggeduser.userImage)
            .config(Bitmap.Config.RGB_565)
            .resize(500,500)
            .centerCrop()
            .into(userImage)
      }
    }
    updateUser.setOnClickListener {
      user.name = settingsUserName.text.toString()
      user.email = settingsUserEmail.text.toString()
      user.password = settingsUserPassword.text.toString()
      if (user.password.equals(settingsUserVerifyPassword.text.toString())){
        if (user.name.isNotEmpty() and user.email.isNotEmpty() and user.password.isNotEmpty()){
          app.users.update(user.copy())
          loggeduser = user.copy()
          setResult(AppCompatActivity.RESULT_OK)
          startActivityForResult<ProfileActivity>(0)
        }
      }
    }

    deleteUser.setOnClickListener {
      app.users.delete(user.copy())
      setResult(AppCompatActivity.RESULT_OK)
      userLogged = false
      startActivityForResult<HillfortListActivity>(0)
    }
    profileImage.setOnClickListener{
      showImagePicker(this, IMAGE_REQUEST)
    }
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    when(requestCode){
      IMAGE_REQUEST -> {
        if (data != null){
          user.userImage = data.getData().toString()
          Picasso.get().load(user.userImage)
              .config(Bitmap.Config.RGB_565)
              .resize(500,500)
              .centerCrop()
              .into(userImage)
        }
      }
    }
  }


}
