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
import org.wit.archaeologicalfieldwork.models.user.UserJSONStore
import org.wit.archaeologicalfieldwork.models.user.UserStore

class LogInFragment : Fragment(), AnkoLogger {

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
        val email: TextInputEditText? = view.findViewById<TextInputEditText>(R.id.fragment_userEmailLogin)
        val password: TextInputEditText? = view.findViewById(R.id.fragment_userPasswordLogin)
        val login: Button? = view.findViewById(R.id.fragment_login)
        progressBar = view.findViewById(R.id.progressBar)

        hideProgress()

        login?.setOnClickListener {
            presenter.doLogin(email?.text.toString().trim().toLowerCase(), password?.text.toString().trim())
        }

        return view
    }

    fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    fun hideProgress() {
        progressBar.visibility = View.GONE
    }
}
