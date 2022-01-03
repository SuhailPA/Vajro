package com.suhail.vajro.viewModels

import androidx.lifecycle.*
import com.suhail.vajro.data.Cart
import com.suhail.vajro.data.Product
import com.suhail.vajro.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductScreenViewModel @Inject constructor(
    private val repository: ProductRepository
): ViewModel() {

    val allProducts = repository.allProductItems().asLiveData()

    fun addItemToCart(item:Product){
        var quantity : Int = 1
        viewModelScope.launch {
            val currentQuantity = repository.exists(item.id.toInt())
            if (currentQuantity != null){
                repository.updateQuantity(currentQuantity.plus(1),item.id.toInt())
            }else{
                val cartItem = Cart(item.image,item.name,item.price,quantity,item.productId)
                repository.addToCart(cartItem)
            }

        }
    }

    fun removeItemFromCart(item:Product){

        viewModelScope.launch {
            val currentQuantity = repository.exists(item.id.toInt())
            if (currentQuantity != null){
                repository.updateQuantity(currentQuantity.minus(1),item.id.toInt())
            }

        }
    }

}