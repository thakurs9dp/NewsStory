package com.s9dp.newsstory.data.protodatastore

data class User(
    val isLogged : Boolean = false,
    val fullName : String = "",
    val email : String = ""
)
