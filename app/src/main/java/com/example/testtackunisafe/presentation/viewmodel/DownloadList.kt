package com.example.testtackunisafe.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.testtackunisafe.domain.RetrofitClient

class DownloadList : ViewModel() {
    private  val mainApi =RetrofitClient.mainApi


    fun getAllMyShopLists(key:String){ //Получить перечень списков

    }


}