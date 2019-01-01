package org.wit.archaeologicalfieldwork.views.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.support.v4.intentFor
import org.wit.archaeologicalfieldwork.R
import org.wit.archaeologicalfieldwork.main.MainApp
import org.wit.archaeologicalfieldwork.models.data.DataFireStore
import org.wit.archaeologicalfieldwork.models.data.DataModel
import org.wit.archaeologicalfieldwork.models.data.DataStore
import org.wit.archaeologicalfieldwork.models.user.UserFireStore
import org.wit.archaeologicalfieldwork.models.user.UserModel
import org.wit.archaeologicalfieldwork.models.user.UserStore
import org.wit.archaeologicalfieldwork.views.hillfort.HillfortFragment
import org.wit.archaeologicalfieldwork.views.hillfortlist.HillfortListActivity
import org.wit.archaeologicalfieldwork.views.maps.HillfortMapFragment
import org.wit.archaeologicalfieldwork.views.user.profile.ProfileFragment
import org.wit.archaeologicalfieldwork.views.user.profile.loggeduser
import org.wit.archaeologicalfieldwork.views.user.settings.SettingsFragment

open class HomeView : AppCompatActivity(), AnkoLogger {

    lateinit var user: UserModel
    lateinit var presenter: HomePresenter
    lateinit var app: MainApp
    lateinit var hillforts: DataStore
    lateinit var data: DataFireStore
    lateinit var users: UserStore

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        presenter = HomePresenter(this)

        hillforts = DataFireStore(applicationContext)
        users = UserFireStore(applicationContext)
        data = DataFireStore(applicationContext)

        presenter.doCheckUser()

        val bottomNavigation: BottomNavigationView = findViewById(R.id.navigationView)
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        if (intent.hasExtra("user")) {
            user = intent.extras.getParcelable<UserModel>("user")
        } else {
            user = loggeduser
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
                var fortsArray = ArrayList<DataModel>()
                data.fetchHillforts {
                    async(UI) {
                        fortsArray = data.findAll()
                        info("boop $fortsArray")
                        val hillfortMapFragment = HillfortMapFragment.newInstance(fortsArray as ArrayList<DataModel>)
                        presenter.openFragment(hillfortMapFragment, supportFragmentManager)
                    }
                }
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_add_hillfort -> {
                val hillfortFragment = HillfortFragment.blankInstance(user, false)
                presenter.openFragment(hillfortFragment, supportFragmentManager)
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
            R.id.menu_profile_settings -> {
                val settingsFragment = SettingsFragment.newInstance(user)
                presenter.openFragment(settingsFragment, supportFragmentManager)
                return true
            }
            R.id.menu_profiles -> {
                startActivityForResult(intentFor<HillfortListActivity>(), 0)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
