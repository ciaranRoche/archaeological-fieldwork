package org.wit.archaeologicalfieldwork.views.hillfortlist

import android.content.Intent
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import kotlinx.android.synthetic.main.fragment_hillfort_list.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.archaeologicalfieldwork.R
import org.wit.archaeologicalfieldwork.adapters.HillfortListener
import org.wit.archaeologicalfieldwork.adapters.HillfortPagerAdapter
import org.wit.archaeologicalfieldwork.models.hillfort.HillfortJSONStore
import org.wit.archaeologicalfieldwork.models.hillfort.HillfortModel
import org.wit.archaeologicalfieldwork.models.hillfort.HillfortStore

class HillfortListActivity : AppCompatActivity(), HillfortListener, AnkoLogger {

    lateinit var hillforts: HillfortStore
    lateinit var viewPager: ViewPager
    lateinit var pagerAdapter: HillfortPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_hillfort_list)
        hillforts = HillfortJSONStore(applicationContext)

        toolbarList.title = "Hill Fort Profiles"
        setSupportActionBar(toolbarList)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        info("boop ${hillforts.findAll()}")
        viewPager = findViewById(R.id.viewPager)
        pagerAdapter = HillfortPagerAdapter(supportFragmentManager, hillforts.findAll() as ArrayList<HillfortModel>)
        viewPager.adapter = pagerAdapter

        // val layoutManager = LinearLayoutManager(this)
        // recyclerView.layoutManager = layoutManager
        // loadHillforts()
    }

    override fun onHillfortClick(hillfort: HillfortModel) {
        // startActivityForResult(intentFor<HillFortProfileActivity>().putExtra("hillfort", hillfort), 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        loadHillforts()
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_list, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun loadHillforts() {
        showHillforts(hillforts.findAll())
    }

    fun showHillforts(hillforts: List<HillfortModel>) {
        // recyclerView.adapter = HillfortAdapter(hillforts, this)
        // recyclerView.adapter?.notifyDataSetChanged()
    }
}