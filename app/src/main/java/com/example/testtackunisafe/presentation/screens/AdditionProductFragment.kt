package com.example.testtackunisafe.presentation.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.testtackunisafe.R
import com.example.testtackunisafe.databinding.FragmentAdditionProductBinding


class AdditionProductFragment : Fragment() {

    private var _binding:FragmentAdditionProductBinding? = null
    private val mBinding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAdditionProductBinding.inflate(layoutInflater, container, false)




        return mBinding?.root



    }

}