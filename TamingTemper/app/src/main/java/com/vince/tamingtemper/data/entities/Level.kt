package com.vince.tamingtemper.data.entities

data class Level(
    val level : String,
    val title : String,
    val description : String,
    val state : String,
    val activities : List<Activities>
) {
}