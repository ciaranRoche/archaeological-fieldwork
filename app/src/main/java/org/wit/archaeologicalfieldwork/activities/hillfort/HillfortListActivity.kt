package org.wit.archaeologicalfieldwork.activities.hillfort

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import kotlinx.android.synthetic.main.activity_hillfort_list.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult
import org.wit.archaeologicalfieldwork.R
import org.wit.archaeologicalfieldwork.activities.profile.UserActivity
import org.wit.archaeologicalfieldwork.activities.profile.userLogged
import org.wit.archaeologicalfieldwork.main.MainApp
import org.wit.archaeologicalfieldwork.models.HillfortModel


class HillfortListActivity : AppCompatActivity(), HillfortListener {

  lateinit var app: MainApp

  override fun onCreate(savedInstanceState: Bundle?) {
    setTheme(R.style.AppTheme)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_hillfort_list)
    app = application as MainApp

    if(!userLogged){
      startActivityForResult<UserActivity>(0)
    }

    toolbarMain.title = title
    setSupportActionBar(toolbarMain)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)

    val layoutManager = LinearLayoutManager(this)
    recyclerView.layoutManager = layoutManager
    loadHillforts()
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      R.id.item_add -> startActivityForResult<HillfortActivity>(0)
      R.id.profile -> startActivityForResult<UserActivity>(0)
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onHillfortClick(hillfort: HillfortModel) {
    startActivityForResult(intentFor<HillFortProfileActivity>().putExtra("hillfort", hillfort), 0)
    //startActivityForResult<HillFortProfileActivity>(0)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    loadHillforts()
    super.onActivityResult(requestCode, resultCode, data)
  }

  private fun loadHillforts(){
    showHillforts(app.hillforts.findAll())
  }

  fun showHillforts(hillforts:List<HillfortModel>){
    recyclerView.adapter = HillfortAdapter(hillforts, this)
    recyclerView.adapter?.notifyDataSetChanged()
  }
}

