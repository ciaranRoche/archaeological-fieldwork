package org.wit.archaeologicalfieldwork.views.startup.startupfragments

import android.os.Bundle
import com.google.android.material.textfield.TextInputEditText
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import org.wit.archaeologicalfieldwork.R
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.toast
import org.mindrot.jbcrypt.BCrypt
import org.wit.archaeologicalfieldwork.helpers.getHash
import org.wit.archaeologicalfieldwork.helpers.hashPassword
import org.wit.archaeologicalfieldwork.views.home.HomeView
import org.wit.archaeologicalfieldwork.views.user.profile.loggeduser
import org.wit.archaeologicalfieldwork.views.startup.userLogged
import org.wit.archaeologicalfieldwork.models.user.UserJSONStore
import org.wit.archaeologicalfieldwork.models.user.UserStore

class LogInFragment : Fragment(), AnkoLogger{

    lateinit var users: UserStore
    lateinit var progressBar: ProgressBar
    lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        users = UserJSONStore(this.context!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        presenter = LoginPresenter(this)

        val view = inflater.inflate(R.layout.fragment_login, container, false)
        val email: TextInputEditText? = view.findViewById(R.id.fragment_userEmailLogin)
        val password: TextInputEditText? = view.findViewById(R.id.fragment_userPasswordLogin)
        val login: Button? = view.findViewById(R.id.fragment_login)
        progressBar = view.findViewById(R.id.progressBar)

        presenter.hideProgress(progressBar)

        login?.setOnClickListener {
            presenter.showProgress(progressBar)
            val checkUser = users.checkUser(email?.text.toString().trim().toLowerCase())
            if (checkUser) {
                presenter.doLogin(email?.text.toString().trim().toLowerCase(), password?.text.toString().trim())
                val getUser = users.getUser(email?.text.toString().trim().toLowerCase())
                if (BCrypt.checkpw(password?.text.toString().trim(), getUser.password)) {
                    //userLogged = true
                    //loggeduser = getUser
                    //startActivityForResult(intentFor<HomeView>().putExtra("user", loggeduser), 0)
                    //val user = FirebaseAuth.getInstance().currentUser
                }
            } else {

            }
        }

        return view
    }

}
