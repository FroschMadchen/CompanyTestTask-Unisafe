package com.example.testtackunisafe.presentation.screens

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.testtackunisafe.R
import com.example.testtackunisafe.databinding.FragmentAdditionProductBinding
import com.example.testtackunisafe.domain.custom_type.ShopListConstructor




class AdditionProductFragment : Fragment() {

    private var _binding:FragmentAdditionProductBinding? = null
    private val mBinding get() = _binding
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAdditionProductBinding.inflate(layoutInflater, container, false)

        val keyText = arguments?.getSerializable("keyValue",ShopListConstructor::class.java)

            mBinding?.textView?.setText(keyText?.name)



        return mBinding?.root



    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}