package org.wit.archaeologicalfieldwork.views.startup

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.view_start_up.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.startActivityForResult
import org.wit.archaeologicalfieldwork.R
import org.wit.archaeologicalfieldwork.views.user.profile.ProfileActivity

var userLogged = false

class StartUpView : AppCompatActivity(), AnkoLogger {

    lateinit var presenter: StartUpPresenter

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        if (userLogged) {
            startActivityForResult<ProfileActivity>(0)
        } else {
            presenter = StartUpPresenter(this)
            setContentView(R.layout.view_start_up)
            presenter.loadLogin(supportFragmentManager)

            signupBtn.setOnClickListener {
                presenter.loadFragment(signupBtn, supportFragmentManager)
            }
        }
    }
}