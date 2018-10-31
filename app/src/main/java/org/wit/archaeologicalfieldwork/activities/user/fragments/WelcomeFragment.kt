package org.wit.archaeologicalfieldwork.activities.user.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.wit.archaeologicalfieldwork.R

class WelcomeFragment : Fragment(){


  override fun onCreate(savedInstanceState: Bundle?){
    super.onCreate(savedInstanceState)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val view = inflater.inflate(R.layout.welcome_fragment,container,false)
    return view
  }
}


