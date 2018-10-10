package org.wit.archaeologicalfieldwork.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_hillfort.*
import org.wit.archaeologicalfieldwork.R
import org.wit.archaeologicalfieldwork.main.MainApp
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import org.wit.archaeologicalfieldwork.models.HillfortModel

class HillfortActivity : AppCompatActivity(), AnkoLogger {

    lateinit var app: MainApp

    var hillfort = HillfortModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort)

        app = application as MainApp

        btnAdd.setOnClickListener(){
            hillfort.name = hillfortName.text.toString()
            if(hillfort.name.isNotEmpty()){
                info("add Button Pressed : $hillfort")
            }else{
                toast("Please Enter a title")
            }
        }
    }
}
