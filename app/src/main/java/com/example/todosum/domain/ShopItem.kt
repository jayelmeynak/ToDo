package com.example.todosum.domain

data class ShopItem(
    var id: Int = UNDEFINED_ID,
    val name: String,
    val count: Int,
    val enabled: Boolean
){

    companion object {

        const val UNDEFINED_ID = 0
    }
}
