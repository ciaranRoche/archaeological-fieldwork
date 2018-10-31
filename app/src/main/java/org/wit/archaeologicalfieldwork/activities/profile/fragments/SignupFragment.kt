package org.wit.archaeologicalfieldwork.activities.profile.fragments

import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.toast
import org.mindrot.jbcrypt.BCrypt
import org.wit.archaeologicalfieldwork.R
import org.wit.archaeologicalfieldwork.activities.profile.ProfileActivity
import org.wit.archaeologicalfieldwork.activities.profile.loggeduser
import org.wit.archaeologicalfieldwork.activities.profile.userLogged
import org.wit.archaeologicalfieldwork.helpers.getDate
import org.wit.archaeologicalfieldwork.models.UserJSONStore
import org.wit.archaeologicalfieldwork.models.UserModel
import org.wit.archaeologicalfieldwork.models.UserStore

class SignupFragment : Fragment(), AnkoLogger{

  var user = UserModel()
  lateinit var users: UserStore

  override fun onCreate(savedInstanceState: Bundle?){
    super.onCreate(savedInstanceState)
    users = UserJSONStore(this.context!!)

  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val view = inflater.inflate(R.layout.signup_fragment, container, false)
    val name: TextInputEditText? = view.findViewById(R.id.fragment_userName)
    val email: TextInputEditText? = view.findViewById(R.id.fragment_userEmail)
    val password: TextInputEditText? = view.findViewById(R.id.fragment_userPassword)
    val verifyPassword: TextInputEditText? = view.findViewById(R.id.fragment_verifyPassword)
    val submit: Button? = view.findViewById(R.id.fragment_saveUser)

    submit?.setOnClickListener {
      user.name = name?.text.toString()
      user.email = email?.text.toString()
      user.password = BCrypt.hashpw(password?.text.toString(), BCrypt.gensalt())
      user.joined = getDate()
      if (password?.text.toString().equals(verifyPassword?.text.toString())){
        if(user.name.isNotEmpty() and user.email.isNotEmpty() and user.password.isNotEmpty()){
          users.create(user.copy())
          val loggedUser = users.getUser(email?.text.toString())
          userLogged = true
          loggeduser = loggedUser
          info("BOOP $loggeduser")
          startActivityForResult(intentFor<ProfileActivity>().putExtra("logged_in", user), 0)
        }else{
          toast("Please fill out All fields")
        }
      }else{
        toast("Don't trip dawg, your passwords need to match")
      }
    }
    return view
  }
}