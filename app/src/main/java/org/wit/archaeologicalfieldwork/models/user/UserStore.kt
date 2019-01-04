package org.wit.archaeologicalfieldwork.models.user

interface UserStore {
    suspend fun create(user: UserModel)
    suspend fun update(user: UserModel)
    suspend fun delete(user: UserModel)
    suspend fun getUser(email: String): UserModel
    suspend fun checkUser(email: String): Boolean
    suspend fun findUserId(id: Long): UserModel
    suspend fun getStats(user: UserModel): Long
    suspend fun addStats(user: UserModel)
    fun updateImage(user: UserModel)
}