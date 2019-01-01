package org.wit.archaeologicalfieldwork.models.data

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import org.jetbrains.anko.AnkoLogger

class DataFireStore(val context: Context) : DataStore, AnkoLogger {

    val hillforts = ArrayList<DataModel>()
    lateinit var userId: String
    lateinit var db: DatabaseReference

    override suspend fun findAll(): List<DataModel> {
        return hillforts
    }

    override suspend fun findById(id: Long): DataModel? {
        val foundHillfort: DataModel? = hillforts.find { h -> h.id == id }
        return foundHillfort
    }

    override suspend fun create(data: DataModel) {
        fetchHillforts {}
        val key = db.child("users").child(userId).child("hillforts").push().key
        data.fbId = key!!
        hillforts.add(data)
        db.child("users").child(userId).child("hillforts").child(key).setValue(data)
    }

    override suspend fun update(data: DataModel) {
        fetchHillforts {}
        val foundHillfort: DataModel? = hillforts.find { h -> h.fbId == data.fbId }
        if (foundHillfort != null) {
            foundHillfort.title = data.title
            foundHillfort.description = data.description
            foundHillfort.images = data.images
            foundHillfort.location = data.location
        }
        db.child("users").child(userId).child("hillforts").child(data.fbId).setValue(data)
    }

    override suspend fun delete(data: DataModel) {
        fetchHillforts {}
        db.child("users").child(userId).child("hillforts").child(data.fbId).removeValue()
        hillforts.remove(data)
    }

    override fun clear() {
        hillforts.clear()
    }

    fun fetchHillforts(hillfortsReady: () -> Unit) {
        val valueEventListener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.mapNotNullTo(hillforts) { it.getValue<DataModel>(DataModel::class.java) }
                hillfortsReady()
            }
        }
        userId = FirebaseAuth.getInstance().currentUser!!.uid
        db = FirebaseDatabase.getInstance().reference
        hillforts.clear()
        db.child("users").child(userId).child("hillforts").addListenerForSingleValueEvent(valueEventListener)
    }
}