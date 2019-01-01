package org.wit.archaeologicalfieldwork.models.user

import android.annotation.SuppressLint
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import org.wit.archaeologicalfieldwork.models.stats.StatsModel

@SuppressLint("ParecelCreator")
@Parcelize
@Entity
data class UserModel(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var fbid: String = "",
    var name: String = "",
    var email: String = "",
    var stats: MutableList<StatsModel> = mutableListOf(),
    var userImage: String = "",
    var joined: String = ""
) : Parcelable