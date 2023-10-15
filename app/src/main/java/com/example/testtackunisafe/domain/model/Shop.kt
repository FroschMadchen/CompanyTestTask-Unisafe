package com.example.testtackunisafe.domain.model

import java.io.Serializable


data class Shop1(
    val listId:Int, //id
    val name: String, //name
    val list:ArrayList<Product> // список который хранится в элементе

    ) : Serializable
