package com.example.testtackunisafe.domain


import com.example.testtackunisafe.domain.`interface`.MainApiV2
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitClientV2 {

        private val interceptor = HttpLoggingInterceptor()
            .apply { level = HttpLoggingInterceptor.Level.BODY }


        private val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()


        private val retrofit = Retrofit.Builder()
            .baseUrl("https://cyberprot.ru/shopping/v2/")
            .client(client)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val mainApiV2 = retrofit.create(MainApiV2::class.java)
}