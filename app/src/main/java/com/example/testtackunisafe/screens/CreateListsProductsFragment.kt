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
import com.example.testtackunisafe.adapter.ActionListener
import com.example.testtackunisafe.adapter.ShopListAdapter
import com.example.testtackunisafe.databinding.FragmentCreateListsProductsBinding
import com.example.testtackunisafe.`interface`.MainApi
import com.example.testtackunisafe.recevied_data.ShopListConstructor
import com.example.testtackunisafe.utils.APP_ACTIVITY
import com.example.testtackunisafe.utils.KEY_VALUE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CreateListsProductsFragment : Fragment() {

    private var _binding:FragmentCreateListsProductsBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var navController: NavController

    private lateinit var adapter:ShopListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateListsProductsBinding.inflate(layoutInflater, container, false)

        navController = findNavController()

        val shoppingList = ArrayList<ShopListConstructor>()
        shoppingList.add(ShopListConstructor(0,"NAME"))

        adapter = ShopListAdapter(object :ActionListener{
            override fun deleteList(shopLists: ShopListConstructor) {
                shoppingList.removeAt(id)


            }

            override fun openList(shopLists: ShopListConstructor) {

                navController.navigate(R.id.action_createListsProducts_to_additionProduct)
            }
        },shoppingList)
        _binding?.recyclerViewProduct?.layoutManager = LinearLayoutManager(APP_ACTIVITY)
        _binding?.recyclerViewProduct?.adapter = adapter

        val mainApi = RetrofitClient.mainApi



        _binding?.btnAddProduct1?.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
//                    val dataKey = arguments?.getString("keyValue")
//                    Log.i("DataKey","data key: $dataKey")
//                    if (dataKey != null){

                        val item = createShoppingList(KEY_VALUE,"productEE", mainApi) // создаю список покупок
                        requireActivity().runOnUiThread{

                            Log.i("CreateNewList","createShoppingList()")
                            if (item != null){
                                shoppingList.add(item)
                                adapter.notifyDataSetChanged()
                                Log.i("Update RecyclerView","get item in shoppingList")
                            }
                        }
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

    private fun addShoppingList(item:ShopListConstructor,shoppingList: List<ShopListConstructor>){
       /* adapter.submitList(shoppingList)
        Log.i("CreateNewList","createShoppingList()")
            shoppingList.add(item)
            adapter.notifyDataSetChanged()
            Log.i("Update RecyclerView","get item in shoppingList")*/
        }

    }




