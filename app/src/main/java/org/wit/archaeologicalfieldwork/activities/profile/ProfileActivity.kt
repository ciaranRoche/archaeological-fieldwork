package org.wit.archaeologicalfieldwork.activities.profile

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_hillfort.*
import org.jetbrains.anko.AnkoLogger
import org.wit.archaeologicalfieldwork.R

class ProfileActivity : AppCompatActivity(), AnkoLogger {

  override fun onCreate(savedInstanceState: Bundle?) {
    setTheme(R.style.AppTheme)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_profile)

    toolbarAdd.title = "Profile"

    setSupportActionBar(toolbarAdd)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
  }
}
