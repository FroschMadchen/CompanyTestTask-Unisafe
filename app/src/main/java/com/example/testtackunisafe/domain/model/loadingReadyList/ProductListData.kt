package com.example.testtackunisafe.domain.model.loadingReadyList

data class ProductListData(
    val item_list: List<Item>,
    val success: Boolean
)

data class Item(
    val created: String,
    val id: Int,
    val is_crossed: Boolean,
    val name: String
)