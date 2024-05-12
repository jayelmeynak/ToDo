package com.example.todosum.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface ShopListDao {

    @Query("SELECT * FROM shop_items")
    fun getShopList(): LiveData<List<ShopItemDbModel>>

    @Insert
    fun addShopItem(shopItemDbModel: ShopItemDbModel)

    @Delete
    fun deleteShopItem(shopItemDbModel: ShopItemDbModel)

    @Query("SELECT * FROM shop_items WHERE id=:shopItemId LIMIT 1")
    fun getShopList(shopItemId: Int): ShopItemDbModel
}