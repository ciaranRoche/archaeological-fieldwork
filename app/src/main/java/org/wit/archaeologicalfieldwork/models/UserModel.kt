package org.wit.archaeologicalfieldwork.models

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParecelCreator")
@Parcelize
data class UserModel(var id: Long = 0,var name: String = "", var email: String = "", var password: String = "", var hillforts: List<Long> = emptyList(), var userImage: String = "") : Parcelable