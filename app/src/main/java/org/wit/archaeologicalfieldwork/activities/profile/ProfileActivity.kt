package org.wit.archaeologicalfieldwork.activities.profile

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_profile.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
import org.wit.archaeologicalfieldwork.R
import org.wit.archaeologicalfieldwork.activities.HillfortActivity
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

    if(intent.hasExtra("logged_in")){
      user = intent.extras.getParcelable<UserModel?>("logged_in")!!
      profile_name.text = user.name
      profile_email.text = user.email
    } else {
      user = app.users.findUserId(userid)
      profile_name.text = user.name
      profile_email.text = user.email
    }
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_profile, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      R.id.item_settings -> startActivityForResult(intentFor<ProfileSettingsActivity>().putExtra("edit_user", user), 0)
    }
    return super.onOptionsItemSelected(item)
  }
}
