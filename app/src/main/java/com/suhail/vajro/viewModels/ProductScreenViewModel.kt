package com.suhail.vajro.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.suhail.vajro.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductScreenViewModel @Inject constructor(
    val repository: ProductRepository
): ViewModel() {
    val allProducts = repository.allProductItems().asLiveData()
}