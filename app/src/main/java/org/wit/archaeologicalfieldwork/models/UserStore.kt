package org.wit.archaeologicalfieldwork.models

interface UserStore{
  fun create(user: UserModel)
  fun update(user: UserModel)
  fun delete(user: UserModel)
}