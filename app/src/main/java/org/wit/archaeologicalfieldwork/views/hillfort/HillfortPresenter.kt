package org.wit.archaeologicalfieldwork.views.hillfort

import org.wit.archaeologicalfieldwork.helpers.showImagePicker
import org.wit.archaeologicalfieldwork.main.MainApp
import org.wit.archaeologicalfieldwork.models.hillfort.HillfortModel
import org.wit.archaeologicalfieldwork.models.location.Location
import org.wit.archaeologicalfieldwork.models.stats.StatsModel

class HillfortPresenter(val view: HillfortFragment) {
    lateinit var app: MainApp
    val IMAGE_REQUEST = 1
    val LOCATION_REQUEST = 2
    var location = Location(52.245696, -7.139102, 15f)
    var hillfort = HillfortModel()
    var stat = StatsModel()
    val edit = false

    init {
        app = MainApp()
        hillfort = view.arguments!!.getParcelable("hillfort")
    }

    fun doAddOrSave(name: String, description: String) {
        hillfort.name = name
        hillfort.description = description
        if (edit) {
            app.hillforts.update(hillfort)
        } else {
            app.hillforts.create(hillfort)
        }
    }

    fun doCancel() {
    }

    fun doDelete() {
        app.hillforts.delete(hillfort)
    }

    fun doSelectImage() {
        showImagePicker(view, IMAGE_REQUEST)
    }
}