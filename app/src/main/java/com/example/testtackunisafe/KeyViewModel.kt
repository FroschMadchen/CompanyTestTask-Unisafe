package com.example.testtackunisafe


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class KeyViewModel : ViewModel(){
    val key = MutableLiveData<String>()
}


