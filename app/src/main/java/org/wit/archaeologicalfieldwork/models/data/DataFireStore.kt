package org.wit.archaeologicalfieldwork.models.data

import android.content.Context
import android.graphics.Bitmap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import org.jetbrains.anko.AnkoLogger
import org.wit.archaeologicalfieldwork.helpers.readImageFromPath
import java.io.ByteArrayOutputStream
import java.io.File

class DataFireStore(val context: Context) : DataStore, AnkoLogger {

    val hillforts = ArrayList<DataModel>()
    lateinit var userId: String
    lateinit var db: DatabaseReference
    lateinit var st: StorageReference

    override suspend fun findAll(): ArrayList<DataModel> {
        fetchHillforts {}
        return hillforts
    }

    override suspend fun findFavorites(): ArrayList<DataModel> {
        fetchHillforts {}
        val favorites = ArrayList<DataModel>()
        hillforts.forEach { if (it.rating > 4) favorites.add(it) }

        return favorites
    }

    override suspend fun findById(id: Long): DataModel? {
        fetchHillforts {}
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
            foundHillfort.rating = data.rating
        }
        db.child("users").child(userId).child("hillforts").child(data.fbId).setValue(data)
    }

    override suspend fun delete(data: DataModel) {
        fetchHillforts {}
        db.child("users").child(userId).child("hillforts").child(data.fbId).removeValue()
        hillforts.remove(data)
    }

    override suspend fun deleteByFbif(fbid: String) {
        fetchHillforts {}
        db.child("users").child(userId).child("hillforts").child(fbid).removeValue()
    }

    override fun clear() {
        hillforts.clear()
    }

    override fun updateImage(image: String, hillfort: DataModel) {
        fetchHillforts { }
        val fileName = File(image)
        val imageName = fileName.name

        var imageRef = st.child(userId + '/' + imageName)
        val baos = ByteArrayOutputStream()
        val bitmap = readImageFromPath(context, image)

        bitmap?.let {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()
            val uploadTask = imageRef.putBytes(data)
            uploadTask.addOnFailureListener {
                println(it.message)
            }.addOnSuccessListener { taskSnapshot ->
                taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener {
                    hillfort.images += it.toString()
                }
            }
        }
    }

    override fun updateBitMapImage(image: Bitmap, name: String, hillfort: DataModel) {
        fetchHillforts { }
        var imageRef = st.child(userId + '/' + name)
        val baos = ByteArrayOutputStream()
        val bitmap = image

        bitmap?.let {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()
            val uploadTask = imageRef.putBytes(data)
            uploadTask.addOnFailureListener {
                println(it.message)
            }.addOnSuccessListener { taskSnapshot ->
                taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener {
                    hillfort.images += it.toString()
                }
            }
        }
    }

    fun fetchHillforts(hillfortsReady: () -> Unit) {
        val valueEventListener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.iterator().forEach { hillforts.add(it.getValue<DataModel>(DataModel::class.java)!!) }
                hillfortsReady()
            }
        }
        userId = FirebaseAuth.getInstance().currentUser!!.uid
        db = FirebaseDatabase.getInstance().reference
        st = FirebaseStorage.getInstance().reference
        hillforts.clear()
        db.child("users").child(userId).child("hillforts").addListenerForSingleValueEvent(valueEventListener)
    }
}