package org.wit.archaeologicalfieldwork.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_hillfort.*
import org.wit.archaeologicalfieldwork.R
import org.wit.archaeologicalfieldwork.main.MainApp
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.wit.archaeologicalfieldwork.BuildConfig
import org.wit.archaeologicalfieldwork.helpers.readImage
import org.wit.archaeologicalfieldwork.helpers.readImageFromPath
import org.wit.archaeologicalfieldwork.helpers.showImagePicker
import org.wit.archaeologicalfieldwork.models.HillfortModel
import org.wit.archaeologicalfieldwork.models.Location

class HillfortActivity : AppCompatActivity(), AnkoLogger {

    lateinit var app : MainApp
    val IMAGE_REQUEST = 1
    val LOCATION_REQUEST = 2
    var location = Location(52.245696, -7.139102, 15f)
    var hillfort = HillfortModel(location = location)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort)

        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)

        app = application as MainApp


        if (intent.hasExtra("hillfort_edit")){
            btnAdd.setText(R.string.save_hillfort)
            hillfort = intent.extras.getParcelable<HillfortModel>("hillfort_edit")
            hillfortName.setText(hillfort.name)
            description.setText(hillfort.description)
            location = hillfort.location
            hillfortImage.setImageBitmap(readImageFromPath(this, hillfort.image))
        } else {
            btnDelete.visibility = View.INVISIBLE
        }

        btnDelete.setOnClickListener(){
            app.hillforts.delete(hillfort.copy())
            setResult(AppCompatActivity.RESULT_OK)
            finish()
        }

        btnAdd.setOnClickListener(){
            hillfort.name = hillfortName.text.toString()
            hillfort.description = description.text.toString()
            hillfort.location = location
            if(hillfort.name.isNotEmpty()){
                if(intent.hasExtra("hillfort_edit")){
                    app.hillforts.update(hillfort.copy())
                } else {
                    app.hillforts.create(hillfort.copy())
                }
                setResult(AppCompatActivity.RESULT_OK)
                finish()
            }else{
                toast("Please Enter a title")
            }
        }

        hillfortLocation.setOnClickListener(){
            startActivityForResult(intentFor<MapsActivity>().putExtra("location", location), LOCATION_REQUEST)
        }

        chooseImage.setOnClickListener{
            showImagePicker(this, IMAGE_REQUEST)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_hillfort, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode){
            IMAGE_REQUEST -> {
                if (data != null){
                    hillfort.image = data.getData().toString()
                    hillfortImage.setImageBitmap(readImage(this, resultCode, data))
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
