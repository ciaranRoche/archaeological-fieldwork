package org.wit.archaeologicalfieldwork.views.hillfort

import android.annotation.SuppressLint
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.jetbrains.anko.AnkoLogger
import org.wit.archaeologicalfieldwork.helpers.checkLocationPermissions
import org.wit.archaeologicalfieldwork.helpers.getDate
import org.wit.archaeologicalfieldwork.helpers.isPermissionGranted
import org.wit.archaeologicalfieldwork.helpers.showImagePicker
import org.wit.archaeologicalfieldwork.main.MainApp
import org.wit.archaeologicalfieldwork.models.hillfort.HillfortJSONStore
import org.wit.archaeologicalfieldwork.models.hillfort.HillfortModel
import org.wit.archaeologicalfieldwork.models.location.Location
import org.wit.archaeologicalfieldwork.models.stats.StatsModel
import org.wit.archaeologicalfieldwork.models.user.UserJSONStore
import org.wit.archaeologicalfieldwork.models.user.UserModel

class HillfortPresenter(val view: HillfortFragment) : AnkoLogger {
    lateinit var app: MainApp

    var hillfortStore: HillfortJSONStore = HillfortJSONStore(view.context!!)
    var userStore: UserJSONStore = UserJSONStore(view.context!!)

    var defaultLocation = Location(52.245696, -7.139102, 15f)
    var locationService: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(view.activity!!)

    var hillfort = HillfortModel()
    var stat = StatsModel()

    var edit = view.arguments!!.getBoolean("edit")

    var map: GoogleMap? = null

    init {
        if (edit) {
            hillfort = view.arguments!!.getParcelable("hillfort")
        } else {
            if (checkLocationPermissions(view.activity!!)) {
                doSetCurrentLocation()
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun doSetCurrentLocation() {
        locationService.lastLocation.addOnSuccessListener {
            locationUpdate(it.latitude, it.longitude)
        }
    }

    fun doRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (isPermissionGranted(requestCode, grantResults)) {
            doSetCurrentLocation()
        } else {
            // permissions denied, so use the default location
            locationUpdate(defaultLocation.lat, defaultLocation.lng)
        }
    }

    fun doConfigureMap(m: GoogleMap) {
        map = m
        locationUpdate(hillfort.location.lat, hillfort.location.lng)
    }

    fun locationUpdate(lat: Double, lng: Double) {
        hillfort.location.lat = lat
        hillfort.location.lng = lng
        hillfort.location.zoom = 15f
        map?.clear()
        map?.uiSettings?.setZoomControlsEnabled(true)
        map?.setOnMarkerDragListener(view)
        val options = MarkerOptions().title(hillfort.name).position(LatLng(hillfort.location.lat, hillfort.location.lng)).draggable(true)
        map?.addMarker(options)
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(hillfort.location.lat, hillfort.location.lng), hillfort.location.zoom))
    }

    fun doMarkerDrag(marker: Marker) {
        hillfort.location.lat = marker.position.latitude
        hillfort.location.lng = marker.position.longitude
        hillfort.location.zoom = map?.cameraPosition!!.zoom
    }

    fun getLocation(): Location {
        return hillfort.location
    }

    fun doAddOrSave(hillfort: HillfortModel) {
        if (edit) {
            hillfortStore.update(hillfort)
        } else {
            hillfortStore.create(hillfort)
        }
    }

    fun edit(): Boolean {
        return edit
    }

    fun doDelete() {
        hillfortStore.delete(hillfort)
    }

    fun doSelectImage(parent: HillfortFragment, req: Int) {
        showImagePicker(parent, req)
    }

    fun doVisit(user: UserModel) {
        val foundStats = user.stats
        val foundHillfort = foundStats!!.find { s -> s.hillfort == hillfort.id }
        if (foundHillfort == null) {
            stat.hillfort = hillfort.id
            stat.date = getDate()
            user.stats.add(stat)
        } else {
            stat.date = getDate()
            user.stats[user.stats.indexOf(stat)] = stat
        }
        userStore.update(user)
    }
}