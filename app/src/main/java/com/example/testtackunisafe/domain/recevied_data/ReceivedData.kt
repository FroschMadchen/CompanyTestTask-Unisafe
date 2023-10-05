package com.example.testtackunisafe.domain.recevied_data

import android.widget.ImageView

data class ReceivedData(
    val key:String,
    val success: Boolean,
    val list_id: Int, //    list_id - id списка, созданный на сервере, по этому id можно взаимодествовать со списком.
    val name: String,
    val shop_list:List<Product>
)
data class Product(
    val created: String,
    val id: Int,
    val name: String
)






