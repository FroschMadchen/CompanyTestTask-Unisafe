package com.example.testtackunisafe.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testtackunisafe.domain.RetrofitClient
import com.example.testtackunisafe.domain.custom_type.Product


/*Добавить товар в список - AddToShoppingList? и Вычеркнуть товар из списка -
CrossItOff? - в обоих случаях должен присутствовать параметр id номер списка в котором мы
зачеркиваем элемент или добавляем новый. В случае с добавлением элемента нужно передать
в параметрах название name и значение количества таких элементов value. Примеры запроса:
https://cyberprot.ru/shopping/v1/AddToShoppingList?id=4&value=tools&n=3
Ответ сервера: {"success":true,"item_id":8}, где item_id - id предмета внутри списка покупок.
https://cyberprot.ru/shopping/v1/RemoveShoppingList?list_id=2
Ответ сервера: {"success":true,"new_value":false}*/

class CreateProductVM : ViewModel(){
    private val mainApi = RetrofitClient.mainApi

    private val liveData = MutableLiveData<Product>()
    val data: LiveData<Product?> = liveData


    fun addToShoppingList(itemProduct:MutableList<Product>){ //добавить товар в корзину

    }

    suspend fun CrossltOff(){ //удалить товар  из корзину

    }
}