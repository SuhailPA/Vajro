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
import coil.load
import com.suhail.vajro.MainActivity
import com.suhail.vajro.R
import com.suhail.vajro.databinding.FragmentItemDetailBinding
import com.suhail.vajro.viewModels.ItemDetailedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ItemDetailFragment : Fragment() {
    lateinit var binding : FragmentItemDetailBinding
    val viewModel : ItemDetailedViewModel by viewModels()
    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentItemDetailBinding.inflate(layoutInflater)

        navController = findNavController()
        viewModel.imageUrl.observe(viewLifecycleOwner, Observer {
            binding.productImage.load(it){
                placeholder(R.drawable.ic_baseline_shopping_cart_24)
            }
        })

        binding.addButtonInDetailedPage.setOnClickListener {

        }

        (activity as MainActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.productName.observe(viewLifecycleOwner, Observer {
            binding.productName.text = it
        })

        viewModel.productPrice.observe(viewLifecycleOwner, Observer {
            binding.productPrice.text = it
        })
        setHasOptionsMenu(true)
        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_toolbar, menu)
        var menuItem = menu.findItem(R.id.cartItems)
        val searchItem = menu.findItem(R.id.search_action)
        searchItem.isVisible = false
        val actionView = menuItem.actionView
        val cartItemCount = actionView.findViewById<TextView>(R.id.itemCountNumber)
        viewModel.cartItems.observe(viewLifecycleOwner, {
            cartItemCount.text = it.size.toString()
        })
        actionView.setOnClickListener {
            onOptionsItemSelected(menuItem)
            val action = ProductListScreenDirections.actionProductListScreenToCartFragment()
            navController.navigate(action)

        }
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