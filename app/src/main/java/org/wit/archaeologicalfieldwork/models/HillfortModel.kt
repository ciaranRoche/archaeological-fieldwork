package org.wit.archaeologicalfieldwork.models

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class HillfortModel(var id: Long = 0, var name: String = "", var description: String = "", var images: List<String>  = emptyList(), var location: Location = Location(52.245696, -7.139102, 15f), var comments: List<CommentsModel> = emptyList() ) : Parcelable