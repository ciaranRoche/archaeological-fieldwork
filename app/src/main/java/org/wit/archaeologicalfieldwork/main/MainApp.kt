package org.wit.archaeologicalfieldwork.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.wit.archaeologicalfieldwork.models.data.DataFireStore
import org.wit.archaeologicalfieldwork.models.data.DataStore
import org.wit.archaeologicalfieldwork.models.user.UserFireStore
import org.wit.archaeologicalfieldwork.models.user.UserStore

class MainApp : Application(), AnkoLogger {

    lateinit var hillforts: DataStore
    lateinit var users: UserStore

    override fun onCreate() {
        super.onCreate()
        hillforts = DataFireStore(applicationContext)
        users = UserFireStore(applicationContext)
    }
}