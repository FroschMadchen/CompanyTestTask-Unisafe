package com.example.testtackunisafe.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testtackunisafe.RetrofitClient
import com.example.testtackunisafe.`interface`.MainApi
import com.example.testtackunisafe.recevied_data.ShopListConstructor
import com.example.testtackunisafe.utils.KEY_VALUE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateListsVM:ViewModel() {
    private val mainApi = RetrofitClient.mainApi
    private val itemLive = MutableLiveData<ShopListConstructor?>()
    val item:LiveData<ShopListConstructor?> = itemLive

 fun sendNameList(listName:String){
     viewModelScope.launch {
         val item = createShoppingList(KEY_VALUE, listName, mainApi)
         itemLive.value = item

     }
  }



    private suspend fun createShoppingList( //создание списка
        dataKey: String,
        nameList: String,
        mainApi: MainApi
    ): ShopListConstructor? {
        val productId = mainApi.createShoppingList(dataKey, nameList)
        if (productId.isSuccessful) {
            val shopList: ShopListConstructor? =
                productId.body()?.list_id?.let { ShopListConstructor(it, nameList) }
            return shopList
        } else {
            return null
        }
    }
}