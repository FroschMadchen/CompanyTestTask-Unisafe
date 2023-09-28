package com.example.testtackunisafe.screens

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.NavController
import com.example.testtackunisafe.R
import com.example.testtackunisafe.RetrofitClient
import com.example.testtackunisafe.databinding.FragmentEntranceBinding
import com.example.testtackunisafe.`interface`.MainApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class EntranceFragment : Fragment() {
    private var _binding: FragmentEntranceBinding? = null
    private val mBinding get() = _binding!!
    lateinit var navController: NavController
    lateinit var createListsProductsFragment: CreateListsProductsFragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEntranceBinding.inflate(layoutInflater, container, false)

        navController.navigate(R.id.action_entrance_to_createListsProducts)
        val mainApi = RetrofitClient.mainApi

        _binding!!.startBtn.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val keyValue = createTestKey(mainApi)
                Log.i("Key value", "" + keyValue)
                val successfulLogin: Boolean = authentication(mainApi, keyValue)
                Log.i("SuccessfulLogin", "Successful login: " + successfulLogin)
                requireActivity().runOnUiThread {
                    val bundle = Bundle()
                    bundle.putString("key", keyValue)
                    createListsProductsFragment.arguments = bundle
                    navController.navigate(R.id.action_entrance_to_createListsProducts)
                }
            }
        }
        // Inflate the layout for this fragment
        return mBinding.root
    }
    private suspend fun authentication(mainApi: MainApi, keyValue: String): Boolean {
        val success = mainApi.authentication(keyValue = keyValue)
        return success.success
    }
    private suspend fun createTestKey(mainApi: MainApi): String {
        val key = mainApi.createTestKey()
        return if (key.isSuccessful) {
            val keyData = key.body().toString() // Get the response body as a string
            Log.i("String value", (keyData ?: "key").toString())
            return keyData
        } else {
            "null"
        }
    }
}

