package com.suhail.vajro.viewModels

import androidx.lifecycle.*
import com.suhail.vajro.data.Cart
import com.suhail.vajro.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartScreenViewModel @Inject constructor(
    private val repository: ProductRepository
): ViewModel() {

    val cartItems = MutableLiveData<List<Cart>>()

    init {
        loadData()
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
}


