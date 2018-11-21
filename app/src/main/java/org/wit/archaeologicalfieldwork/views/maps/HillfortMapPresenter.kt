package org.wit.archaeologicalfieldwork.views.maps

import com.google.android.gms.maps.model.Marker
import org.wit.archaeologicalfieldwork.models.hillfort.HillfortJSONStore
import org.wit.archaeologicalfieldwork.models.hillfort.HillfortModel

class HillfortMapPresenter(view: HillfortMapFragment) {

    var hillfortStore: HillfortJSONStore = HillfortJSONStore(view.context!!)

    fun getHillfort(marker: Marker): HillfortModel {
        return hillfortStore.findById(marker.tag as Long)
    }
}