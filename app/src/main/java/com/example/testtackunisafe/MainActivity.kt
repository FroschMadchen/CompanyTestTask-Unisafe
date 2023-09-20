package com.example.testtackunisafe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testtackunisafe.adapter.ProductAdapter
import com.example.testtackunisafe.databinding.ActivityMainBinding
import com.example.testtackunisafe.`interface`.MainApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val retrofit = Retrofit.Builder()
            .baseUrl("https://dummyjson.com")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val mainApi = retrofit.create(MainApi::class.java)


        binding.startBtn.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val keyValue = mainApi.createTestKey()
                runOnUiThread {
                    binding.apply {
                        val keyViewModel = keyValue
                    }
                }
            }


        }
    }
}

