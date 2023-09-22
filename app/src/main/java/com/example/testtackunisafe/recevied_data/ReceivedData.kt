package com.example.testtackunisafe.recevied_data

data class ReceivedData(
    val key:String,
    val success: Boolean,
    val list_id: Int,
//    list_id - id списка, созданный на сервере, по этому id можно взаимодествовать со списком.
    val shop_list:List<Shop>
)
data class Shop(
    val created: String,
    val id: Int,
    val name: String
)


