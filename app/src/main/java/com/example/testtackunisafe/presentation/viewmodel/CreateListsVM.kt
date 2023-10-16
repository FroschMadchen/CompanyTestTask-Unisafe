package com.example.testtackunisafe.presentation.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testtackunisafe.domain.RetrofitClient

import com.example.testtackunisafe.data.utils.KEY_VALUE
import com.example.testtackunisafe.domain.model.loadingReadyList.Shop
import com.example.testtackunisafe.domain.model.loadingReadyList.ShopListData
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class CreateListsVM:ViewModel() {
    private val mainApi = RetrofitClient.mainApi
    private val itemLive = MutableLiveData<Shop?>()
    val item:LiveData<Shop?> = itemLive

 @SuppressLint("SuspiciousIndentation")
 fun createList(listName:String){ // получаем имя списка
     viewModelScope.launch {
         val item = createShoppingList(KEY_VALUE, listName)
         itemLive.value = item

             Log.i("CreateListViewModel", "${item?.name}  ,${item?.id}  " )

     }
  }
    private suspend fun createShoppingList( //создание списка
        dataKey: String,
        nameList: String,
    ): Shop? {
        val listId = mainApi.createShoppingList(dataKey, nameList)
        if (listId.isSuccessful) {
            val shopList: Shop? =
                listId.body()?.list_id?.let { Shop("tame", it,nameList) }
                /*listId.body()?.list_id?.let { Shop(it, nameList,listOfList) }*/
            Log.i("createShoppingListVM","${listId.body()?.list_id}")
            return shopList
        } else {
            Log.e("createShoppingListVM",
                "Failed to create a shopping list. Response code: ${listId.code()}")

            return null
        }
    }

    suspend fun getAllMyShopLists(key: String): ShopListData? { //Получить перечень списков
        var dataListOfLists: ShopListData? = null
        return suspendCoroutine { continuation ->
            viewModelScope.launch {
                try {
                    val successfulLogin = mainApi.getAllMyShopLists(key)
                    if (successfulLogin.isSuccessful) {
                        dataListOfLists = successfulLogin.body()!!
                        continuation.resume(dataListOfLists)
                    }
                    Log.i("getAllMyShopListsVM", "${successfulLogin.body()?.success}")
                } catch (e: Exception) {
                    Log.i("ERROR", "${e.message}")
                }
            }
        }
    }
}