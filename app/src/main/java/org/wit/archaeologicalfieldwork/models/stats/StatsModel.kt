package org.wit.archaeologicalfieldwork.models.stats

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParecelCreator")
@Parcelize
data class StatsModel(var id: Long = 0, var hillfort: Long = 0, var date: String = "") : Parcelable