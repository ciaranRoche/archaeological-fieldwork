package org.wit.archaeologicalfieldwork.activities.hillfort

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_hillfort_list.*
import kotlinx.android.synthetic.main.activity_home.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult
import org.wit.archaeologicalfieldwork.R
import org.wit.archaeologicalfieldwork.activities.HomeActivity
import org.wit.archaeologicalfieldwork.adapters.HillfortAdapter
import org.wit.archaeologicalfieldwork.adapters.HillfortListener
import org.wit.archaeologicalfieldwork.activities.user.UserActivity
import org.wit.archaeologicalfieldwork.activities.user.userLogged
import org.wit.archaeologicalfieldwork.main.MainApp
import org.wit.archaeologicalfieldwork.models.hillfort.HillfortModel

class HillfortListActivity : HomeActivity(), HillfortListener {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        app = application as MainApp

        if (!userLogged) {
            startActivityForResult<UserActivity>(0)
        }

        val contentView = layoutInflater.inflate(R.layout.activity_hillfort_list, null, false)
        drawer_layout.addView(contentView, 0)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        loadHillforts()
    }

    override fun onHillfortClick(hillfort: HillfortModel) {
        startActivityForResult(intentFor<HillFortProfileActivity>().putExtra("hillfort", hillfort), 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        loadHillforts()
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun loadHillforts() {
        showHillforts(app.hillforts.findAll())
    }

    fun showHillforts(hillforts: List<HillfortModel>) {
        recyclerView.adapter = HillfortAdapter(hillforts, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }
}
