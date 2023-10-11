package com.example.testtackunisafe.domain.model

data class ReceivedData(
    val key:String,
    val success: Boolean,
    val list_id: Int, //    list_id - id списка, созданный на сервере, по этому id можно взаимодествовать со списком.
    val name: String,
    val shop_list:List<Product>,
    val item_id:Int,
    val new_value:Boolean
)






