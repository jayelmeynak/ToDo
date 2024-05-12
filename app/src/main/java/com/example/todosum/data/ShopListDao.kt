package com.example.todosum.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ShopListDao {


    @Query("SELECT * FROM shop_items")
    fun getShopItemList(): LiveData<List<ShopItemDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addShopItem(shopItemDbModel: ShopItemDbModel)

    @Delete
    fun deleteShopItem(shopItemDbModel: ShopItemDbModel)

    @Query("SELECT * FROM shop_items WHERE id=:shopItemId")
    fun getShopItem(shopItemId: Int): ShopItemDbModel
}