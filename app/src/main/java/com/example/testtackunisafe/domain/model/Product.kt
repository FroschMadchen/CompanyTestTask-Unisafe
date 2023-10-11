package com.example.testtackunisafe.domain.model

class Product(
    val created: String,
    val name:String,
    val item_id:Int,
    val quantity:Int,
    val list_id:Int
) {
    constructor( name:String, quantity: Int,list_id:Int)
            : this("", name, 0, quantity,list_id)
    constructor( name:String, quantity: Int,list_id:Int,item_id: Int)
            : this("", name, item_id, quantity,list_id)
}

