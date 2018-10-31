package org.wit.archaeologicalfieldwork.activities.profile

import android.content.Intent
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_profile_settings.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivityForResult
import org.wit.archaeologicalfieldwork.R
import org.wit.archaeologicalfieldwork.activities.HomeActivity
import org.wit.archaeologicalfieldwork.activities.hillfort.HillfortListActivity
import org.wit.archaeologicalfieldwork.helpers.showImagePicker
import org.wit.archaeologicalfieldwork.main.MainApp
import org.wit.archaeologicalfieldwork.models.UserModel

class ProfileSettingsActivity : HomeActivity(), AnkoLogger {

  lateinit var app : MainApp
  var user = UserModel()
  var IMAGE_REQUEST = 1
  var image = ""

  override fun onCreate(savedInstanceState: Bundle?) {
    setTheme(R.style.AppTheme)
    super.onCreate(savedInstanceState)
    val contentView = layoutInflater.inflate(R.layout.activity_profile_settings, null, false)
    drawer_layout.addView(contentView, 0)

    if(!userLogged){
      startActivityForResult<UserActivity>(0)
    }

    app = application as MainApp

    settingsUserName.setText(loggeduser.name)
    settingsUserEmail.setText(loggeduser.email)
    settingsUserPassword.setText(loggeduser.password)
    settingsUserVerifyPassword.setText(loggeduser.password)
    if(loggeduser.userImage.isNotEmpty()){
      profileImage.setText(R.string.button_updateProfileImage)
      Picasso.get().load(loggeduser.userImage)
          .config(Bitmap.Config.RGB_565)
          .resize(500,500)
          .centerCrop()
          .into(userImage)
    }

    updateUser.setOnClickListener {
      loggeduser.name = settingsUserName.text.toString()
      loggeduser.email = settingsUserEmail.text.toString()
      loggeduser.password = settingsUserPassword.text.toString()
      if (loggeduser.password.equals(settingsUserVerifyPassword.text.toString())){
        if (loggeduser.name.isNotEmpty() and loggeduser.email.isNotEmpty() and loggeduser.password.isNotEmpty()){
          info("BOOP ${loggeduser}")
          app.users.update(loggeduser.copy())
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
          loggeduser.userImage = data.getData().toString()
          Picasso.get().load(loggeduser.userImage)
              .config(Bitmap.Config.RGB_565)
              .resize(500,500)
              .centerCrop()
              .into(userImage)
        }
      }
    }
  }


}
