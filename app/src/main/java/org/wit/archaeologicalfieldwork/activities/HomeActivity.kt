package org.wit.archaeologicalfieldwork.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import org.jetbrains.anko.startActivityForResult
import org.wit.archaeologicalfieldwork.R
import org.wit.archaeologicalfieldwork.activities.hillfort.HillfortListActivity
import org.wit.archaeologicalfieldwork.activities.profile.ProfileSettingsActivity
import org.wit.archaeologicalfieldwork.activities.profile.UserActivity
import org.wit.archaeologicalfieldwork.activities.profile.userLogged

class HomeActivity : AppCompatActivity() {

  private lateinit var drawerLayout: DrawerLayout

  override fun onCreate(savedInstanceState: Bundle?) {
    setTheme(R.style.AppTheme)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_home)

    val toolbar: Toolbar = findViewById(R.id.toolbar)
    setSupportActionBar(toolbar)

    val actionbar: ActionBar? = supportActionBar
    actionbar?.apply {
      setDisplayHomeAsUpEnabled(true)
      setHomeAsUpIndicator(R.drawable.baseline_menu_white_18dp)
    }

    drawerLayout = findViewById(R.id.drawer_layout)

    val navigationView: NavigationView = findViewById(R.id.nav_view)
    navigationView.setNavigationItemSelectedListener { menuItem ->

      menuItem.isChecked = true

      when (menuItem.itemId){
        R.id.nav_profile -> {
          startActivityForResult<UserActivity>(0)
        }
        R.id.nav_hillfort -> {
          startActivityForResult<HillfortListActivity>(0)
        }
        R.id.nav_settings -> {
          startActivityForResult<ProfileSettingsActivity>(0)
        }
      }
      drawerLayout.closeDrawer(GravityCompat.START)
      true
   }

    drawerLayout.addDrawerListener(
        object : DrawerLayout.DrawerListener {
          override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
            // Respond when the drawer's position changes
          }

          override fun onDrawerOpened(drawerView: View) {
            // Respond when the drawer is opened
          }

          override fun onDrawerClosed(drawerView: View) {
            // Respond when the drawer is closed
          }

          override fun onDrawerStateChanged(newState: Int) {
            // Respond when the drawer motion state changes
          }
        }
    )


  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      android.R.id.home -> {
        drawerLayout.openDrawer(GravityCompat.START)
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }

}
