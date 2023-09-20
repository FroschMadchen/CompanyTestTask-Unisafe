package com.example.testtackunisafe.recevied_data

data class ReceivedDataListId(
    val list_id: Int, //list_id - id списка, созданный на сервере, по этому id
   // можно взаимодествовать со списком.
    val success: Boolean,
    val new_value: Boolean
)