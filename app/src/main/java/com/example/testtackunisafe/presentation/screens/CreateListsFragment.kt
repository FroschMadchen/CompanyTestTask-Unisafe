package com.example.testtackunisafe.presentation.screens

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testtackunisafe.R
import com.example.testtackunisafe.domain.RetrofitClient.mainApi
import com.example.testtackunisafe.presentation.adapter.ActionListener
import com.example.testtackunisafe.presentation.adapter.ShopListAdapter
import com.example.testtackunisafe.databinding.DialogCreateListBinding
import com.example.testtackunisafe.databinding.FragmentCreateListsBinding

import com.example.testtackunisafe.presentation.viewmodel.CreateListsVM
import com.example.testtackunisafe.data.utils.APP_ACTIVITY
import com.example.testtackunisafe.domain.RetrofitClientV2.mainApiV2
import com.example.testtackunisafe.domain.model.Product
import com.example.testtackunisafe.domain.model.loadingReadyList.Shop
import com.example.testtackunisafe.domain.model.loadingReadyList.ShopListData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CreateListsFragment : Fragment() {

    private var _binding: FragmentCreateListsBinding? = null
    private lateinit var binding: DialogCreateListBinding
    private val mBinding get() = _binding!!
    private lateinit var navController: NavController
    private lateinit var vm: CreateListsVM

    private lateinit var adapter: ShopListAdapter
//    private val shoppingList = ArrayList<ShopListConstructor>() // список списков
    private val productList = ArrayList<Product>() // списко продуктов

    private  var listOfLists = mutableListOf<Shop>() //ПЕРЕЧЕНЬ СПИСОК



    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
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
        vm = ViewModelProvider(this).get(CreateListsVM::class.java)



        adapter = ShopListAdapter(object : ActionListener {  // реализация интерфейса
            override fun deleteList(id: Int) { //  удаления
                /*CoroutineScope(Dispatchers.IO).launch {
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
                }*/ TODO("NOT IMPLEMENTATION")
            }

            override fun openList(id: Int) { // клик по элементу
              /*  val isIdPresent = shoppingList.find { product -> product.listId == listId }
                if (isIdPresent != null){
                    val bundle = Bundle()
                    bundle.putSerializable("keyValue",isIdPresent )
                    navController.navigate(R.id.action_createListsProducts_to_additionProduct,bundle)
                }*/
//                navController.navigate(R.id.action_createListsProducts_to_additionProduct)
                getShoppingList(id)

            }
        }, listOfLists)



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

        val shopList = arguments?.getSerializable("shopList",ShopListData::class.java)
        if(shopList != null){
            val list: List<Shop> = shopList.shop_list
            listOfLists.clear()
            listOfLists.addAll(list)
            Log.i("New value","${listOfLists.size}")
            adapter.notifyDataSetChanged()

            /*val list: List<Shop> = shopList.shop_list
            listOfLists.clear()
            listOfLists.addAll(list)
            Log.i("New value", "${listOfLists.size}")
            adapter.notifyDataSetChanged()*/
        }
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

    fun addItemInShoppingList(item: Shop) { // добавляем созданный эелемент в recyclerView
        listOfLists.add(item)
        Log.i("NEWListID", "$item.id , ${item.name}")
        adapter.notifyDataSetChanged()
        Log.i("Update RecyclerView", "get item in shoppingList")

    }

    fun showAddListDialog() { // диалоговое окно, создание списка
        binding = DialogCreateListBinding.inflate(LayoutInflater.from(APP_ACTIVITY))
        val dialog = AlertDialog.Builder(APP_ACTIVITY)
            .setView(binding.root)
            .setTitle("Добавить список")
            .setPositiveButton("Создать") { _, _ ->
                val listName = binding.nameList.text.toString() //получаем имя нового  листа
                vm.sendNameList(listName,listOfLists)
//                addItemInShoppingList(item)

            }
            .setNegativeButton("Отмена", null)
            .create()

        dialog.show()
    }
    fun getShoppingList (id:Int) { //загружаю список продуктов через id списка
        CoroutineScope(Dispatchers.IO).launch{
            val productInfo = mainApiV2.getSoppingList(id)
            if (productInfo.isSuccessful)
                APP_ACTIVITY.runOnUiThread {
                    val product = productInfo.body()
                    val bundle = Bundle()
                    bundle.putInt("id_list",id)
                    bundle.putSerializable("item_list",product )
                    navController.navigate(R.id.action_createListsProducts_to_additionProduct,bundle)
             }
        }
    }
}









