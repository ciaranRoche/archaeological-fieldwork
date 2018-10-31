package org.wit.archaeologicalfieldwork.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.wit.archaeologicalfieldwork.models.hillfort.HillfortJSONStore
import org.wit.archaeologicalfieldwork.models.hillfort.HillfortStore
import org.wit.archaeologicalfieldwork.models.user.UserJSONStore
import org.wit.archaeologicalfieldwork.models.user.UserStore

class MainApp : Application(), AnkoLogger {

  lateinit var hillforts: HillfortStore
  lateinit var users: UserStore

  override fun onCreate() {
    super.onCreate()
    hillforts = HillfortJSONStore(applicationContext)
    users = UserJSONStore(applicationContext)
  }
}