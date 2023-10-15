package com.example.testtackunisafe.domain.model.loadingReadyList

import java.io.Serializable

data class ProductListData(
    val item_list: List<Item>,
    val success: Boolean
):Serializable

data class Item(
    val created: String,
    val id: Int,
    val is_crossed: Boolean,
    val name: String
):Serializable