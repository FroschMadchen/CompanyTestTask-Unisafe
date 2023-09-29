package com.example.testtackunisafe.screens

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testtackunisafe.R
import com.example.testtackunisafe.RetrofitClient
import com.example.testtackunisafe.adapter.ProductAdapter
import com.example.testtackunisafe.databinding.FragmentAdditionProductBinding
import com.example.testtackunisafe.databinding.FragmentCreateListsProductsBinding
import com.example.testtackunisafe.`interface`.MainApi
import com.example.testtackunisafe.recevied_data.ShopListConstructor
import com.example.testtackunisafe.utils.APP_ACTIVITY
import com.example.testtackunisafe.utils.KEY_VALUE
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CreateListsProductsFragment : Fragment() {

    private var _binding:FragmentCreateListsProductsBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var navController: NavController

    private lateinit var adapter:ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateListsProductsBinding.inflate(layoutInflater, container, false)

        navController = findNavController()

        val shoppingList = mutableListOf<ShopListConstructor>()
        shoppingList.add(ShopListConstructor(1,"LIST1"))
        shoppingList.add(ShopListConstructor(4,"LIST12"))

        adapter = ProductAdapter()
        _binding?.recyclerViewProduct?.layoutManager = LinearLayoutManager(APP_ACTIVITY)
        _binding?.recyclerViewProduct?.adapter = adapter

        val mainApi = RetrofitClient.mainApi

        _binding?.btnAddProduct1?.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val dataKey = arguments?.getString("keyValue")
                    Log.i("GetKey","Get Key in Create Lists")
                    Log.i("DataKey","data key: $dataKey")
//                    if (dataKey != null){

                        val productsNum1Id = createShoppingList(KEY_VALUE,"productEE", mainApi) // создаю список покупок
                        requireActivity().runOnUiThread{
                            adapter.submitList(shoppingList)
                            Log.i("CreateNewList","createShoppingList()")
                            if (productsNum1Id != null){
                                shoppingList.add(productsNum1Id)
                                adapter.notifyDataSetChanged()
                                Log.i("Update RecyclerView","get item in shoppingList")
                            }
                        }
//                    }
                }catch (e:Exception){
                    Log.i("Exception", ""+e.message)
                }

            }

        }

        adapter.onDeleteItemListener = object : ProductAdapter.OnDeleteItemListener {
            override fun onDeleteItem(position: Int) {
                shoppingList.removeAt(position)
                adapter.notifyItemRemoved(position)// UPDATE RecyclerView
            }
        }
        return mBinding.root
    }
    private suspend fun createShoppingList(dataKey: String, nameList: String, mainApi: MainApi): ShopListConstructor? {
        val productId = mainApi.createShoppingList(dataKey,nameList)
        if (productId.isSuccessful) {
            val shopList: ShopListConstructor? =
                productId.body()?.list_id?.let { ShopListConstructor(it, nameList) }
            return shopList
        } else {
            return null
        }
    }
}


