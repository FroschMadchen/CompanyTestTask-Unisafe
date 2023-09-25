package com.example.testtackunisafe.`interface`

import com.example.testtackunisafe.adapter.Product
import com.example.testtackunisafe.recevied_data.ReceivedData
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MainApi {
    @GET("/CreateTestKey") // may be POST
    suspend fun createTestKey(): Response<ReceivedData>
//    https://cyberprot.ru/shopping/v1/CreateTestKey?
//    {"key" = "#XXXXXX#"} String
    @GET("/Authentication")
    suspend fun getProductById(@Query("key")keyValue:String):ReceivedData
//    https://cyberprot.ru/shopping/v1/Authentication?key=92EGHS
//    {"success":true} Boolean
    @POST("/CreateShoppingList")
    suspend fun createShoppingList(@Query("key") key: String,
                                   @Query("name") name: String
    ): Response<ReceivedData>
    //https://cyberprot.ru/shopping/v1/CreateShoppingList?key=92EGHS&name=Shopping%20with%20bestie
    @DELETE("/RemoveShoppingList?list_{id}")
    suspend fun removeShoppingList(@Path("id")id:Int):ReceivedData

    @POST("/AddToShoppingList?id={id}&value=tools&n=3")
    suspend fun addToShoppingList(@Path("id")id:String)

    @GET("/GetShoppingList")
    suspend fun getSoppingList(@Query("key")key:String):Response<Product>
//    https://cyberprot.ru/shopping/v1/GetAllMyShopLists?key=92EGHS
//    {"shop_list":[{"created":"2023-08-28 06:35:44","name":"Test1","id":2},...,"success":true}
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
