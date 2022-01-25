package com.suhail.vajro.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.suhail.vajro.MainActivity
import com.suhail.vajro.adapters.CartAdapter
import com.suhail.vajro.databinding.FragmentCartBinding
import com.suhail.vajro.viewModels.CartScreenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {

    lateinit var binding: FragmentCartBinding
    lateinit var cartAdapter: CartAdapter
    lateinit var navController : NavController
    val viewModel: CartScreenViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(layoutInflater)
        navController = findNavController()
        (activity as MainActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        cartAdapter = CartAdapter()
        viewModel.cartItems.observe(viewLifecycleOwner, Observer {
            cartAdapter.differ.submitList(it)
        })

        setHasOptionsMenu(true)
        binding.cartRecyclerView.apply {
            adapter = cartAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home ->{
                navController.navigateUp()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}