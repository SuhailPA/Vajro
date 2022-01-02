package com.suhail.vajro.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.suhail.vajro.R
import com.suhail.vajro.databinding.FragmentProductListScreenBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductListScreen : Fragment() {
    lateinit var binding : FragmentProductListScreenBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProductListScreenBinding.inflate(layoutInflater)


        return binding.root
    }

}