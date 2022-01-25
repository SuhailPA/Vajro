package com.suhail.vajro.viewModels

import android.util.Log
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
    var item: Product = stateHandle.get<Product>("itemDetails")!!

    var productQuantity = MutableLiveData<Int>()
    val cartItems = MutableLiveData<List<Cart>>()

    init {
        initializeItems()
        getProductQuantity()
        loadData()
    }

    private fun getProductQuantity() {
        val itemId = item.productId
        viewModelScope.launch {
            productQuantity.value = repository.exists(itemId.toInt())?: 0
        }

    }

    fun removeItemFromCart() {
        productQuantity.value = productQuantity.value?.minus(1)
        viewModelScope.launch {
            val currentQuantity = repository.exists(item.productId.toInt())
            if (currentQuantity != null && currentQuantity == 1) {
                repository.deleteFromCart(item.productId.toInt())
            } else if (currentQuantity != null) {
                repository.updateQuantity(currentQuantity.minus(1), item.productId.toInt())
            }

        }
    }

    fun addItemToCart() {
        var quantity: Int = 1
        productQuantity.value = productQuantity.value?.plus(1)
        viewModelScope.launch {
            val currentQuantity = repository.exists(item.productId.toInt())
            if (currentQuantity != null) {
                Log.i("working", currentQuantity.toString())
                repository.updateQuantity(currentQuantity.plus(1), item.productId.toInt())
            } else {
                val cartItem = Cart(item.image, item.name, item.price, quantity, item.productId)
                repository.addToCart(cartItem)
            }

        }
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

        imageUrl.value = item.image
        productName.value = item.name
        productPrice.value = item.price
    }
}