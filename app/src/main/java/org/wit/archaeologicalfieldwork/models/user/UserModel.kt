package org.wit.archaeologicalfieldwork.models.user

import android.annotation.SuppressLint
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParecelCreator")
@Parcelize
@Entity
data class UserModel(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var fbid: String = "",
    var name: String = "",
    var email: String = "",
    var stat: Long = 0,
    var userImage: String = "",
    var joined: String = ""
) : Parcelable