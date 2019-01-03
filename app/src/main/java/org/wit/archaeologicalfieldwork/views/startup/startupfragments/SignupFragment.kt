package org.wit.archaeologicalfieldwork.views.startup.startupfragments

import android.os.Bundle
import com.google.android.material.textfield.TextInputEditText
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.toast
import org.wit.archaeologicalfieldwork.R
import org.wit.archaeologicalfieldwork.helpers.getDate
import org.wit.archaeologicalfieldwork.models.user.UserModel
import org.wit.archaeologicalfieldwork.views.home.HomeView

class SignupFragment : Fragment(), AnkoLogger {

    var user = UserModel()
    lateinit var progressBar: ProgressBar
    lateinit var presenter: SignupPresenter
    lateinit var password: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        presenter = SignupPresenter(this)

        val view = inflater.inflate(R.layout.fragment_signup, container, false)
        val name: TextInputEditText? = view.findViewById(R.id.fragment_userName)
        val email: TextInputEditText? = view.findViewById(R.id.fragment_userEmail)
        val passwordText: TextInputEditText? = view.findViewById(R.id.fragment_userPassword)
        val submit: Button? = view.findViewById(R.id.fragment_saveUser)
        progressBar = view.findViewById(R.id.progressBar2)

        hideProgress()

        submit?.setOnClickListener {
            user.name = name?.text.toString()
            user.email = email?.text.toString().trim().toLowerCase()
            password = passwordText?.text.toString().trim()
            user.joined = getDate()
            if (password.isNotEmpty()) {
                if (user.name.isNotEmpty() and user.email.isNotEmpty() and password.isNotEmpty()) {
                    presenter.doSignUp(user.email, password, user.name)
                } else {
                    toast("Please fill out All fields")
                }
            } else {
                toast("Don't trip dawg, your passwords need to match")
            }
        }
        return view
    }

    fun login(user: UserModel) {
        startActivityForResult(intentFor<HomeView>().putExtra("user", user), 0)
    }

    fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    fun hideProgress() {
        progressBar.visibility = View.GONE
    }
}