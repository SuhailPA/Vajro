package com.suhail.vajro.viewModels

import android.util.Log
import androidx.lifecycle.*
import com.suhail.vajro.data.Cart
import com.suhail.vajro.data.Product
import com.suhail.vajro.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductScreenViewModel @Inject constructor(
    private val repository: ProductRepository
): ViewModel() {

    val allProducts = repository.allProductItems().asLiveData()

    val cartItems = MutableLiveData<List<Cart>>()

    init {
        loadData()
    }

    fun addItemToCart(item:Product){
        var quantity : Int = 1
        viewModelScope.launch {
            Log.i("working","Enter")
            val currentQuantity = repository.exists(item.productId.toInt())
            if (currentQuantity != null){
                Log.i("working", currentQuantity.toString())
                repository.updateQuantity(currentQuantity.plus(1),item.productId.toInt())
            }else{
                val cartItem = Cart(item.image,item.name,item.price,quantity,item.productId)
                repository.addToCart(cartItem)
            }

        }
    }

    private fun loadData() {
        viewModelScope.launch {
            repository.getAllItemsFromCart()
                .distinctUntilChanged()
                .collect {
                    it?.let {
                        cartItems.value = it
                    }
                }

        }

    }

    fun removeItemFromCart(item:Product){

        viewModelScope.launch {
            val currentQuantity = repository.exists(item.productId.toInt())
            if (currentQuantity != null && currentQuantity == 1){
                repository.deleteFromCart(item.productId.toInt())

            }
            else if (currentQuantity !=null ){
                repository.updateQuantity(currentQuantity.minus(1),item.productId.toInt())
            }

        }
    }

}