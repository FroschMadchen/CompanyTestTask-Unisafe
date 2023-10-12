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
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testtackunisafe.data.utils.APP_ACTIVITY
import com.example.testtackunisafe.databinding.DialogAddProductBinding
import com.example.testtackunisafe.databinding.FragmentAdditionProductBinding
import com.example.testtackunisafe.domain.model.Product
import com.example.testtackunisafe.domain.model.ShopListConstructor
import com.example.testtackunisafe.presentation.adapter.ActionListenerProduct
import com.example.testtackunisafe.presentation.adapter.SpecificProductListAdapter
import com.example.testtackunisafe.presentation.viewmodel.CreateProductVM


class AdditionProductFragment : Fragment() {

    private var _binding: FragmentAdditionProductBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var binding: DialogAddProductBinding

    private lateinit var adapter: SpecificProductListAdapter
    private var productList = ArrayList<Product>()
    private lateinit var vm: CreateProductVM

    private var list_id: Int = 0

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

        val keyText = arguments?.getSerializable("keyValue", ShopListConstructor::class.java)
        if (keyText != null) {
            list_id = keyText.listId
            productList = keyText.list

            Log.i("mainUI"," ID СПИСКА ${list_id}")
        }

        adapter = SpecificProductListAdapter(object : ActionListenerProduct{
            override fun deleteProduct(item_id: Int) {
                val selectedItem = productList.indexOfFirst { it.item_id == item_id }
                if(selectedItem != -1){
                    val item =productList[selectedItem]
                    item.crossedOut = true
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
                Log.i("liveData", "observe :${it.name},${it.item_id}")
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
                val incomingProduct = Product(
                    name = nameProduct,
                    quantity = quantityProduct.toInt(),
                    list_id = list_id
                )
                Log.i("showAddProductDialog"," name incomingProduct :${incomingProduct.name}")
                vm.addProductToList(productList, incomingProduct)
            }
            .setNegativeButton("Отменить",null)
            .create()
        dialog.show()
    }
}


