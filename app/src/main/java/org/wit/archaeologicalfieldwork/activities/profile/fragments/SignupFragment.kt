package org.wit.archaeologicalfieldwork.activities.profile.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.jetbrains.anko.AnkoLogger
import org.wit.archaeologicalfieldwork.R

class SignupFragment : Fragment(), AnkoLogger{
  override fun onCreate(savedInstanceState: Bundle?){
    super.onCreate(savedInstanceState)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val view = inflater!!.inflate(R.layout.signup_fragment, container, false)

    return view
  }
}