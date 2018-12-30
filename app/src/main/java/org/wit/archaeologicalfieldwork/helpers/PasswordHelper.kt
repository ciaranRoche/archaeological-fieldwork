package org.wit.archaeologicalfieldwork.helpers

import org.mindrot.jbcrypt.BCrypt

fun hashPassword(pass: String?, verify: String?): String {
    if (pass?.trim().equals(verify?.trim())) {
        return BCrypt.hashpw(pass, BCrypt.gensalt())
    }
    return ""
}

fun getHash(pass:String?): String {
    return BCrypt.hashpw(pass, BCrypt.gensalt())
}

fun checkPassword() {
}