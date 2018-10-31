package org.wit.archaeologicalfieldwork.activities.hillfort

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_hillfort.*
import kotlinx.android.synthetic.main.activity_home.*
import org.jetbrains.anko.*
import org.wit.archaeologicalfieldwork.R
import org.wit.archaeologicalfieldwork.activities.HomeActivity
import org.wit.archaeologicalfieldwork.main.MainApp
import org.wit.archaeologicalfieldwork.activities.maps.MapsActivity
import org.wit.archaeologicalfieldwork.activities.profile.UserActivity
import org.wit.archaeologicalfieldwork.activities.profile.loggeduser
import org.wit.archaeologicalfieldwork.activities.profile.userLogged
import org.wit.archaeologicalfieldwork.helpers.getDate
import org.wit.archaeologicalfieldwork.helpers.showImagePicker
import org.wit.archaeologicalfieldwork.models.HillfortModel
import org.wit.archaeologicalfieldwork.models.Location
import org.wit.archaeologicalfieldwork.models.StatsModel
import java.text.SimpleDateFormat
import java.util.*

class HillfortActivity : HomeActivity(), AnkoLogger {

  lateinit var app: MainApp
  val IMAGE_REQUEST = 1
  val LOCATION_REQUEST = 2
  var location = Location(52.245696, -7.139102, 15f)
  var hillfort = HillfortModel(location = location)
  var stat = StatsModel()

  override fun onCreate(savedInstanceState: Bundle?) {
    setTheme(R.style.AppTheme)
    super.onCreate(savedInstanceState)

    if(!userLogged){
      startActivityForResult<UserActivity>(0)
    }

    val contentView = layoutInflater.inflate(R.layout.activity_hillfort, null, false)
    drawer_layout.addView(contentView, 0)

    toolbarAdd.title = title

    setSupportActionBar(toolbarAdd)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)

    app = application as MainApp


    if (intent.hasExtra("hillfort_edit")) {
      btnAdd.setText(R.string.save_hillfort)
      hillfort = intent.extras.getParcelable<HillfortModel>("hillfort_edit")
      hillfortName.setText(hillfort.name)
      description.setText(hillfort.description)
      location = hillfort.location
      if (loggeduser.stats.isNotEmpty()) {
        val found = loggeduser.stats.find { h -> h.hillfort == hillfort.id }
        if (found != null) {
          visitedBox.isChecked = true
          visitedBox.text = String.format("Visited on ${found.date}")
        }
      }
    } else {
      btnDelete.visibility = View.INVISIBLE
    }


    btnDelete.setOnClickListener {
      app.hillforts.delete(hillfort.copy())
      setResult(AppCompatActivity.RESULT_OK)
      finish()
    }

    btnAdd.setOnClickListener {
      hillfort.name = hillfortName.text.toString()
      hillfort.description = description.text.toString()
      hillfort.location = location
      if (hillfort.name.isNotEmpty()) {
        if (intent.hasExtra("hillfort_edit")) {
          app.hillforts.update(hillfort.copy())
        } else {
          app.hillforts.create(hillfort.copy())
        }
        setResult(AppCompatActivity.RESULT_OK)
        finish()
      } else {
        toast("Please Enter a title")
      }
    }

    hillfortLocation.setOnClickListener {
      startActivityForResult(intentFor<MapsActivity>().putExtra("location", location), LOCATION_REQUEST)
    }

    chooseImage.setOnClickListener {
      showImagePicker(this, IMAGE_REQUEST)
    }

    visitedBox.setOnClickListener {
      toast("Hillfort Visited")
      val foundStats = app.users.getStats(loggeduser)
      val foundHillfort = foundStats!!.find { s -> s.hillfort == hillfort.id }
      if(foundHillfort == null){
        stat.hillfort = hillfort.id
        stat.date = getDate()
        loggeduser.stats.add(stat)
      } else {
        stat.date = getDate()
        loggeduser.stats[loggeduser.stats.indexOf(stat)] = stat
      }
      app.users.update(loggeduser)
    }
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_hillfort, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      R.id.item_cancel -> {
        finish()
      }
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    when (requestCode) {
      IMAGE_REQUEST -> {
        if (data != null) {
          val image: String = data.data.toString()
          hillfort.images += image
          toast("Image Uploaded")
        }
      }
      LOCATION_REQUEST -> {
        if (data != null) {
          location = data.extras.getParcelable<Location>("location")
        }
      }
    }
  }
}

