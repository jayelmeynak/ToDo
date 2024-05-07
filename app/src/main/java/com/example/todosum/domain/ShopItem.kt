package com.example.todosum.domain

data class ShopItem(
    var id: Int = -1,
    val name: String,
    val count: Int,
    val enabled: Boolean
)
