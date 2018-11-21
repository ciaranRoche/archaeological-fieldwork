package org.wit.archaeologicalfieldwork.models.hillfort

import org.wit.archaeologicalfieldwork.models.comment.CommentsModel

interface HillfortStore {
    fun findAll(): List<HillfortModel>
    fun create(hillfort: HillfortModel)
    fun update(hillfort: HillfortModel)
    fun delete(hillfort: HillfortModel)
    fun findAllComments(hillfort: HillfortModel): List<CommentsModel>
    fun findById(id: Long): HillfortModel
}