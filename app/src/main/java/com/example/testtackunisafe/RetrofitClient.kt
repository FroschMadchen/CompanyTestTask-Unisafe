package com.example.testtackunisafe

import com.example.testtackunisafe.`interface`.MainApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitClient {

    private val interceptor = HttpLoggingInterceptor()
        .apply { level = HttpLoggingInterceptor.Level.BODY }


    private val client = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()


    private val retrofit = Retrofit.Builder()
        .baseUrl("https://cyberprot.ru/shopping/v1/")
        .client(client)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val mainApi = retrofit.create(MainApi::class.java)
}