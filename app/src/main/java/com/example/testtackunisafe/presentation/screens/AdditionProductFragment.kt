package com.example.testtackunisafe.presentation.screens

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testtackunisafe.data.utils.APP_ACTIVITY
import com.example.testtackunisafe.databinding.DialogAddProductBinding
import com.example.testtackunisafe.databinding.FragmentAdditionProductBinding
import com.example.testtackunisafe.domain.RetrofitClientV2
import com.example.testtackunisafe.domain.model.loadingReadyList.Item
import com.example.testtackunisafe.domain.model.loadingReadyList.ProductListData
import com.example.testtackunisafe.presentation.adapter.ActionListenerProduct
import com.example.testtackunisafe.presentation.adapter.SpecificProductListAdapter
import com.example.testtackunisafe.presentation.viewmodel.CreateProductVM
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AdditionProductFragment : Fragment() {

    private var _binding: FragmentAdditionProductBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var binding: DialogAddProductBinding

    private lateinit var adapter: SpecificProductListAdapter

    //    private var productList = ArrayList<Product>()
    private var productList = mutableListOf<Item>()
    private lateinit var vm: CreateProductVM

    private var list_id: Int = 0

    private val handler = Handler(Looper.getMainLooper())
    private val refreshInterval = 10000L

    private val refreshRunnable = object : Runnable {
        override fun run() {
            downloadListProduct()
            handler.postDelayed(this, refreshInterval)
        }
    }

    @SuppressLint("SuspiciousIndentation")
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdditionProductBinding.inflate(
            layoutInflater,
            container,
            false
        )
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val list_item = arguments?.getSerializable("item_list", ProductListData::class.java)
        val listID = arguments?.getInt("id_list")
        if (list_item != null && listID != null) {
            list_id = listID
            productList = list_item.item_list.toMutableList()
            Log.i("BUNDEL f3", "success get productList:${list_item.success}")
            (activity as AppCompatActivity).supportActionBar?.title = listID.toString()
        }
        handler.post(refreshRunnable)

        /*val keyText = arguments?.getSerializable("keyValue", ShopListConstructor::class.java)
        if (keyText != null) {
            list_id = keyText.listId
            productList = keyText.list
            (activity as AppCompatActivity).supportActionBar?.title = keyText.name

                Log.i("mainUI"," ID СПИСКА ${list_id}")
        }*/

        adapter = SpecificProductListAdapter(object : ActionListenerProduct {
            override fun crossItProduct(id: Int) {
                val selectedItem = productList.indexOfFirst { it.id == id }
                if (selectedItem != -1) {
                    val item = productList[selectedItem]
//                    item.is_crossed = true
                    CoroutineScope(Dispatchers.IO).launch {
                        val crossltOff = vm.crossltOff(id, list_id)
                        if (crossltOff) {
                            APP_ACTIVITY.runOnUiThread {
                                item.is_crossed = crossltOff
                                adapter.notifyDataSetChanged()
                                Log.i("Strikethrough element ", "element crossed out: ${item.id}")
                            }
                        }

                    }
                }
            }

            override fun deleteProduct(id: Int) {
                val selectedItem = productList.indexOfFirst { it.id == id }
                if (selectedItem != -1) {
                    val item = productList[selectedItem]
                    CoroutineScope(Dispatchers.IO).launch {
                        val remove = vm.removeFromList(list_id = list_id, item_id = id)
                        Log.i("Remove", "$remove")
                            APP_ACTIVITY.runOnUiThread {
                                productList.removeAt(selectedItem)
                                Log.i("crossProduct mainUI", "item: $selectedItem")
                                adapter.notifyDataSetChanged()
                            }
                        }

                    }
                }


        }, productList)
        mBinding.recyclerViewProduct.layoutManager = LinearLayoutManager(APP_ACTIVITY)
        mBinding.recyclerViewProduct.adapter = adapter

        vm = ViewModelProvider(this).get(CreateProductVM::class.java)

        _binding?.btnFloating?.setOnClickListener {
            showAddProductDialog(list_id)
        }
        vm.data.observe(APP_ACTIVITY) {
            if (it != null) {
                productList.add(it)
                Log.i("liveData", "observe :${it.name},${it.id}")
                adapter.notifyDataSetChanged()
            }
        }
        return mBinding.root
    }

    fun showAddProductDialog(list_id: Int) { // диалоговое оконо, добавления продукта
        binding = DialogAddProductBinding.inflate(LayoutInflater.from(APP_ACTIVITY))
        val dialog = AlertDialog.Builder(APP_ACTIVITY)
            .setView(binding.root)
            .setTitle("Добавить продукт")
            .setPositiveButton("Сохранить") { _, _ ->
                val nameProduct = binding.nameProduct.text.toString()
                val quantityProduct = binding.quantityProduct.text.toString()

                if (nameProduct.isNotEmpty() && quantityProduct.isNotEmpty()){
                    val incomingProduct = Item(
                        name = nameProduct,
                        created = quantityProduct,
                        id = list_id,
                        is_crossed = false
                    )
                    Log.i("showAddProductDialog", " name incomingProduct :${incomingProduct.name}")
                    vm.addProductToList(productList, incomingProduct)
                } else{
                    Toast.makeText(APP_ACTIVITY,"Нужно заполнить все поля",Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Отменить", null)
            .create()
        dialog.show()
    }

    fun downloadListProduct() {
        CoroutineScope(Dispatchers.IO).launch {
            val listNew = RetrofitClientV2.mainApiV2.getSoppingList(list_id)
            if (listNew.isSuccessful) {
                APP_ACTIVITY.runOnUiThread {
                    val list = listNew.body()?.item_list
                    productList.clear()
                    if (list != null) {
                        productList.addAll(list)
                        adapter.notifyDataSetChanged()
                    }

                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(refreshRunnable)
    }
}




