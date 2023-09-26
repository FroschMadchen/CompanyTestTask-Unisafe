package com.example.testtackunisafe

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testtackunisafe.adapter.ItemProduct
import com.example.testtackunisafe.adapter.ProductAdapter
import com.example.testtackunisafe.adapter.Shop
import com.example.testtackunisafe.databinding.SecondActivityBinding
import com.example.testtackunisafe.`interface`.MainApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class SecondActivity : AppCompatActivity() {

    lateinit var binding: SecondActivityBinding
    private lateinit var adapter: ProductAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SecondActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()


        val retrofit = Retrofit.Builder()
            .baseUrl("https://cyberprot.ru/shopping/v1/")
            .client(client)
            .addConverterFactory(ScalarsConverterFactory.create()) // Add ScalarsConverterFactory here
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val mainApi = retrofit.create(MainApi::class.java)

        val products = mutableListOf<Shop>()
        products.add(Shop("26.09.2023 13:40", 0, "Конфеты Охота"))
        products.add(Shop("26.09.2023 13:45", 1, "Чай Гринфелд"))

        adapter = ProductAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter


        binding.createBtn.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                val keyValue = intent.getStringExtra("keyV")
                if (keyValue != null) {
                    val productsNum1Id = createShoppingList(itemProduct = ItemProduct(keyValue,"product"), mainApi)
                    val productsNum2Id = createShoppingList(itemProduct = ItemProduct(keyValue,"product2"), mainApi)
                    val productsNum3Id = createShoppingList(itemProduct = ItemProduct(keyValue,"product3"), mainApi)

                    runOnUiThread {
                        binding.apply {
                            textView.setText(productsNum1Id)
                            textView2.setText(productsNum2Id )
                            textView3.setText(productsNum3Id )
                        }

                    }
                }
                } catch (e:Exception){
                    Log.i("Exception", ""+e.message)
                    e.printStackTrace()
                }
            }
        }
    }

    private suspend fun createShoppingList(itemProduct: ItemProduct, mainApi: MainApi): Int {
        val productId = mainApi.createShoppingList(itemProduct)
        if (productId.isSuccessful) {
            return productId.body()?.list_id ?: 0
        } else {
            return 0
        }
    }
}
/*
        CoroutineScope(Dispatchers.IO).launch{
            val keyValue = intent.getStringExtra("keyV")
           if (keyValue != null){
               val objectShop = mainApi.getAllMyShopLists(keyValue)
               Log.d("SecondActivity", "Received keyV: $keyValue")
               runOnUiThread{
                   binding.apply {
                       adapter.submitList(objectShop.body()?.shop_list)
                       Log.e("runOnUiThread", "Create shop_list")
                   }
               }

           }else{
               Log.e("SecondActivity", "keyV is null")
           }

        }*/




