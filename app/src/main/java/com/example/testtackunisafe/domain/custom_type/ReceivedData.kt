package com.example.testtackunisafe.domain.custom_type

data class ReceivedData(
    val key:String,
    val success: Boolean,
    val list_id: Int, //    list_id - id списка, созданный на сервере, по этому id можно взаимодествовать со списком.
    val name: String,
    val shop_list:List<Product>
)






