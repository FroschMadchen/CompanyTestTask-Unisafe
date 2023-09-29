package com.example.testtackunisafe.screens

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.testtackunisafe.R
import com.example.testtackunisafe.databinding.ActivityMainBinding
import com.example.testtackunisafe.databinding.MainActivity1Binding
import com.example.testtackunisafe.`interface`.MainApi
import com.example.testtackunisafe.utils.APP_ACTIVITY
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class MainActivity1 : AppCompatActivity() {
    lateinit var mToolbar: Toolbar
    lateinit var mNavConverter: NavController
    private var _binding: MainActivity1Binding? = null
    val mBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = MainActivity1Binding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mToolbar = mBinding.toolbar
        mNavConverter = Navigation.findNavController(this, R.id.nav_host_fragment)
        setSupportActionBar(mToolbar)
        title = getString(R.string.name_app_custom)
        APP_ACTIVITY = this
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
