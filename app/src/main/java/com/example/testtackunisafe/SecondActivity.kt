package com.example.testtackunisafe

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testtackunisafe.adapter.ProductAdapter
import com.example.testtackunisafe.adapter.Shop
import com.example.testtackunisafe.databinding.ActivityMainBinding
import com.example.testtackunisafe.databinding.SecondActivityBinding
import com.example.testtackunisafe.`interface`.MainApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SecondActivity : AppCompatActivity() {

    lateinit var binding: SecondActivityBinding
    private lateinit var adapter: ProductAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SecondActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ProductAdapter()
        binding.recyclerView.layoutManager=LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        val retrofit = Retrofit.Builder()
            .baseUrl("https://dummyjson.com")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val mainApi = retrofit.create(MainApi::class.java)

        CoroutineScope(Dispatchers.IO).launch{
            val keyValue = intent.getStringExtra("keyV")

           if (keyValue != null){
               Log.d("SecondActivity", "Received keyV: $keyValue")
           }else{
               Log.e("SecondActivity", "keyV is null")
           }
        /*    val objectList = keyValue?.let { mainApi.getSoppingList(it) }
            runOnUiThread{
                binding.apply {
                    adapter.submitList(objectList.shop_list)
                }
            }*/
        }


    }
}