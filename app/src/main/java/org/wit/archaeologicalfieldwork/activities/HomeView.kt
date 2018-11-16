package org.wit.archaeologicalfieldwork.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_home.*
import org.jetbrains.anko.AnkoLogger
import org.wit.archaeologicalfieldwork.R
import org.wit.archaeologicalfieldwork.main.MainApp
import org.wit.archaeologicalfieldwork.models.hillfort.HillfortModel
import org.wit.archaeologicalfieldwork.models.user.UserModel
import org.wit.archaeologicalfieldwork.views.hillfortlist.HillfortListFragment
import org.wit.archaeologicalfieldwork.views.home.HomeFragment
import org.wit.archaeologicalfieldwork.views.user.profile.ProfileFragment
import org.wit.archaeologicalfieldwork.views.user.settings.SettingsFragment

open class HomeView : AppCompatActivity(), AnkoLogger {

    lateinit var user: UserModel
    lateinit var presenter: HomePresenter
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        presenter = HomePresenter(this)

        app = application as MainApp

        presenter.doCheckUser()

        val bottomNavigation: BottomNavigationView = findViewById(R.id.navigationView)
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        if (intent.hasExtra("user")) {
            user = intent.extras.getParcelable<UserModel>("user")
        }

        app_toolbar.title = title
        setSupportActionBar(app_toolbar)

        val homeFragment = HomeFragment.newInstance(user.name)
        presenter.openFragment(homeFragment, supportFragmentManager)
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_profile -> {
                val profileFragment = ProfileFragment.newInstance(user)
                presenter.openFragment(profileFragment, supportFragmentManager)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_hillforts -> {
                val hillforts: ArrayList<HillfortModel> = app.hillforts.findAll() as ArrayList<HillfortModel>
                val hillfortListFragment = HillfortListFragment.newInstance(hillforts)
                presenter.openFragment(hillfortListFragment, supportFragmentManager)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_location -> {

                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_logout -> {
                presenter.doLogout()
                return true
            }
            R.id.menu_add_hillfort -> {
                return true
            }
            R.id.menu_profile_settings -> {
                val settingsFragment = SettingsFragment.newInstance(user)
                presenter.openFragment(settingsFragment, supportFragmentManager)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
