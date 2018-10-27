package org.wit.archaeologicalfieldwork.activities.profile

import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_profile_settings.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult
import org.wit.archaeologicalfieldwork.R
import org.wit.archaeologicalfieldwork.activities.HillfortListActivity
import org.wit.archaeologicalfieldwork.helpers.readImageFromPath
import org.wit.archaeologicalfieldwork.main.MainApp
import org.wit.archaeologicalfieldwork.models.UserModel

var loggeduser = UserModel()

class ProfileActivity : AppCompatActivity(), AnkoLogger {

  lateinit var app : MainApp

  override fun onCreate(savedInstanceState: Bundle?) {
    setTheme(R.style.AppTheme)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_profile)

    toolbarProfile.title = "Profile"

    setSupportActionBar(toolbarProfile)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)

    app = application as MainApp

    if(intent.hasExtra("logged_in")){
      loggeduser = intent.extras.getParcelable<UserModel?>("logged_in")!!
      profile_name.text = loggeduser.name
      profile_email.text = loggeduser.email
      if(loggeduser.userImage.isNotEmpty()){
        Picasso.get().load(loggeduser.userImage)
            .config(Bitmap.Config.RGB_565)
            .resize(200,200)
            .centerCrop()
            .into(profile_image)
      }
    } else {
      profile_name.text = loggeduser.name
      profile_email.text = loggeduser.email
      if(loggeduser.userImage.isNotEmpty()){
        Picasso.get().load(loggeduser.userImage)
            .config(Bitmap.Config.RGB_565)
            .resize(200,200)
            .centerCrop()
            .into(profile_image)
      }
    }
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_profile, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      R.id.item_settings -> startActivityForResult(intentFor<ProfileSettingsActivity>().putExtra("edit_user", loggeduser), 0)
      R.id.item_logout -> logoutUser()
    }
    return super.onOptionsItemSelected(item)
  }

  private fun logoutUser() {
    userLogged = false
    startActivityForResult<HillfortListActivity>(0)
  }
}
