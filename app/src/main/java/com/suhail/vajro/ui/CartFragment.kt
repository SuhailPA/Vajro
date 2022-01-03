package com.suhail.vajro.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.suhail.vajro.R
import com.suhail.vajro.adapters.CartAdapter
import com.suhail.vajro.databinding.FragmentCartBinding
import com.suhail.vajro.viewModels.CartScreenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {

    lateinit var binding : FragmentCartBinding
    lateinit var cartAdapter : CartAdapter
    val viewModel : CartScreenViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(layoutInflater)
        cartAdapter = CartAdapter()
        viewModel.cartItems.observe(viewLifecycleOwner, Observer {
            cartAdapter.differ.submitList(it)
        })

        binding.cartRecyclerView.apply {
            adapter = cartAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        return binding.root
    }

}