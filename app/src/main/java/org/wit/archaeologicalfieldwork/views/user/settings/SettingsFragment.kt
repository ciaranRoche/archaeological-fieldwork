package org.wit.archaeologicalfieldwork.views.user.settings

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import com.google.android.material.textfield.TextInputEditText
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.squareup.picasso.Picasso
import org.jetbrains.anko.AnkoLogger
import org.wit.archaeologicalfieldwork.R
import org.wit.archaeologicalfieldwork.models.user.UserModel

class SettingsFragment : Fragment(), AnkoLogger {

    lateinit var presenter: SettingsPresenter
    lateinit var image: ImageView
    var user = UserModel()
    var IMAGE_REQUEST = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        presenter = SettingsPresenter(this)
        presenter.checkUser()

        user = arguments!!.getParcelable("user") as UserModel

        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        val name: TextInputEditText? = view.findViewById(R.id.settingsUserName)
        val email: TextInputEditText? = view.findViewById(R.id.settingsUserEmail)
        image = view.findViewById(R.id.userImage)
        val imgBtn: Button? = view.findViewById(R.id.profileImage)
        val updateBtn: Button? = view.findViewById(R.id.updateUser)
        val deleteBtn: Button? = view.findViewById(R.id.deleteUser)
        val passwordBtn: Button? = view.findViewById(R.id.updatePassword)

        name?.setText(user.name)
        email?.setText(user.email)
        if (user.userImage.isNotEmpty()) {
            imgBtn?.setText(R.string.button_updateProfileImage)
            Picasso.get().load(user.userImage)
                .config(Bitmap.Config.RGB_565)
                .resize(500, 500)
                .centerCrop()
                .into(image)
        }

        updateBtn?.setOnClickListener {
            user.name = name?.text.toString()
            user.email = email?.text.toString()
            presenter.updateUser(user)
            presenter.redirectProfile(user, this.fragmentManager!!)
        }

        deleteBtn?.setOnClickListener {
            presenter.confirmDelete(user)
        }

        imgBtn?.setOnClickListener {
            presenter.doImagePicker(this, IMAGE_REQUEST)
        }

        passwordBtn?.setOnClickListener {
            presenter.doPasswordUpdate(user)
        }

        return view
    }

    companion object {
        fun newInstance(user: UserModel): SettingsFragment {
            val args = Bundle()
            args.putParcelable("user", user)
            val fragment = SettingsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IMAGE_REQUEST -> {
                if (data != null) {
                    user.userImage = data.getData().toString()
                    Picasso.get().load(user.userImage)
                        .config(Bitmap.Config.RGB_565)
                        .resize(500, 500)
                        .centerCrop()
                        .into(image)
                }
            }
        }
    }
}
