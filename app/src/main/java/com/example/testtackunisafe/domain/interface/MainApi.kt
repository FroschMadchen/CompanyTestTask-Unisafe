package com.example.testtackunisafe.domain.`interface`


import com.example.testtackunisafe.domain.model.ReceivedData
import com.example.testtackunisafe.domain.model.loadingReadyList.ShopListData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface MainApi {
    @POST("CreateTestKey?")
    @Headers("Accept:text/plane")
    suspend fun createTestKey(): Response<String>
//    https://cyberprot.ru/shopping/v1/CreateTestKey?
//    {"key" = "#XXXXXX#"} String (работает)
    @GET("Authentication")
    suspend fun authentication(@Query("key")keyValue:String): ShopListData
//    https://cyberprot.ru/shopping/v1/Authentication?key=92EGHS
//    {"success":true} Boolean (работает)


    @POST("CreateShoppingList") // создать список покупок
    //@Headers("Accept:text/plane")
    suspend fun createShoppingList(@Query("key")key: String,
                                   @Query("name")name:String): Response<ReceivedData>
    //https://cyberprot.ru/shopping/v1/CreateShoppingList?key=92EGHS&name=Shopping%20with%20bestie
//    {success":true,"list_id":4}. list_id - id списка, созданный на сервере, по этому id
//     можно взаимодествовать со списком. (работает)

    @POST("RemoveShoppingList")
    @Headers("Accept:text/plane")// удалить список покупок
    suspend fun removeShoppingList(@Query("list_id")list_id:Int):Response<ShopListData>
//  https://cyberprot.ru/shopping/v1/RemoveShoppingList?list_id=2  https://cyberprot.ru/shopping/v1/RemoveShoppingList?list_id=498
//  Ответ сервера: {"success":true,"new_value":false}



    @POST("AddToShoppingList") //  добавить товра в список покупок
    suspend fun addToShoppingList(@Query("id")id:Int,
                                  @Query("value")value:String,
                                  @Query("n")n:Int
    ):Response<ReceivedData>
//     https://cyberprot.ru/shopping/v1/AddToShoppingList?id=4&value=tools&n=3 (id :спсика, value : (имя продукта), n : кол-во
//     Ответ сервера: {"success":true,"item_id":8}, где item_id - id предмета внутри списка покупок.
//
    @POST("CrossItOff") // зачеркнуть товра из списка покупок
    @Headers("Accept:text/plane")
    suspend fun crossItOff(@Query ("list_id")list_id:Int,
                           @Query ("id")id:Int
    ):Response<ShopListData>
//    https://cyberprot.ru/shopping/v1/CrossItOff?list_id=276&id=45
//    Ответ сервера: {"success":true,"new_value":false}



/*    @POST("RemoveFromList") //удалить товар из списка
    @Headers("Accept:text/plane")
    suspend fun removeFromList(@Query("list_id")list_id:Int,
                                @Query("item_id")item_id:Int
    ):Response<ReceivedData>
    //    https://cyberprot.ru/shopping/v2/RemoveFromList?list_id=711&item_id=877*/

    @GET("GetAllMyShopLists")
    suspend fun getAllMyShopLists(@Query("key") key: String):Response<ShopListData> //Получить перечень списков
//    https://cyberprot.ru/shopping/v1/GetAllMyShopLists?key=92EGHS
//    {"shop_list":[{"created":"2023-08-28 06:35:44","name":"Test1","id":2},...,"success":true}

/*    @GET("GetShoppingList")
    suspend fun getSoppingList(@Query("key")key:String):Response<ProductListData>

//    https://cyberprot.ru/shopping/v2/GetShoppingList?list_id=711*/
}

