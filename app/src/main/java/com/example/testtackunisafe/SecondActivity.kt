package com.example.testtackunisafe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.testtackunisafe.databinding.ActivityMainBinding

class SecondActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}