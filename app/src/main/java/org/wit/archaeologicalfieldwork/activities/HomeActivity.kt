package org.wit.archaeologicalfieldwork.activities

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBar
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.startActivityForResult
import org.wit.archaeologicalfieldwork.R
import org.wit.archaeologicalfieldwork.activities.hillfort.HillfortActivity
import org.wit.archaeologicalfieldwork.activities.hillfort.HillfortListActivity
import org.wit.archaeologicalfieldwork.activities.user.ProfileSettingsActivity
import org.wit.archaeologicalfieldwork.views.startup.StartUpView
import org.wit.archaeologicalfieldwork.views.startup.userLogged

open class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, AnkoLogger {

    protected lateinit var drawerLayout: DrawerLayout
    protected lateinit var navigationView: NavigationView

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

        navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close)

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        toggle.isDrawerIndicatorEnabled = true
    }

    private inline fun <reified T : Activity> launch(): Boolean {
        if (this is T) return closeDrawer()
        val intent = Intent(applicationContext, T::class.java)
        startActivity(intent)
        finish()
        return true
    }

    private fun closeDrawer(): Boolean {
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.nav_profile -> {
                startActivityForResult<StartUpView>(0)
            }
            R.id.nav_hillfort -> {
                startActivityForResult<HillfortListActivity>(0)
            }
            R.id.nav_add_hillfort -> {
                startActivityForResult<HillfortActivity>(0)
            }
            R.id.nav_settings -> {
                startActivityForResult<ProfileSettingsActivity>(0)
            }
            R.id.nav_logout -> {
                userLogged = false
                startActivityForResult<StartUpView>(0)
            }
        }
        return false
    }
}
