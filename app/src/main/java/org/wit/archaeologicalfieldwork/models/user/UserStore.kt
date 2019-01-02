package org.wit.archaeologicalfieldwork.models.user

import org.wit.archaeologicalfieldwork.models.stats.StatsModel

interface UserStore {
    suspend fun create(user: UserModel)
    suspend fun update(user: UserModel)
    suspend fun delete(user: UserModel)
    suspend fun getUser(email: String): UserModel
    suspend fun checkUser(email: String): Boolean
    suspend fun findUserId(id: Long): UserModel
    suspend fun getStats(user: UserModel): MutableList<StatsModel>?
    fun updateImage(user: UserModel)
}