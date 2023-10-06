package com.example.testtackunisafe.presentation.screens

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testtackunisafe.R
import com.example.testtackunisafe.domain.RetrofitClient
import com.example.testtackunisafe.domain.RetrofitClient.mainApi
import com.example.testtackunisafe.presentation.adapter.ActionListener
import com.example.testtackunisafe.presentation.adapter.ShopListAdapter
import com.example.testtackunisafe.databinding.DialogAddListBinding
import com.example.testtackunisafe.databinding.FragmentCreateListsBinding
import com.example.testtackunisafe.domain.custom_type.ShopListConstructor
import com.example.testtackunisafe.presentation.viewmodel.CreateListsVM
import com.example.testtackunisafe.data.utils.APP_ACTIVITY
import com.example.testtackunisafe.domain.custom_type.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CreateListsFragment : Fragment() {

    private var _binding: FragmentCreateListsBinding? = null
    private lateinit var binding: DialogAddListBinding
    private val mBinding get() = _binding!!
    private lateinit var navController: NavController
    private lateinit var vm: CreateListsVM

    private lateinit var adapter: ShopListAdapter
    private val shoppingList = ArrayList<ShopListConstructor>() // список списков
    private val productList = ArrayList<Product>() // списко продуктов

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateListsBinding.inflate(
            layoutInflater,
            container,
            false
        )
        navController = findNavController()
        val mainApi = RetrofitClient.mainApi
        vm = ViewModelProvider(this).get(CreateListsVM::class.java)


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

        vm.item.observe(APP_ACTIVITY, Observer {
            if (it != null) {
                addItemInShoppingList(it)
            }
        })

        return mBinding.root
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
                vm.sendNameList(listName,productList)
                   // addItemInShoppingList(item)

            }
            .setNegativeButton("Отмена", null)
            .create()

        dialog.show()
    }
}




