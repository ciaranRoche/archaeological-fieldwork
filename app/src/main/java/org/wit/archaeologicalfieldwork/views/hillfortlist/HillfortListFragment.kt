package org.wit.archaeologicalfieldwork.views.hillfortlist

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.wit.archaeologicalfieldwork.R
import org.wit.archaeologicalfieldwork.adapters.HillfortAdapter
import org.wit.archaeologicalfieldwork.adapters.HillfortListener
import org.wit.archaeologicalfieldwork.main.MainApp
import org.wit.archaeologicalfieldwork.models.hillfort.HillfortModel

class HillfortListFragment : Fragment(), HillfortListener {

    lateinit var app: MainApp
    lateinit var layoutManager: LinearLayoutManager
    lateinit var hillforts: ArrayList<HillfortModel>
    lateinit var recycler: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_hillfort_list, container, false)

        hillforts = arguments!!.getParcelableArrayList("hillforts")

        layoutManager = LinearLayoutManager(this.context)
        recycler = view.findViewById(R.id.recyclerView)
        recycler.layoutManager = layoutManager
        loadHillforts()

        return view
    }

    companion object {
        fun newInstance(hillforts: ArrayList<HillfortModel>): HillfortListFragment {
            val args = Bundle()
            args.putParcelableArrayList("hillforts", hillforts)
            val fragment = HillfortListFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onHillfortClick(hillfort: HillfortModel) {
        // startActivityForResult(intentFor<HillFortProfileActivity>().putExtra("hillfort", hillfort), 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        loadHillforts()
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun loadHillforts() {
        showHillforts(hillforts)
    }

    fun showHillforts(hillforts: ArrayList<HillfortModel>) {
        recycler.adapter = HillfortAdapter(hillforts, this)
        recycler.adapter?.notifyDataSetChanged()
    }
}
