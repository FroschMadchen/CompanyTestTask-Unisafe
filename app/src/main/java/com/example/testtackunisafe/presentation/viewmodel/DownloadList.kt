package com.example.testtackunisafe.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testtackunisafe.domain.RetrofitClient
import com.example.testtackunisafe.domain.model.loadingReadyList.ShopListData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class DownloadList:ViewModel() {
    private val mainApi = RetrofitClient.mainApi

   /* suspend fun getAllMyShopLists(key: String): ShopListData? { //Получить перечень списков


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
    }*/

    fun updateData(dataListOfLists: ShopListData?) {

    }
}
