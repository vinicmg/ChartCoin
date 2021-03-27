package com.github.chartcoin.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.chartcoin.data.dto.BitcoinPricesDto
import com.github.chartcoin.data.dto.CoinCurrencysDto
import com.github.chartcoin.data.repository.BitcoinRepository
import com.github.chartcoin.data.repository.CurrencyRepository
import com.github.chartcoin.data.response.ApiResponse
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val repositoryBitcoin = BitcoinRepository()
    private val repositoryCurrency = CurrencyRepository()

    val prices = MutableLiveData<ApiResponse<BitcoinPricesDto>>()
    val coins = MutableLiveData<ApiResponse<CoinCurrencysDto>>()

    fun getPrices() {
        viewModelScope.launch {
            val result = repositoryBitcoin.getPrices()
            prices.postValue(result)
        }
    }

    fun getCurrencys() {
        viewModelScope.launch {
            val result = repositoryCurrency.getCurrencys()
            coins.postValue(result)
        }
    }
}