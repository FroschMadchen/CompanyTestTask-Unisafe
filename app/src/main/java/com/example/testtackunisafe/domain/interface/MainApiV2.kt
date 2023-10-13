package com.example.testtackunisafe.domain.`interface`

import com.example.testtackunisafe.domain.model.loadingReadyList.ProductListData
import com.example.testtackunisafe.domain.model.ReceivedData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface MainApiV2 {
    @GET("GetShoppingList")
    suspend fun getSoppingList(@Query("list_id")list_id:Int): Response<ProductListData> // получить список продуктов
//    https://cyberprot.ru/shopping/v2/GetShoppingList?list_id=711


    @POST("RemoveFromList") //удалить товар из списка
    @Headers("Accept:text/plane")
    suspend fun removeFromList(@Query("list_id")list_id:Int,
                                @Query("item_id")item_id:Int
    ):Response<ReceivedData>
    //    https://cyberprot.ru/shopping/v2/RemoveFromList?list_id=711&item_id=877
}