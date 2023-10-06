package com.example.testtackunisafe.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testtackunisafe.domain.RetrofitClient
import com.example.testtackunisafe.domain.`interface`.MainApi
import com.example.testtackunisafe.domain.custom_type.ShopListConstructor
import com.example.testtackunisafe.data.utils.KEY_VALUE
import com.example.testtackunisafe.domain.custom_type.Product
import kotlinx.coroutines.launch

class CreateListsVM:ViewModel() {
    private val mainApi = RetrofitClient.mainApi
    private val itemLive = MutableLiveData<ShopListConstructor?>()
    val item:LiveData<ShopListConstructor?> = itemLive

 fun sendNameList(listName:String,productList:ArrayList<Product>){ // получаем имя списка
     viewModelScope.launch {
         val item = createShoppingList(KEY_VALUE, listName, productList = productList)
         itemLive.value = item

     }
  }



    private suspend fun createShoppingList( //создание списка
        dataKey: String,
        nameList: String,
        productList:ArrayList<Product>
    ): ShopListConstructor? {
        val listId = mainApi.createShoppingList(dataKey, nameList)
        if (listId.isSuccessful) {
            val shopList: ShopListConstructor? =
                listId.body()?.list_id?.let { ShopListConstructor(it, nameList,productList) }
            return shopList
        } else {
            return null
        }
    }
}