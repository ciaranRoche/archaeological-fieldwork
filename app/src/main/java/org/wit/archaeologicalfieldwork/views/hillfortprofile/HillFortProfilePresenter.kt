package org.wit.archaeologicalfieldwork.views.hillfortprofile

import android.content.Intent
import org.jetbrains.anko.AnkoLogger
import org.wit.archaeologicalfieldwork.models.data.DataModel

class HillFortProfilePresenter(val view: HillFortProfileFragment) : AnkoLogger {

    fun doShare(hillfort: DataModel) {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        val shareBody = "Hey I found this awesome hillfort here, lat : ${hillfort.location.lat} , lng : ${hillfort.location.lng}"
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "I found this cool hillfort")
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody)
        view.startActivity(Intent.createChooser(sharingIntent, "Share via"))
    }
}