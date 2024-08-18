package com.vince.tamingtemper.data.entities



data class Challenge(
  val parentPosition: Int,
  val pairPosition: List<Int>?,
  val isItemNull: Boolean,
) {
}