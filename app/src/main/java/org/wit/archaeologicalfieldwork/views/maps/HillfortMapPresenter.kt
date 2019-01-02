package org.wit.archaeologicalfieldwork.views.maps

import com.google.android.gms.maps.model.Marker
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.AnkoLogger
import org.wit.archaeologicalfieldwork.models.data.DataFireStore
import org.wit.archaeologicalfieldwork.models.data.DataModel

class HillfortMapPresenter(view: HillfortMapFragment) : AnkoLogger {

    var hillfortStore: DataFireStore = DataFireStore(view.context!!)
    var hillfort = DataModel()

    fun getHillfort(marker: Marker): DataModel? {
        async(UI) { hillfort = hillfortStore.findById(marker.tag as Long)!! }
        return hillfort
    }
}