package org.wit.archaeologicalfieldwork.models.user

import org.wit.archaeologicalfieldwork.models.stats.StatsModel

interface UserStore{
  fun create(user: UserModel)
  fun update(user: UserModel)
  fun delete(user: UserModel)
  fun getUser(email: String) : UserModel
  fun checkUser(email: String) : Boolean
  fun findUserId(id: Long) : UserModel
  fun getStats(user: UserModel): MutableList<StatsModel>?
}