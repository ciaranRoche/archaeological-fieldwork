package org.wit.archaeologicalfieldwork.models.hillfort

import org.wit.archaeologicalfieldwork.models.hillfort.HillfortModel

interface HillfortStore{
  fun findAll(): List<HillfortModel>
  fun create(hillfort: HillfortModel)
  fun update(hillfort: HillfortModel)
  fun delete(hillfort: HillfortModel)
}