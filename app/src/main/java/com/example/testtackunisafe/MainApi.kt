package com.example.testtackunisafe

import com.example.testtackunisafe.recevied_data.ReceivedData
import com.example.testtackunisafe.recevied_data.ReceivedDataListId
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MainApi {
    @POST("/CreateTestKey?")
    suspend fun createTestKey(): ReceivedData

    @GET("/Authentication?key={keyValue}")
    suspend fun getProductById(@Query("keyValue")keyValue:String):Boolean

    @POST("/CreateShoppingList?key={keyValue}&name={Shopping}%20with%20bestie")
    suspend fun createShoppingList(@Path("keyValue")keyValue:String,
                                   @Path("Shopping")shopping:String, // не понимаю какое   имя
                                   @Path("TestKey")testKey:String, // не помнимаю какой тестовый ключ
                                   @Path("NameList")nameList:String, // имя моего списка
                                   ): ReceivedDataListId
    @DELETE("/RemoveShoppingList?list_{id}")
    suspend fun removeShoppingList(@Path("id")id:Int):ReceivedDataListId

    @POST("/AddToShoppingList?id={id}&value=tools&n=3")
    suspend fun addToShoppingList(@Path("id")id:String)
}