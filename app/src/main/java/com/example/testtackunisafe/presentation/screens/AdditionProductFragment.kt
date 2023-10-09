package com.example.testtackunisafe.presentation.screens

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testtackunisafe.data.utils.APP_ACTIVITY
import com.example.testtackunisafe.databinding.FragmentAdditionProductBinding
import com.example.testtackunisafe.domain.custom_type.Product
import com.example.testtackunisafe.domain.custom_type.ShopListConstructor
import com.example.testtackunisafe.presentation.adapter.SpecificProductListAdapter
import com.example.testtackunisafe.presentation.viewmodel.CreateProductVM


class AdditionProductFragment : Fragment() {

    private var _binding:FragmentAdditionProductBinding? = null
    private val mBinding get() = _binding!!

    private lateinit var adapter: SpecificProductListAdapter
    private var productList = ArrayList<Product>()
    private lateinit var vm: CreateProductVM



    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdditionProductBinding.inflate(layoutInflater, container, false)

        val keyText = arguments?.getSerializable("keyValue",ShopListConstructor::class.java)
//        mBinding.textView.setText(keyText?.name)
        if (keyText != null) {
            var createProduct = createProductList(keyText)
        }

        adapter = SpecificProductListAdapter(productList)
        mBinding.recyclerViewProduct.layoutManager = LinearLayoutManager(APP_ACTIVITY)
        mBinding.recyclerViewProduct.adapter = adapter

        vm = ViewModelProvider(this).get(CreateProductVM::class.java)


        return mBinding.root



    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private  fun createProductList(keyText:ShopListConstructor){
            productList = keyText.list
            val nameProduct = mBinding.editTextAppProduct.text.toString()
            vm.addToShoppingList(productList)
            productList.add(Product("",nameProduct,0,3))

        }

    }


