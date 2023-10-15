package com.example.testtackunisafe.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testtackunisafe.domain.RetrofitClient
import com.example.testtackunisafe.domain.model.Product
import com.example.testtackunisafe.domain.model.loadingReadyList.Item
import kotlinx.coroutines.launch


/*Добавить товар в список - AddToShoppingList? и Вычеркнуть товар из списка -
CrossItOff? - в обоих случаях должен присутствовать параметр id номер списка в котором мы
зачеркиваем элемент или добавляем новый. В случае с добавлением элемента нужно передать
в параметрах название name и значение количества таких элементов value. Примеры запроса:
https://cyberprot.ru/shopping/v1/AddToShoppingList?id=4&value=tools&n=3
Ответ сервера: {"success":true,"item_id":8}, где item_id - id предмета внутри списка покупок.
https://cyberprot.ru/shopping/v1/RemoveShoppingList?list_id=2
Ответ сервера: {"success":true,"new_value":false}*/

class CreateProductVM : ViewModel() {
    private val mainApi = RetrofitClient.mainApi

    private val liveData = MutableLiveData<Item>()
    val data: LiveData<Item?> = liveData


    fun addProductToList(productList: MutableList<Item>, incomingProduct: Item) {
        viewModelScope.launch {
            val Product = addProductToServer(incomingProduct)
            liveData.value = Product
            Log.i("addProductToList "," in ViewModel")
        }
    }

    private suspend fun addProductToServer(incomingProduct: Item): Item {
        // https: cyberprot.ru/shopping/v1/AddToShoppingList?id=4&value=tools&n=3
        val info = mainApi.addToShoppingList(
            id = incomingProduct.id,
            value = incomingProduct.name,
            n = incomingProduct.created.toInt()
        )
        if (info.isSuccessful) {
            val item_id: Int? = info.body()?.item_id
            if (item_id != null) {
                val comingOutProduct = Item(
                    name = incomingProduct.name,
                    created = incomingProduct.created,
                    id = item_id,
                   is_crossed = false
                )
                Log.i("addProductToServer"," name: ${comingOutProduct.name}," +
                        "id item: ${comingOutProduct.id}")
                return comingOutProduct

            }else{
                return incomingProduct
            }
        }else{
            return incomingProduct
        }

    }


    suspend fun crossltOff(item_id:Int,id_list:Int):Boolean { //вычеркнуть  товар  из корзину
    // https://cyberprot.ru/shopping/v1/CrossItOff?list_id=276&id=45
                val crossItOff = mainApi.crossItOff(list_id = id_list, id = item_id)
        return crossItOff.isSuccessful



    }

    fun getShoppingList(){
    //Загрузить конкретный список - GetShoppingList?

    }


}
