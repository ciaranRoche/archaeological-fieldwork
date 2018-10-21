package org.wit.archaeologicalfieldwork.activities.profile.fragments

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import org.wit.archaeologicalfieldwork.R
import android.widget.TextView
import android.widget.Toast
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info


class LogInFragment : Fragment(), AnkoLogger{
  override fun onCreate(savedInstanceState: Bundle?){
    super.onCreate(savedInstanceState)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val view = inflater!!.inflate(R.layout.login_fragment,container,false)
    val loginView = view.findViewById<TextView>(R.id.login_view)
    loginView.setOnClickListener {
      loginView.setTextColor(Color.RED)
      Toast.makeText(view.context, "Login Clicked", Toast.LENGTH_SHORT).show()
    }

   val button: Button? = view?.findViewById(R.id.boop)

    button?.setOnClickListener {
      info ("Hello")
    }

    return view
  }
}