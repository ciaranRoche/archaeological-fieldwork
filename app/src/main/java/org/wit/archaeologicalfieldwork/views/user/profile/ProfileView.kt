package org.wit.archaeologicalfieldwork.views.user.profile

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.startActivity
import org.wit.archaeologicalfieldwork.R
import org.wit.archaeologicalfieldwork.models.user.UserModel
import org.wit.archaeologicalfieldwork.views.user.settings.SettingsView

var loggeduser = UserModel()

class ProfileActivity : AppCompatActivity(), AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        setSupportActionBar(toolbarProfile)

        if (intent.hasExtra("logged_in")) loggeduser = intent.extras.getParcelable<UserModel?>("logged_in")!!

        profile_name.text = loggeduser.name
        profile_email.text = loggeduser.email
        profile_member_date.text = loggeduser.joined
        profile_no_visit.text = loggeduser.stats.size.toString()
        if (loggeduser.userImage.isNotEmpty()) {
            Picasso.get().load(loggeduser.userImage)
                .config(Bitmap.Config.RGB_565)
                .resize(200, 200)
                .centerCrop()
                .into(profile_image)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_profile, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_edit -> startActivity<SettingsView>()
        }
        return super.onOptionsItemSelected(item)
    }
}
