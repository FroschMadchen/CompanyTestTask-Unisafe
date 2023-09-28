package com.example.testtackunisafe.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import com.example.testtackunisafe.R
import com.example.testtackunisafe.RetrofitClient
import com.example.testtackunisafe.databinding.FragmentAdditionProductBinding
import com.example.testtackunisafe.databinding.FragmentCreateListsProductsBinding


class CreateListsProductsFragment : Fragment() {
    private var _binding:FragmentCreateListsProductsBinding? = null
    private val mBinding get() = _binding!!
    lateinit var navController: NavController
    lateinit var additionProductBinding: FragmentAdditionProductBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateListsProductsBinding.inflate(layoutInflater, container, false)
        navController.navigate(R.id.action_createListsProducts_to_additionProduct)
        val mainApi = RetrofitClient.mainApi

        return mBinding.root
    }
}
