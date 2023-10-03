package com.example.testtackunisafe.screens

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testtackunisafe.R
import com.example.testtackunisafe.RetrofitClient
import com.example.testtackunisafe.RetrofitClient.mainApi
import com.example.testtackunisafe.adapter.ActionListener
import com.example.testtackunisafe.adapter.ShopListAdapter
import com.example.testtackunisafe.databinding.DialogAddListBinding
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
    private lateinit var binding:DialogAddListBinding
    private val mBinding get() = _binding!!
    private lateinit var navController: NavController

    private lateinit var adapter:ShopListAdapter
    private val shoppingList = ArrayList<ShopListConstructor>() //для доступа всех функций

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateListsProductsBinding.inflate(layoutInflater, container, false)

        navController = findNavController()
        val mainApi = RetrofitClient.mainApi

//        val shoppingList = ArrayList<ShopListConstructor>()
        shoppingList.add(ShopListConstructor(0,"NAME"))

        adapter = ShopListAdapter(object :ActionListener{
            override fun deleteList(listId:Int) {
                CoroutineScope(Dispatchers.IO).launch {
                    val deletedItem = removeShoppingList(listId)
                    Log.i("DeletedItem","$deletedItem")
                    APP_ACTIVITY.runOnUiThread{
                        if (deletedItem == true){
                            val removedIndex = shoppingList.indexOfFirst { it.listId == listId }
                            if (removedIndex != -1) {
                                shoppingList.removeAt(removedIndex)
                                adapter.notifyDataSetChanged()
                            }
                        }
                    }
                }
            }
            override fun openList(shopLists: ShopListConstructor) {
                navController.navigate(R.id.action_createListsProducts_to_additionProduct)
            }
        },shoppingList)
        _binding?.recyclerViewProduct?.layoutManager = LinearLayoutManager(APP_ACTIVITY)
        _binding?.recyclerViewProduct?.adapter = adapter




        _binding?.btnAddProduct1?.setOnClickListener{
            showAddListDialog()
        }




        /*   _binding?.btnAddProduct1?.setOnClickListener {
                  CoroutineScope(Dispatchers.IO).launch {
      //                    val dataKey = arguments?.getString("keyValue")
      //                    Log.i("DataKey","data key: $dataKey")
      //                    if (dataKey != null){

                              val item = createShoppingList(KEY_VALUE,"productEE", mainApi) // создаю список покупок
                              requireActivity().runOnUiThread{

                                  Log.i("CreateNewList","createShoppingList()")
                                  if (item != null){
                                      shoppingList.add(item)
                                      Log.i("NEWListID","$item.id , ${item.name}" )
                                      adapter.notifyDataSetChanged()
                                      Log.i("Update RecyclerView","get item in shoppingList")
                                  }
                              }
                      }

                  }*/




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
    private suspend fun removeShoppingList(listId:Int): Boolean? {
        val isSuccessRemove = mainApi.removeShoppingList(listId)
        if (isSuccessRemove.isSuccessful) {
            return isSuccessRemove.body()?.success
        } else {
            return false
        }
    }

   /* fun showAddListDialog(){
        binding = DialogAddListBinding.inflate(LayoutInflater.from(APP_ACTIVITY))
//        val dialogView = LayoutInflater.from(APP_ACTIVITY).inflate(R.layout.dialog_add_list,null)
//        val editTextNameList = dialogView.findViewById<EditText>(R.id.nameList)
//        val btnCreate = dialogView.findViewById<Button>(R.id.btnCreateList)
        binding.btnCreateList

        val dialog = AlertDialog.Builder(APP_ACTIVITY)
            .setView(binding.root)
            .setTitle("Добавить список")
            .setPositiveButton("Добавить"){_,_->
                val listName = binding.nameList.text.toString()
                if (listName.isNotEmpty()){
                    CoroutineScope(Dispatchers.IO).launch {
                        val item = createShoppingList(KEY_VALUE,"productEE", mainApi) // создаю список покупок
                        requireActivity().runOnUiThread{
                            Log.i("CreateNewList","createShoppingList()")
                            if (item != null) {
                                addItemInShoppingList(item)
                            }
                        }
                    }
                }
            }
            .setPositiveButton("Отмена",null)
            .create()
        dialog.show()
    }*/

    fun addItemInShoppingList(item:ShopListConstructor){ // добавляем эелемент в recyclerView
            shoppingList.add(item)
            Log.i("NEWListID","$item.id , ${item.name}" )
            adapter.notifyDataSetChanged()
            Log.i("Update RecyclerView","get item in shoppingList")

    }

  fun showAddListDialog(){
      binding = DialogAddListBinding.inflate(LayoutInflater.from(APP_ACTIVITY))
        val dialog = AlertDialog.Builder(APP_ACTIVITY)
            .setView(binding.root)
            .setTitle("Добавить список")
            .setPositiveButton("Добавить", null)
            .setNegativeButton("Отмена", null)
            .create()

       binding.btnCreateList.setOnClickListener {
          // Здесь выполните код, который должен выполняться при нажатии на кнопку
          val listName = binding.nameList.text.toString()
          if (listName.isNotEmpty()) {
              CoroutineScope(Dispatchers.IO).launch {
                  val item = createShoppingList(KEY_VALUE, "productEE", mainApi)
                  requireActivity().runOnUiThread {
                      Log.i("CreateNewList", "createShoppingList()")
                      if (item != null) {
                          addItemInShoppingList(item)
                      }
                  }
              }
          }
      }
        dialog.show()

     /* // Установите слушатель нажатия на кнопку
      binding.btnCreateList.setOnClickListener {
          // Здесь выполните код, который должен выполняться при нажатии на кнопку
          val listName = binding.nameList.text.toString()
          if (listName.isNotEmpty()) {
              CoroutineScope(Dispatchers.IO).launch {
                  val item = createShoppingList(KEY_VALUE, "productEE", mainApi)
                  requireActivity().runOnUiThread {
                      Log.i("CreateNewList", "createShoppingList()")
                      if (item != null) {
                          addItemInShoppingList(item)
                      }
                  }
              }
          }
      }*/


  }
}




