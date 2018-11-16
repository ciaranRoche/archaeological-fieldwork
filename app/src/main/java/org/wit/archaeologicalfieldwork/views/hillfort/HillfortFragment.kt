package org.wit.archaeologicalfieldwork.views.hillfort

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox

import org.jetbrains.anko.AnkoLogger
import org.wit.archaeologicalfieldwork.R
import org.wit.archaeologicalfieldwork.models.hillfort.HillfortModel

class HillfortFragment : Fragment(), AnkoLogger {

    lateinit var presenter: HillfortPresenter

    var hillfort = HillfortModel()
    var IMAGE_REQUEST = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        presenter = HillfortPresenter(this)

        if(presenter.edit){
            hillfort = arguments!!.getParcelable("hillfort") as HillfortModel
        }

        val view = inflater.inflate(R.layout.fragment_hillfort, container, false)
        val name: TextInputEditText? = view?.findViewById(R.id.hillfortName)
        val description: TextInputEditText? = view?.findViewById(R.id.description)
        val visited: CheckBox? = view?.findViewById(R.id.visitedBox)
        val imageBtn: Button? = view?.findViewById(R.id.chooseImage)
        val locationBtn: Button? = view?.findViewById(R.id.hillfortLocation)
        val addBtn: Button? = view?.findViewById(R.id.btnAdd)
        val deleteBtn: Button? = view?.findViewById(R.id.btnDelete)

        addBtn?.setOnClickListener {
            hillfort.name = name?.text.toString()
            hillfort.description = description?.text.toString()
            hillfort.location = presenter.location
            if (hillfort.name.isNotEmpty()) {
                presenter.doAddOrSave(hillfort.copy())
            }
            presenter.redirectList(this.fragmentManager!!)
        }

        imageBtn?.setOnClickListener {
            presenter.doSelectImage(this, IMAGE_REQUEST)
        }

        deleteBtn?.setOnClickListener {
            presenter.doDelete()
        }
        return view
    }

    companion object {
        fun newInstance(hillfort: HillfortModel): HillfortFragment {
            val args = Bundle()
            args.putParcelable("hillfort", hillfort)
            val fragment = HillfortFragment()
            fragment.arguments = args
            return fragment
        }
        fun blankInstance(): HillfortFragment{
            return HillfortFragment()
        }
    }

//        hillfortLocation.setOnClickListener {
//            startActivityForResult(intentFor<MapsActivity>().putExtra("location", location), LOCATION_REQUEST)
//        }
//
//        visitedBox.setOnClickListener {
//            toast("Hillfort Visited")
//            val foundStats = app.users.getStats(loggeduser)
//            val foundHillfort = foundStats!!.find { s -> s.hillfort == hillfort.id }
//            if (foundHillfort == null) {
//                stat.hillfort = hillfort.id
//                stat.date = getDate()
//                loggeduser.stats.add(stat)
//            } else {
//                stat.date = getDate()
//                loggeduser.stats[loggeduser.stats.indexOf(stat)] = stat
//            }
//            app.users.update(loggeduser)
//        }
//    }
//
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IMAGE_REQUEST -> {
                if (data != null) {
                    val image: String = data.data.toString()
                    hillfort.images += image
                }
            }
//            LOCATION_REQUEST -> {
//                if (data != null) {
//                    location = data.extras.getParcelable<Location>("location")
//                }
//            }
        }
    }
}
