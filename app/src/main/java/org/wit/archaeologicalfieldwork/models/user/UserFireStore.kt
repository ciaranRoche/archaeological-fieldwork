package org.wit.archaeologicalfieldwork.models.user

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
import org.wit.archaeologicalfieldwork.models.stats.StatsModel
import java.io.ByteArrayOutputStream
import java.io.File

class UserFireStore(val context: Context) : UserStore, AnkoLogger {

    var users = mutableListOf<UserModel>()
    lateinit var userId: String
    lateinit var db: DatabaseReference
    lateinit var st: StorageReference

    override suspend fun create(user: UserModel) {
        fetchUserProfile { }
        val key = db.child("users").child(userId).child("profile").push().key
        user.fbid = key!!
        users.add(user)
        db.child("users").child(userId).child("profile").child(key).setValue(user)
        updateImage(user)
    }

    override suspend fun update(user: UserModel) {
        fetchUserProfile { }
        var foundUser: UserModel? = users.find { u -> u.fbid == user.fbid }
        if (foundUser != null) {
            foundUser.name = user.name
            foundUser.email = user.email
            foundUser.stats = user.stats
            foundUser.userImage = user.userImage
            foundUser.joined = user.joined
        }
        db.child("users").child(userId).child("profile").child(user.fbid).setValue(user)
        if ((user.userImage.length) > 0 && (user.userImage[0] != 'h')) {
            updateImage(user)
        }
    }

    override suspend fun delete(user: UserModel) {
        fetchUserProfile { }
        db.child("users").child(userId).child("profile").child(user.fbid).removeValue()
        users.remove(user)
    }

    override suspend fun getUser(email: String): UserModel {
        val foundUser: UserModel? = users.find { u -> u.email == email }
        return foundUser!!
    }

    override suspend fun checkUser(email: String): Boolean {
        val foundUser: UserModel? = users.find { u -> u.email == email }
        if (foundUser != null) return true
        return false
    }

    override suspend fun findUserId(id: Long): UserModel {
        val foundUser: UserModel? = users.find { u -> u.id == id }
        return foundUser!!
    }

    override suspend fun getStats(user: UserModel): MutableList<StatsModel>? {
        val foundUser = findUserId(user.id)
        return foundUser.stats
    }

    override fun updateImage(user: UserModel) {
        fetchUserProfile {}
        if (user.userImage != "") {
            val fileName = File(user.userImage)
            val imageName = fileName.name

            var imageRef = st.child(userId + '/' + imageName)
            val baos = ByteArrayOutputStream()
            val bitmap = readImageFromPath(context, user.userImage)

            bitmap?.let {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val data = baos.toByteArray()
                val uploadTask = imageRef.putBytes(data)
                uploadTask.addOnFailureListener {
                    println(it.message)
                }.addOnSuccessListener { taskSnapshot ->
                    taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener {
                        user.userImage = it.toString()
                        db.child("users").child(userId).child("profile").child(user.fbid).setValue(user)
                    }
                }
            }
        }
    }

    fun fetchUserProfile(usersReady: () -> Unit) {
        val valueEventListener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.mapNotNullTo(users) { it.getValue<UserModel>(UserModel::class.java) }
                usersReady()
            }
        }
        userId = FirebaseAuth.getInstance().currentUser!!.uid
        db = FirebaseDatabase.getInstance().reference
        st = FirebaseStorage.getInstance().reference
        users.clear()
        db.child("users").child(userId).child("profile").addListenerForSingleValueEvent(valueEventListener)
    }
}