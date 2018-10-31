package org.wit.archaeologicalfieldwork.models

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class CommentsModel(var id: Long = 0, var user: String = "", var comment: String = "", var date: String = "") : Parcelable