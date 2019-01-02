package org.wit.archaeologicalfieldwork.models.data

import android.os.Parcelable
import androidx.room.Embedded
import kotlinx.android.parcel.Parcelize
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.wit.archaeologicalfieldwork.models.location.Location

@Parcelize
@Entity
data class DataModel(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var fbId: String = "",
    var title: String = "",
    var description: String = "",
    var images: List<String> = emptyList(),
    @Embedded var location: Location = Location()
) : Parcelable
