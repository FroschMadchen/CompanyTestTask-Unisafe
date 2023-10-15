package com.example.testtackunisafe.presentation.screens

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testtackunisafe.data.utils.APP_ACTIVITY
import com.example.testtackunisafe.databinding.DialogAddProductBinding
import com.example.testtackunisafe.databinding.FragmentAdditionProductBinding
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

        val list_item =arguments?.getSerializable("item_list",ProductListData::class.java)
        val listID = arguments?.getInt("id_list")
        if (list_item!= null && listID != null){
                list_id = listID
            productList = list_item.item_list.toMutableList()
            Log.i("BUNDEL f3","success get productList:${list_item.success}")
            (activity as AppCompatActivity).supportActionBar?.title = listID.toString()
        }

        /*val keyText = arguments?.getSerializable("keyValue", ShopListConstructor::class.java)
        if (keyText != null) {
            list_id = keyText.listId
            productList = keyText.list
            (activity as AppCompatActivity).supportActionBar?.title = keyText.name

                Log.i("mainUI"," ID СПИСКА ${list_id}")
        }*/

        adapter = SpecificProductListAdapter(object : ActionListenerProduct{
            override fun deleteProduct(id: Int) {
               val selectedItem = productList.indexOfFirst { it.id == id }
                if(selectedItem != -1){
                    val item =productList[selectedItem]
                    item.is_crossed = true
                    CoroutineScope(Dispatchers.IO).launch {
                        val crossltOff = vm.crossltOff(id,list_id)
                        item.is_crossed = crossltOff
                    }
                    Log.i("deleteProduct mainUI","item: $selectedItem")
                    adapter.notifyDataSetChanged()
                }

            }

            override fun editProduct(item_id: Int) {
                TODO("Not yet implemented")
            }

        },productList)
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
                val incomingProduct = Item(
                    name = nameProduct,
                    created = quantityProduct,
                    id = list_id,
                    is_crossed = false
                )
                Log.i("showAddProductDialog"," name incomingProduct :${incomingProduct.name}")
                vm.addProductToList(productList, incomingProduct)
            }
            .setNegativeButton("Отменить",null)
            .create()
        dialog.show()
    }
}


