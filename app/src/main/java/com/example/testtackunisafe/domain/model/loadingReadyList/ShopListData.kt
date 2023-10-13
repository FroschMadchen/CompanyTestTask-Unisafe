package com.example.testtackunisafe.domain.model.loadingReadyList

import java.io.Serializable

data class ShopListData(
    val shop_list: List<Shop>,
    val success: Boolean
) :Serializable

data class Shop(
    val created: String,
    val id: Int,
    val name: String
)