package org.wit.archaeologicalfieldwork.helpers

import java.text.SimpleDateFormat
import java.util.*

fun getDate() : String {
  val sdf = SimpleDateFormat("dd/M/yyyy")
  return sdf.format(Date())
}