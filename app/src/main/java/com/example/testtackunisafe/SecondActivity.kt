package com.example.testtackunisafe

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testtackunisafe.adapter.ProductAdapter
import com.example.testtackunisafe.databinding.SecondActivityBinding
import com.example.testtackunisafe.`interface`.MainApi
import com.example.testtackunisafe.recevied_data.ShopListConstructor
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

        val shoppingList = mutableListOf<ShopListConstructor>()


     /*   adapter = ProductAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
*/

        binding.createBtn.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                val keyValue = intent.getStringExtra("keyV")
                if (keyValue != null) {
                    val productsNum1Id = createShoppingList(keyValue,"product", mainApi) // создаю список покупок                    val productsNum2Id = createShoppingList(keyValue,"product2", mainApi)
                    val productsNum3Id = createShoppingList(keyValue,"product3", mainApi)

                    runOnUiThread {
                        adapter.submitList(shoppingList)

                        if (productsNum1Id != null) { // отображаю в recyclerview
                            shoppingList.add(productsNum1Id)
                            adapter.notifyDataSetChanged()
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

    private suspend fun createShoppingList(key:String,nameList:String, mainApi: MainApi):ShopListConstructor? {
        val productId = mainApi.createShoppingList(key,nameList)
        if (productId.isSuccessful) {
           val shopList: ShopListConstructor? =
               productId.body()?.list_id?.let { ShopListConstructor(it, nameList) }
            return shopList
        } else {
            return null
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




