package org.wit.archaeologicalfieldwork.activities.user

import android.graphics.Bitmap
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_profile.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult
import org.wit.archaeologicalfieldwork.R
import org.wit.archaeologicalfieldwork.activities.HomeActivity
import org.wit.archaeologicalfieldwork.activities.hillfort.HillfortListActivity
import org.wit.archaeologicalfieldwork.main.MainApp
import org.wit.archaeologicalfieldwork.models.user.UserModel

var loggeduser = UserModel()

class ProfileActivity : HomeActivity(), AnkoLogger {

  lateinit var app : MainApp

  override fun onCreate(savedInstanceState: Bundle?) {
    setTheme(R.style.AppTheme)
    super.onCreate(savedInstanceState)
    val contentView = layoutInflater.inflate(R.layout.activity_profile, null, false)
    drawer_layout.addView(contentView, 0)

    app = application as MainApp

    // Todo : refactor this
    if(intent.hasExtra("logged_in")){
      loggeduser = intent.extras.getParcelable<UserModel?>("logged_in")!!
      profile_name.text = loggeduser.name
      profile_email.text = loggeduser.email
      profile_member_date.text = loggeduser.joined
      profile_no_visit.text = loggeduser.stats.size.toString()
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
      profile_member_date.text = loggeduser.joined
      profile_no_visit.text = loggeduser.stats.size.toString()
      if(loggeduser.userImage.isNotEmpty()){
        Picasso.get().load(loggeduser.userImage)
            .config(Bitmap.Config.RGB_565)
            .resize(200,200)
            .centerCrop()
            .into(profile_image)
      }
    }
  }

}
