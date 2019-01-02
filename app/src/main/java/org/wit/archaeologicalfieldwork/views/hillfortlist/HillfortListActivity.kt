package org.wit.archaeologicalfieldwork.views.hillfortlist

import android.content.Intent
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

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_hillfort_list)

        data = DataFireStore(applicationContext)

        async(UI) {
            hillforts = data.findAll()
            delay(1000)
            toolbarList.title = "Hill Fort Profiles"
            setSupportActionBar(toolbarList)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            viewPager = findViewById(R.id.viewPager)
            pagerAdapter = HillfortPagerAdapter(supportFragmentManager, hillforts as ArrayList<DataModel>)
            viewPager.adapter = pagerAdapter
            viewPager.currentItem = pagerAdapter.count / 2
            recyclerTabLayout = findViewById(R.id.recyclerTabLayout)
            recyclerTabLayout.setUpWithViewPager(viewPager)
        }

        // val layoutManager = LinearLayoutManager(this)
        // recyclerView.layoutManager = layoutManager
        // loadHillforts()
    }

    override fun onHillfortClick(hillfort: HillfortModel) {
        // startActivityForResult(intentFor<HillFortProfileActivity>().putExtra("hillfort", hillfort), 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        loadHillforts()
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_list, menu)
        return super.onCreateOptionsMenu(menu)
    }

//    private fun loadHillforts() {
//        async (UI) {
//            showHillforts(hillforts.findAll())
//        }
//    }

    fun showHillforts(hillforts: ArrayList<DataModel>) {
        // recyclerView.adapter = HillfortAdapter(hillforts, this)
        // recyclerView.adapter?.notifyDataSetChanged()
    }
}