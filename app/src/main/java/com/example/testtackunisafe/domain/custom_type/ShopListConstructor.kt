package com.example.testtackunisafe.domain.custom_type

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable


data class ShopListConstructor(
    val listId:Int,
    val name: String,
    val list:ArrayList<Product>
    ) : Serializable
