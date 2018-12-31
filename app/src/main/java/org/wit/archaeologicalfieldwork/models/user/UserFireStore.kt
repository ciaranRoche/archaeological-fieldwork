package org.wit.archaeologicalfieldwork.models.user

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import org.jetbrains.anko.AnkoLogger
import org.wit.archaeologicalfieldwork.models.stats.StatsModel

class UserFireStore(val context: Context) : UserStore, AnkoLogger {

    val users = ArrayList<UserModel>()
    lateinit var userId: String
    lateinit var db: DatabaseReference

    override fun create(user: UserModel) {
        db = FirebaseDatabase.getInstance().reference
        val key = db.child("users").push().key
        user.fbid = key!!
        users.add(user)
        db.child("users").child(key).setValue(user)
    }

    override fun update(user: UserModel) {
        var foundUser: UserModel? = users.find { u -> u.id == user.id }
        if (foundUser != null) {
            foundUser.name = user.name
            foundUser.email = user.email
            foundUser.password = user.password
            foundUser.stats = user.stats
            foundUser.userImage = user.userImage
            foundUser.joined = user.joined
        }
        db.child("users").child(user.fbid).setValue(user)
    }

    override fun delete(user: UserModel) {
        db.child("users").child(user.fbid).removeValue()
        users.remove(user)
    }

    override fun getUser(email: String): UserModel {
        val foundUser: UserModel? = users.find { u -> u.email == email }
        return foundUser!!
    }

    override fun checkUser(email: String): Boolean {
        val foundUser: UserModel? = users.find { u -> u.email == email }
        if (foundUser != null) return true
        return false
    }

    override fun findUserId(id: Long): UserModel {
        val foundUser: UserModel? = users.find { u -> u.id == id }
        return foundUser!!
    }

    override fun getStats(user: UserModel): MutableList<StatsModel>? {
        val foundUser = findUserId(user.id)
        return foundUser.stats
    }

    fun fetchUsers(usersReady: () -> Unit) {
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
        users.clear()
        db.child("users").addListenerForSingleValueEvent(valueEventListener)
    }
}