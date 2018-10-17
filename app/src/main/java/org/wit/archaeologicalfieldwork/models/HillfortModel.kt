package org.wit.archaeologicalfieldwork.models

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class HillfortModel(var id: Long = 0, var name: String = "", var description: String = "", var image: String = "", var location: Location ) : Parcelable