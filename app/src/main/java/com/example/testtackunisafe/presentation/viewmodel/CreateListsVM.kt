package com.example.testtackunisafe.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testtackunisafe.domain.RetrofitClient

import com.example.testtackunisafe.data.utils.KEY_VALUE
import com.example.testtackunisafe.domain.model.loadingReadyList.Shop
import kotlinx.coroutines.launch

class CreateListsVM:ViewModel() {
    private val mainApi = RetrofitClient.mainApi
    private val itemLive = MutableLiveData<Shop?>()
    val item:LiveData<Shop?> = itemLive

 fun sendNameList(listName:String,listOfList: MutableList<Shop>){ // получаем имя списка
     viewModelScope.launch {
         val item = createShoppingList(KEY_VALUE, listName, listOfList = listOfList)
         itemLive.value = item
     }
  }
    private suspend fun createShoppingList( //создание списка
        dataKey: String,
        nameList: String,
        listOfList: MutableList<Shop>
    ): Shop? {
        val listId = mainApi.createShoppingList(dataKey, nameList)
        if (listId.isSuccessful) {
            val shopList: Shop? =
                listId.body()?.list_id?.let { Shop("tame", it,nameList) }
                /*listId.body()?.list_id?.let { Shop(it, nameList,listOfList) }*/
            return shopList
        } else {
            return null
        }
    }


}