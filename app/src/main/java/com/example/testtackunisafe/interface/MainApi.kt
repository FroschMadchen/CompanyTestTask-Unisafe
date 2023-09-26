package com.example.testtackunisafe.`interface`

import com.example.testtackunisafe.adapter.ItemProduct
import com.example.testtackunisafe.adapter.Product
import com.example.testtackunisafe.adapter.Shop
import com.example.testtackunisafe.recevied_data.ReceivedData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MainApi {
    @POST("CreateTestKey?")
    @Headers("Accept:text/plane")
    suspend fun createTestKey(): Response<String>
//    https://cyberprot.ru/shopping/v1/CreateTestKey?
//    {"key" = "#XXXXXX#"} String **********
    @GET("Authentication")
    suspend fun authentication(@Query("key")keyValue:String):ReceivedData
//    https://cyberprot.ru/shopping/v1/Authentication?key=92EGHS
//    {"success":true} Boolean **********


    @POST("createShoppingList") // создать список покупок
    @Headers("Accept:text/plane")
    suspend fun createShoppingList(@Body itemProduct: ItemProduct
    ): Response<ReceivedData>
    //https://cyberprot.ru/shopping/v1/CreateShoppingList?key=92EGHS&name=Shopping%20with%20bestie
//    {success":true,"list_id":4}. list_id - id списка, созданный на сервере, по этому id
//     можно взаимодествовать со списком.

    @DELETE("RemoveShoppingList?list_{id}")  // удалить список покупок
    suspend fun removeShoppingList(@Path("id")id:Int):ReceivedData

    @POST("AddToShoppingList?id={id}&value=tools&n=3") // добавить товра в список покупок
    suspend fun addToShoppingList(@Path("id")id:String)
//     https://cyberprot.ru/shopping/v1/AddToShoppingList?id=4&value=tools&n=3
//     Ответ сервера: {"success":true,"item_id":8}, где item_id - id предмета внутри списка покупок.
//
    @DELETE("RemoveShoppingList")
    suspend fun crossItOff(@Query ("list_id")list_id:Int):Response<ReceivedData>
//    https://cyberprot.ru/shopping/v1/RemoveShoppingList?list_id=2
//    Ответ сервера: {"success":true,"new_value":false}

    @GET("GetAllMyShopLists")
    suspend fun getAllMyShopLists(@Query("key") key: String):Response<Product>
//    https://cyberprot.ru/shopping/v1/GetAllMyShopLists?key=92EGHS
//    {"shop_list":[{"created":"2023-08-28 06:35:44","name":"Test1","id":2},...,"success":true}

    @GET("GetShoppingList")
    suspend fun getSoppingList(@Query("key")key:String):Response<Product>
//    https://cyberprot.ru/shopping/v1/GetShoppingList?list_id=4
//    {"success":true,"item_list":[{"created":"2","name":"steel armor","id":3},
//  {"created":"1","name":"huge hammer","id":4},{"created":"1","name":"sharp spear","id":5},
//  {"created":"17","name":"bear traps","id":6},{"created":"40","name":"barrels of
//  explosives","id":7},{"created":"5","name":"thieves tools","id":8}]}

}


/*
interface MainApi {
    @POST("/CreateTestKey")
    suspend fun createTestKey(): Response<ReceivedData>

    @GET("/Authentication")
    suspend fun getProductById(@Query("key") keyValue: String): ReceivedData

    @POST("/CreateShoppingList")
    suspend fun createShoppingList(
        @Query("key") key: String,
        @Query("name") name: String
    ): MainData
}*/
