package org.wit.archaeologicalfieldwork.models.data

interface DataStore {
    suspend fun findAll(): List<DataModel>
    suspend fun findById(id: Long): DataModel?
    suspend fun create(data: DataModel)
    suspend fun update(data: DataModel)
    suspend fun delete(data: DataModel)
    fun clear()
}