package org.wit.archaeologicalfieldwork.views.hillfortlist

import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import kotlinx.android.synthetic.main.fragment_hillfort_list.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.delay
import org.jetbrains.anko.AnkoLogger
import org.wit.archaeologicalfieldwork.R
import org.wit.archaeologicalfieldwork.adapters.HillfortListener
import org.wit.archaeologicalfieldwork.adapters.HillfortPagerAdapter
import org.wit.archaeologicalfieldwork.models.data.DataFireStore
import org.wit.archaeologicalfieldwork.models.data.DataModel
import org.wit.archaeologicalfieldwork.models.hillfort.HillfortModel
import com.nshmura.recyclertablayout.RecyclerTabLayout

class HillfortListActivity : AppCompatActivity(), HillfortListener, AnkoLogger {

    lateinit var hillforts: ArrayList<DataModel>
    lateinit var viewPager: ViewPager
    lateinit var pagerAdapter: HillfortPagerAdapter
    lateinit var data: DataFireStore
    private lateinit var recyclerTabLayout: RecyclerTabLayout
    var favorites: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_hillfort_list)

        if (intent.hasExtra("favorites")) {
            favorites = intent.extras.getBoolean("favorites")
        }

        data = DataFireStore(applicationContext)

        async(UI) {
            hillforts = data.findAll()
            delay(1000)
            setSupportActionBar(toolbarList)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            viewPager = findViewById(R.id.viewPager)
            if (favorites) {
                toolbarList.title = "HillFort Favorites"
                val favoritesList = ArrayList<DataModel>()
                hillforts.forEach { if (it.rating > 4) favoritesList.add(it) }
                pagerAdapter = HillfortPagerAdapter(supportFragmentManager, favoritesList as ArrayList<DataModel>)
            } else {
                toolbarList.title = "HillFort Profiles"
                pagerAdapter = HillfortPagerAdapter(supportFragmentManager, hillforts as ArrayList<DataModel>)
            }
            viewPager.adapter = pagerAdapter
            viewPager.currentItem = pagerAdapter.count / 2
            recyclerTabLayout = findViewById(R.id.recyclerTabLayout)
            recyclerTabLayout.setUpWithViewPager(viewPager)
        }
    }

    override fun onHillfortClick(hillfort: HillfortModel) {
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_list, menu)
        return super.onCreateOptionsMenu(menu)
    }
}