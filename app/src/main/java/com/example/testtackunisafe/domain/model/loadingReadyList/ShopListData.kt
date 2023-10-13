package com.example.testtackunisafe.domain.model.loadingReadyList

data class ShopListData(
    val shop_list: List<Shop>,
    val success: Boolean
)

data class Shop(
    val created: String,
    val id: Int,
    val name: String
)