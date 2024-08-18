package com.vince.tamingtemper.data.entities

data class File(
    val url : String,
    val details: Detail,
    val fileName: String,
    val contentType: String
) {
}