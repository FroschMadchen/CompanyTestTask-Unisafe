package com.example.testtackunisafe.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.NavController
import com.example.testtackunisafe.R
import com.example.testtackunisafe.RetrofitClient
import com.example.testtackunisafe.databinding.FragmentEntranceBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class EntranceFragment : Fragment() {
    private var _binding:FragmentEntranceBinding?=null
    private val mBinding get() = _binding!!
    lateinit var navController: NavController
    lateinit var button: Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEntranceBinding.inflate(layoutInflater,container,false)

        navController.navigate(R.id.action_entrance_to_createListsProducts)
        val mainApi =RetrofitClient.mainApi

        _binding!!.startBtn.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {


                }


            navController.navigate(R.id.action_entrance_to_createListsProducts)
        }
        // Inflate the layout for this fragment
        return mBinding.root
    }


}
