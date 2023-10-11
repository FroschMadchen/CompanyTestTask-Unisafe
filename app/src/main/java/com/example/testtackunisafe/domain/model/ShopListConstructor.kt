package com.example.testtackunisafe.domain.model

import java.io.Serializable


data class ShopListConstructor(
    val listId:Int,
    val name: String,
    val list:ArrayList<Product>
    ) : Serializable
