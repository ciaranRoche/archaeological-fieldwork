package org.wit.archaeologicalfieldwork.views.maps

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.wit.archaeologicalfieldwork.R

import org.wit.archaeologicalfieldwork.models.hillfort.HillfortModel

class HillfortMapFragment : Fragment(), GoogleMap.OnMarkerClickListener {

    lateinit var map: GoogleMap
    lateinit var mapView: MapView
    var hillforts = ArrayList<HillfortModel>()

    lateinit var currentTitle: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_hillfort_map, container, false)
        currentTitle = view.findViewById(R.id.currentTitle)
        hillforts = arguments!!.getParcelableArrayList("hillforts")

        mapView = view.findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync {
            map = it
            configureMap()
        }
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    fun configureMap() {
        map.uiSettings.setZoomControlsEnabled(true)
        hillforts.forEach {
            val loc = LatLng(it.location.lat, it.location.lng)
            val options = MarkerOptions().title(it.name).position(loc)
            map.addMarker(options).tag = it.id
            map.setOnMarkerClickListener(this)
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.location.zoom))
        }
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        currentTitle.text = marker.title
        return false
    }

    companion object {
        fun newInstance(hillforts: ArrayList<HillfortModel>): HillfortMapFragment {
            val args = Bundle()
            args.putParcelableArrayList("hillforts", hillforts)
            val fragment = HillfortMapFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
