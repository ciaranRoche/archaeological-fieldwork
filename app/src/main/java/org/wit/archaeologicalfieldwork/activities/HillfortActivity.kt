package org.wit.archaeologicalfieldwork.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import org.wit.archaeologicalfieldwork.R
import org.wit.archaeologicalfieldwork.main.MainApp

class HillfortActivity : AppCompatActivity() {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort)

        app = application as MainApp
    }
}
