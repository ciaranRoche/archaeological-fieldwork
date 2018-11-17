package org.wit.archaeologicalfieldwork.views.startup.startupfragments

import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import org.wit.archaeologicalfieldwork.R
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.toast
import org.mindrot.jbcrypt.BCrypt
import org.wit.archaeologicalfieldwork.views.home.HomeView
import org.wit.archaeologicalfieldwork.views.user.profile.loggeduser
import org.wit.archaeologicalfieldwork.views.startup.userLogged
import org.wit.archaeologicalfieldwork.models.user.UserJSONStore
import org.wit.archaeologicalfieldwork.models.user.UserStore

class LogInFragment : Fragment(), AnkoLogger {

    lateinit var users: UserStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        users = UserJSONStore(this.context!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        val email: TextInputEditText? = view.findViewById(R.id.fragment_userEmailLogin)
        val password: TextInputEditText? = view.findViewById(R.id.fragment_userPasswordLogin)
        val login: Button? = view.findViewById(R.id.fragment_login)

        login?.setOnClickListener {
            val checkUser = users.checkUser(email?.text.toString().trim().toLowerCase())
            if (checkUser) {
                val getUser = users.getUser(email?.text.toString().trim().toLowerCase())
                if (BCrypt.checkpw(password?.text.toString().trim(), getUser.password)) {
                    userLogged = true
                    loggeduser = getUser
                    startActivityForResult(intentFor<HomeView>().putExtra("user", loggeduser), 0)
                }
            } else {
                toast("Incorrect Details")
            }
        }

        return view
    }
}
