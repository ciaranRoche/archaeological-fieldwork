package org.wit.archaeologicalfieldwork.models.data

interface DataStore {
    suspend fun findAll(): ArrayList<DataModel>
    suspend fun findById(id: Long): DataModel?
    suspend fun create(data: DataModel)
    suspend fun update(data: DataModel)
    suspend fun delete(data: DataModel)
    suspend fun findFavorites(): ArrayList<DataModel>
    fun clear()
}