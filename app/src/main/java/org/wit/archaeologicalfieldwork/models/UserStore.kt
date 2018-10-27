package org.wit.archaeologicalfieldwork.models

interface UserStore{
  fun create(user: UserModel)
  fun update(user: UserModel)
  fun delete(user: UserModel)
  fun findUserEmail(email: String) : UserModel
  fun findUserId(id: Long) : UserModel
  fun verifyUser(email: String, password: String) : Boolean
  fun getVisitedHillforts(user: UserModel): List<Long>
}