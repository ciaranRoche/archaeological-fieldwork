package org.wit.archaeologicalfieldwork.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.archaeologicalfieldwork.models.HillfortModel

class MainApp : Application(), AnkoLogger {

  val hillforts = ArrayList<HillfortModel>()

  override fun onCreate() {
    super.onCreate()
    info("HillFort Application Started")
    hillforts.add(HillfortModel("Boop", "Snoop"))
    hillforts.add(HillfortModel("Doop", "Noop"))
  }
}