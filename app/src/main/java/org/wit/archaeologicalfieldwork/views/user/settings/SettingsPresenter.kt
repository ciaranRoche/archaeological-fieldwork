package org.wit.archaeologicalfieldwork.views.user.settings

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.appcompat.app.AlertDialog
import android.text.InputType
import android.widget.EditText
import android.widget.LinearLayout
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import org.wit.archaeologicalfieldwork.R
import org.wit.archaeologicalfieldwork.helpers.showImagePicker
import org.wit.archaeologicalfieldwork.models.user.UserFireStore
import org.wit.archaeologicalfieldwork.models.user.UserModel
import org.wit.archaeologicalfieldwork.views.startup.StartUpView
import org.wit.archaeologicalfieldwork.views.startup.userLogged
import org.wit.archaeologicalfieldwork.views.user.profile.ProfileFragment

class SettingsPresenter(val view: SettingsFragment) {

    var users: UserFireStore = UserFireStore(view.context!!)

    fun doImagePicker(parent: SettingsFragment, req: Int) {
        showImagePicker(parent, req)
    }

    fun updateUser(user: UserModel) {
        users.update(user.copy())
    }

    fun checkUser() {
        if (!userLogged) view.startActivity<StartUpView>()
    }

    fun deleteUser(user: UserModel) {
        users.delete(user)
        userLogged = false
        view.startActivity<StartUpView>()
        view.toast("User Deleted")
    }

    fun redirectProfile(user: UserModel, support: FragmentManager) {
        val profile = ProfileFragment.newInstance(user)
        openFragment(profile, support)
    }

    fun openFragment(fragment: Fragment, support: FragmentManager) {
        val transaction = support.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun doPasswordUpdate() {
        val alert = AlertDialog.Builder(view.context!!)
        var layout: LinearLayout? = null
        var updatePass: EditText?
        var verifyPass: EditText?

        with(alert) {
            setTitle("Update Password")

            updatePass = EditText(context)
            updatePass!!.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
            updatePass?.hint = "Password"

            verifyPass = EditText(context)
            verifyPass!!.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
            verifyPass?.hint = "Verify Password"

            layout = LinearLayout(context)
            layout?.orientation = LinearLayout.VERTICAL
            layout?.addView(updatePass)
            layout?.addView(verifyPass)

            setPositiveButton("Update") { dialog, _ ->
                dialog.dismiss()
                val pass = updatePass?.text.toString().trim()
                val verify = verifyPass?.text.toString().trim()
                if (pass.equals(verify)) {
                    val auth: FirebaseAuth = FirebaseAuth.getInstance()
                    val authUser = auth.currentUser
                    authUser!!.updatePassword(pass).addOnCompleteListener(view.activity!!) {
                        task -> if (!task.isSuccessful) {
                        view.toast("Password Update Failed : ${task.exception?.message}")
                    } else {
                        view.toast("Password Updated")
                    } }
                } else {
                    view.toast("Looks like something went wrong")
                }
            }

            setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
        }
        val dialog = alert.create()
        dialog.setView(layout)
        dialog.show()
    }

    fun confirmDelete(user: UserModel) {
        val alert = AlertDialog.Builder(view.context!!)

        with(alert) {
            setTitle("Confirm Account Deletion")

            setMessage("Are you sure you want to delete your account?")

            setPositiveButton("Delete") {
                dialog, _ ->
                dialog.dismiss()
                deleteUser(user)
            }

            setNegativeButton("Cancel") {
                dialog, _ ->
                dialog.dismiss()
            }
        }

        val dialog = alert.create()
        dialog.show()
    }
}
