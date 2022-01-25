package com.suhail.vajro.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.suhail.vajro.MainActivity
import com.suhail.vajro.R
import com.suhail.vajro.adapters.ItemAdapter
import com.suhail.vajro.data.Cart
import com.suhail.vajro.data.Product
import com.suhail.vajro.databinding.FragmentProductListScreenBinding
import com.suhail.vajro.viewModels.ProductScreenViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ProductListScreen : Fragment() {
    lateinit var binding: FragmentProductListScreenBinding
    private val viewModel: ProductScreenViewModel by viewModels()
    lateinit var productAdapter: ItemAdapter
    lateinit var cartItemCount: TextView
    var tempArraylist = mutableListOf<Product>()
    var newArrayList = mutableListOf<Product>()
    lateinit var navController: NavController
    var cartItems = mutableListOf<Cart>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProductListScreenBinding.inflate(layoutInflater)
        navController = findNavController()

        (activity as MainActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)

        productAdapter = ItemAdapter()
        setHasOptionsMenu(true)
        binding.productRecyclerView.apply {
            adapter = productAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }

        viewModel.allProducts.observe(viewLifecycleOwner, { products ->
            productAdapter.differ.submitList(products.data)
            products.data?.let { newArrayList.addAll(it) }
        })

        productAdapter.setOnClickListner {
            viewModel.addItemToCart(it)
        }
        productAdapter.setOnRemoveListner {
            viewModel.removeItemFromCart(it)
        }

        productAdapter.itemClickListner {
            val action = ProductListScreenDirections.actionProductListScreenToItemDetailFragment(it)
            navController.navigate(action)
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
        val searchItem = menu.findItem(R.id.search_action)
        val searchView = searchItem?.actionView as SearchView
        val actionView = menuItem.actionView
        cartItemCount = actionView.findViewById<TextView>(R.id.itemCountNumber)

        viewModel.cartItems.observe(viewLifecycleOwner, Observer {
            cartItemCount.text = it.size.toString()
        })

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) searchDatabase(query)
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query != null) searchDatabase(query)
                return true
            }

        })

        actionView.setOnClickListener {
            onOptionsItemSelected(menuItem)
            val action = ProductListScreenDirections.actionProductListScreenToCartFragment()
            navController.navigate(action)
        }
    }

    private fun searchDatabase(query: String) {
        val searchQuery = "%$query%"
        viewModel.searchDatabase(searchQuery).observe(this, { products ->
            products.let {
                productAdapter.differ.submitList(it)
            }
        })

    }
}