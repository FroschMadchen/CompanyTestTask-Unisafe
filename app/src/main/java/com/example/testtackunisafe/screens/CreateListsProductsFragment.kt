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

    private var _binding: FragmentCreateListsProductsBinding? = null
    private lateinit var binding: DialogAddListBinding
    private val mBinding get() = _binding!!
    private lateinit var navController: NavController

    private lateinit var adapter: ShopListAdapter
    private val shoppingList = ArrayList<ShopListConstructor>() // список списков

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateListsProductsBinding.inflate(
            layoutInflater,
            container,
            false
        )
        navController = findNavController()
        val mainApi = RetrofitClient.mainApi

        adapter = ShopListAdapter(object : ActionListener {  // реализация интерфейса
            override fun deleteList(listId: Int) { //  удаления
                CoroutineScope(Dispatchers.IO).launch {
                    val deletedItem = removeShoppingList(listId)
                    Log.i("DeletedItem", "$deletedItem")
                    APP_ACTIVITY.runOnUiThread {
                        if (deletedItem == true) {
                            val removedIndex = shoppingList.indexOfFirst { it.listId == listId }
                            if (removedIndex != -1) {
                                shoppingList.removeAt(removedIndex)
                                adapter.notifyDataSetChanged()
                            }
                        }
                    }
                }
            }

            override fun openList(listId: Int) { // клик по элементу
                navController.navigate(R.id.action_createListsProducts_to_additionProduct)
            }
        }, shoppingList)

        _binding?.recyclerViewProduct?.layoutManager = LinearLayoutManager(APP_ACTIVITY)
        _binding?.recyclerViewProduct?.adapter = adapter

        _binding?.btnAddProduct1?.setOnClickListener {// FloatingActionButton
            showAddListDialog()
        }

        return mBinding.root
    }


    private suspend fun createShoppingList( //создание списка
        dataKey: String,
        nameList: String,
        mainApi: MainApi
    ): ShopListConstructor? {
        val productId = mainApi.createShoppingList(dataKey, nameList)
        if (productId.isSuccessful) {
            val shopList: ShopListConstructor? =
                productId.body()?.list_id?.let { ShopListConstructor(it, nameList) }
            return shopList
        } else {
            return null
        }
    }

    private suspend fun removeShoppingList(listId: Int): Boolean? { // удаление списка
        val isSuccessRemove = mainApi.removeShoppingList(listId)
        if (isSuccessRemove.isSuccessful) {
            return isSuccessRemove.body()?.success
        } else {
            return false
        }
    }

    fun addItemInShoppingList(item: ShopListConstructor) { // добавляем эелемент в recyclerView
        shoppingList.add(item)
        Log.i("NEWListID", "$item.id , ${item.name}")
        adapter.notifyDataSetChanged()
        Log.i("Update RecyclerView", "get item in shoppingList")

    }

    fun showAddListDialog() { // диалоговое окно, создание списка
        binding = DialogAddListBinding.inflate(LayoutInflater.from(APP_ACTIVITY))
        val dialog = AlertDialog.Builder(APP_ACTIVITY)
            .setView(binding.root)
            .setTitle("Добавить список")
            .setPositiveButton("Создать") { _, _ ->
                val listName = binding.nameList.text.toString()
                if (listName.isNotEmpty()) {
                    CoroutineScope(Dispatchers.IO).launch {
                        val item = createShoppingList(KEY_VALUE, listName, mainApi)
                        requireActivity().runOnUiThread {
                            Log.i("CreateNewList", "createShoppingList()")
                            if (item != null) {
                                addItemInShoppingList(item)
                            }
                        }
                    }
                }
            }
            .setNegativeButton("Отмена", null)
            .create()

        dialog.show()
    }
}




