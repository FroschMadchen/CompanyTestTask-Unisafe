package com.example.testtackunisafe.adapter

data class Product(
    val shop_list: List<Shop>,
    val success: Boolean
)

data class Shop(
    val created: String,
    val id: Int,
    val name: String
)

data class ItemProduct(
    val key:String,
    val name:String
)