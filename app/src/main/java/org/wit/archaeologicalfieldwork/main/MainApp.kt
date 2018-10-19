package org.wit.archaeologicalfieldwork.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.archaeologicalfieldwork.models.HillfortJSONStore
import org.wit.archaeologicalfieldwork.models.HillfortStore
import org.wit.archaeologicalfieldwork.models.UserJSONStore
import org.wit.archaeologicalfieldwork.models.UserStore

class MainApp : Application(), AnkoLogger {

  lateinit var hillforts: HillfortStore
  lateinit var users: UserStore

  override fun onCreate() {
    super.onCreate()
    hillforts = HillfortJSONStore(applicationContext)
    users = UserJSONStore(applicationContext)
    info("HillFort Application Started")
  }
}