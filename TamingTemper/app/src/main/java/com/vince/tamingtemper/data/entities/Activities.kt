package com.vince.tamingtemper.data.entities

data class Activities(
    val id : String,
    val challengeId : String,
    val type : String,
    val title : String,
    val titleB : String,
    val description : String,
    val descriptionB : String,
    val state : String,
    val icon : Icon,
    val lockedIcon : Icon,
) {
}