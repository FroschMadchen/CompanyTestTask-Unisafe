package com.example.testtackunisafe.`interface`

import com.example.testtackunisafe.recevied_data.ReceivedData
import com.example.testtackunisafe.recevied_data.ReceivedDataListId
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MainApi {
    @POST("/CreateTestKey")
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
    suspend fun removeShoppingList(@Path("id")id:Int):ReceivedDataListId

    @POST("/AddToShoppingList?id={id}&value=tools&n=3")
    suspend fun addToShoppingList(@Path("id")id:String)
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
