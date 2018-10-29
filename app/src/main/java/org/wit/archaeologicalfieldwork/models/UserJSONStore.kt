package org.wit.archaeologicalfieldwork.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import org.wit.archaeologicalfieldwork.helpers.exists
import org.wit.archaeologicalfieldwork.helpers.read
import org.wit.archaeologicalfieldwork.helpers.write

val USER_FILE = "users.json"
val gsonBuild = GsonBuilder().setPrettyPrinting().create()
val list = object : TypeToken<ArrayList<UserModel>>() {}.type

class UserJSONStore(val context: Context) : UserStore, AnkoLogger {

  var users = mutableListOf<UserModel>()

  init {
    if(exists(context, USER_FILE))
      deserialize()
  }

  override fun create(user: UserModel) {
    user.id = generateRandomId()
    users.add(user)
    serialize()
  }

  override fun update(user: UserModel) {
    var foundUser: UserModel? = users.find { u -> u.id == user.id }
    if (foundUser != null){
      foundUser.name = user.name
      foundUser.email = user.email
      foundUser.password = user.password
      foundUser.stats = user.stats
      foundUser.userImage = user.userImage
      serialize()
    }
  }

  override fun delete(user: UserModel) {
    users.remove(user)
    serialize()
  }

  override fun verifyUser(email: String, password: String): Boolean{
    val foundUser: UserModel? = users.find { u -> u.email == email }
    if (foundUser != null){
      if(foundUser.password.equals(password)) return true
    }
    return false
  }

  override fun findUserEmail(email: String): UserModel {
    val foundUser: UserModel? = users.find { u -> u.email == email }
    return foundUser!!
  }

  override fun findUserId(id: Long): UserModel {
    val foundUser: UserModel? = users.find { u -> u.id == id }
    return foundUser!!
  }

  override fun getStats(user: UserModel): MutableList<StatsModel> {
    val foundUser = findUserId(user.id)
    return foundUser.stats
  }

  private fun serialize() {
    val jsonString = gsonBuild.toJson(users, list)
    write(context, USER_FILE, jsonString)
  }

  private fun deserialize() {
    val jsonString = read(context, USER_FILE)
    users = Gson().fromJson(jsonString, list)
  }

}