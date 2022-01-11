package com.suhail.vajro.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.suhail.vajro.R
import com.suhail.vajro.adapters.ItemAdapter
import com.suhail.vajro.data.Cart
import com.suhail.vajro.databinding.FragmentProductListScreenBinding
import com.suhail.vajro.viewModels.ProductScreenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductListScreen : Fragment() {
    lateinit var binding: FragmentProductListScreenBinding
    private val viewModel: ProductScreenViewModel by viewModels()
    lateinit var productAdapter: ItemAdapter
    lateinit var cartItemCount: TextView
    lateinit var navController: NavController
    var cartItems = mutableListOf<Cart>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProductListScreenBinding.inflate(layoutInflater)
        navController = findNavController()
        productAdapter = ItemAdapter()
        setHasOptionsMenu(true)
        binding.productRecyclerView.apply {
            adapter = productAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }

        viewModel.allProducts.observe(viewLifecycleOwner, { products ->
            productAdapter.differ.submitList(products.data)
        })

        productAdapter.setOnClickListner {
            viewModel.addItemToCart(it)
        }
        productAdapter.setOnRemoveListner {
            viewModel.removeItemFromCart(it)
        }

        viewModel.cartItems.observe(viewLifecycleOwner, Observer {
            productAdapter.cart = it
        })



        return binding.root


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        Log.i("menuItem", "working")
        inflater.inflate(R.menu.menu_toolbar, menu)
        var menuItem = menu.findItem(R.id.cartItems)
        val actionView = menuItem.actionView
        cartItemCount = actionView.findViewById<TextView>(R.id.itemCountNumber)
        viewModel.cartItems.observe(viewLifecycleOwner, Observer {
            cartItemCount.text = it.size.toString()
        })
        actionView.setOnClickListener {
            onOptionsItemSelected(menuItem)
            val action = ProductListScreenDirections.actionProductListScreenToCartFragment()
            navController.navigate(action)

        }
    }
}