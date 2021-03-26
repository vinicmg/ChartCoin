package com.github.chartcoin.ui.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.chartcoin.data.dto.BitcoinPricesDto
import com.github.chartcoin.data.repository.BitcoinRepository
import com.github.chartcoin.data.response.ApiResult
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val repository = BitcoinRepository()

    val prices = MutableLiveData<ApiResult<BitcoinPricesDto>>()

    fun getPrices() {
        viewModelScope.launch {
            val result = repository.getPrices()
            prices.postValue(result)
        }
    }
}