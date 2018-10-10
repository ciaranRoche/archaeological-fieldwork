package org.wit.archaeologicalfieldwork.models

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class HillfortMemStore : HillfortStore, AnkoLogger {

  val hillforts = ArrayList<HillfortModel>()

  override fun findAll(): List<HillfortModel> {
    return hillforts
  }

  override fun create(hillfort: HillfortModel) {
    hillforts.add(hillfort)
    logAll()
  }

  fun logAll() {
    hillforts.forEach{info("${it}")}
  }

  override fun update(hillfort: HillfortModel) {
    var foundHillfort: HillfortModel? = hillforts.find {h -> h.id == hillfort.id}
    if (foundHillfort != null){
      hillfort.name = hillfort.name
      hillfort.description = hillfort.description
      logAll()
    }
  }


}