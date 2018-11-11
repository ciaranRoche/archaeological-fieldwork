package org.wit.archaeologicalfieldwork.views.user.profile

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import org.jetbrains.anko.AnkoLogger
import org.wit.archaeologicalfieldwork.R
import org.wit.archaeologicalfieldwork.models.user.UserModel

var loggeduser = UserModel()
lateinit var user: UserModel

class ProfileFragment : Fragment(), AnkoLogger {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        val name: TextView? = view.findViewById(R.id.profile_name)
        val email: TextView? = view.findViewById(R.id.profile_email)
        val date: TextView? = view.findViewById(R.id.profile_member_date)
        val visit: TextView? = view.findViewById(R.id.profile_no_visit)
        val image: ImageView? = view.findViewById(R.id.profile_image)

        user = arguments!!.getParcelable("user") as UserModel
        name?.text = user.name
        email?.text = user.email
        date?.text = user.joined
        visit?.text = user.stats.size.toString()
        if (user.userImage.isNotEmpty()) {
            Picasso.get().load(user.userImage)
                .config(Bitmap.Config.RGB_565)
                .resize(500, 500)
                .centerCrop()
                .into(image)
        }
        return view
    }

    companion object {
        fun newInstance(user: UserModel): ProfileFragment {
            val args = Bundle()
            args.putParcelable("user", user)
            val fragment = ProfileFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
