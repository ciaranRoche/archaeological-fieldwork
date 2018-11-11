package org.wit.archaeologicalfieldwork.views.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.jetbrains.anko.AnkoLogger
import org.wit.archaeologicalfieldwork.R

class HomeFragment : Fragment(), AnkoLogger {

    var username: String? = ""

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val name: TextView? = view.findViewById(R.id.welcome_message)
        username = arguments?.getString("username")
        name?.text = "Welcome $username to Archaeological Fieldwork."
        return view
    }


    companion object {
        fun newInstance(username: String): HomeFragment {
            val args = Bundle()
            args.putString("username", username)
            val fragment = HomeFragment()
            fragment.arguments = args
            return fragment
        }
    }
}