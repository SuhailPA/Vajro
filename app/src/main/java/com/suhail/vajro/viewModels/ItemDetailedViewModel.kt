package com.suhail.vajro.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suhail.vajro.data.Cart
import com.suhail.vajro.data.Product
import com.suhail.vajro.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemDetailedViewModel @Inject constructor(
    private val stateHandle: SavedStateHandle,
    private val repository: ProductRepository
) : ViewModel() {

    var imageUrl = MutableLiveData<String>()
    var productName = MutableLiveData<String>()
    var productPrice = MutableLiveData<String>()

    val cartItems = MutableLiveData<List<Cart>>()

    init {
        initializeItems()
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            repository.getAllItemsFromCart()
                .distinctUntilChanged()
                .collect {
                    it.let {
                        cartItems.value = it
                    }
                }

        }

    }

    private fun initializeItems() {
        val item = stateHandle.get<Product>("itemDetails")
        imageUrl.value = item?.image
        productName.value = item?.name
        productPrice.value = item?.price
    }
}