package org.wit.archaeologicalfieldwork.activities.hillfort

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.view.ViewPager
import android.view.*
import kotlinx.android.synthetic.main.activity_hill_fort_profile.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.wit.archaeologicalfieldwork.R
import org.wit.archaeologicalfieldwork.main.MainApp
import org.wit.archaeologicalfieldwork.models.HillfortModel
import org.wit.archaeologicalfieldwork.models.Location

class HillFortProfileActivity : AppCompatActivity() {

  lateinit var app : MainApp
  private lateinit var viewPager: ViewPager
  var location = Location(0.0, 0.0, 15f)
  var hillfort = HillfortModel()

  override fun onCreate(savedInstanceState: Bundle?) {
    setTheme(R.style.AppTheme)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_hill_fort_profile)

    toolbarHillfortProfile.title = "About Hillfort"

    setSupportActionBar(toolbarHillfortProfile)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)

    app = application as MainApp

    if(intent.hasExtra("hillfort")) {
      hillfort = intent.extras.getParcelable<HillfortModel>("hillfort")
      hillfortProfileName.setText(hillfort.name)
      hillfortProfileDescription.setText(hillfort.description)
      location = hillfort.location

      viewPager = findViewById(R.id.view_pager)
      viewPager.adapter = ViewPagerAdapter(hillfort.images)
    }

    val fab = findViewById(R.id.fab) as FloatingActionButton
    fab.setOnClickListener{
      toast("Fab Clicked")
    }
  }



  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_hillfort_profile, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId){
      R.id.item_edit -> startActivityForResult(intentFor<HillfortActivity>().putExtra("hillfort_edit", hillfort), 0)
    }
    return super.onOptionsItemSelected(item)
  }
}
