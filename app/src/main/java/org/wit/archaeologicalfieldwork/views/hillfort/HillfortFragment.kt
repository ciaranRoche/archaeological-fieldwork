package org.wit.archaeologicalfieldwork.views.hillfort

import android.content.Intent
import android.os.Bundle
import com.google.android.material.textfield.TextInputEditText
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RatingBar
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.Marker

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.toast
import org.wit.archaeologicalfieldwork.R
import org.wit.archaeologicalfieldwork.models.data.DataModel
import org.wit.archaeologicalfieldwork.models.hillfort.HillfortModel
import org.wit.archaeologicalfieldwork.models.user.UserModel
import org.wit.archaeologicalfieldwork.views.hillfortlist.HillfortListActivity

class HillfortFragment : Fragment(), AnkoLogger, GoogleMap.OnMarkerDragListener {

    lateinit var presenter: HillfortPresenter

    var hillfort = DataModel()
    var user = UserModel()
    var IMAGE_REQUEST = 1
    lateinit var mapView: MapView
    lateinit var nameText: TextInputEditText
    lateinit var descriptionText: TextInputEditText
    lateinit var ratingBar: RatingBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        presenter = HillfortPresenter(this)

//        if (presenter.edit) {
//            hillfort = arguments!!.getParcelable("hillfort") as HillfortModel
//        }

        user = arguments!!.getParcelable("user") as UserModel

        val view = inflater.inflate(R.layout.fragment_hillfort, container, false)
        nameText = view.findViewById(R.id.hillfortName)
        descriptionText = view.findViewById(R.id.description)
        val imageBtn: Button? = view?.findViewById(R.id.chooseImage)
        val addBtn: Button? = view?.findViewById(R.id.btnAdd)
        val deleteBtn: Button? = view?.findViewById(R.id.btnDelete)
        ratingBar = view.findViewById(R.id.ratingBarAdd)
        mapView = view.findViewById(R.id.mapView)

        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync {
            presenter.doConfigureMap(it)
        }

        handleButton(deleteBtn, view)

        ratingBar.setOnRatingBarChangeListener { ratingBar, fl, b ->
            hillfort.rating = fl
        }

        addBtn?.setOnClickListener {
            hillfort.title = nameText.text.toString()
            hillfort.description = descriptionText.text.toString()
            hillfort.location = presenter.getLocation()
            if (hillfort.title.isNotEmpty()) {
                presenter.doAddOrSave(hillfort.copy())
            }
            toast("${hillfort.title} has been added")
            startActivityForResult(intentFor<HillfortListActivity>(), 0)
            activity!!.setResult(AppCompatActivity.RESULT_OK)
            activity!!.finish()
        }

        imageBtn?.setOnClickListener {
            presenter.doSelectImage(this, IMAGE_REQUEST)
        }

        deleteBtn?.setOnClickListener {
            presenter.doDelete()
            activity!!.finish()
        }

        return view
    }

    fun handleButton(button: Button?, view: View) {
        if (!presenter.edit()) button?.visibility = View.INVISIBLE
    }

    companion object {
        fun newInstance(hillfort: HillfortModel?, user: UserModel, edit: Boolean): HillfortFragment {
            val args = Bundle()
            args.putParcelable("hillfort", hillfort)
            args.putParcelable("user", user)
            args.putBoolean("edit", edit)
            val fragment = HillfortFragment()
            fragment.arguments = args
            return fragment
        }
        fun blankInstance(user: UserModel, edit: Boolean): HillfortFragment {
            val args = Bundle()
            args.putParcelable("user", user)
            args.putBoolean("edit", edit)
            val fragment = HillfortFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IMAGE_REQUEST -> {
                if (data != null) {
                    val image: String = data.data.toString()
                    hillfort.images += image
                }
            }
        }
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

    override fun onMarkerDragEnd(marker: Marker?) {
    }

    override fun onMarkerDragStart(marker: Marker?) {
    }

    override fun onMarkerDrag(marker: Marker) {
        presenter.doMarkerDrag(marker)
    }
}
