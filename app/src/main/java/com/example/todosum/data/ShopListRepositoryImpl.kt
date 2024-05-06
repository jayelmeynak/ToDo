package com.example.todosum.data

import com.example.todosum.domain.ShopItem
import com.example.todosum.domain.ShopListRepository

object ShopListRepositoryImpl: ShopListRepository {
    private val shopList = mutableListOf<ShopItem>()
    private var autoIncrementId = 1
    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == -1) {
            shopItem.id = autoIncrementId++
        }
        shopList.add(shopItem)
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
    }

    override fun editShopItem(shopItem: ShopItem) {
        val olditem = getShopItem(shopItem.id)
        deleteShopItem(olditem)
        shopList.add(shopItem)
    }

    override fun getShopItem(shopItemId: Int): ShopItem {
        return shopList.find { it.id == shopItemId
        } ?: throw RuntimeException("Element with $shopItemId not found")
    }

    override fun getShopList(): List<ShopItem> {
        return shopList.toMutableList()
    }
}