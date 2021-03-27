package com.github.chartcoin.data.repository

import com.github.chartcoin.api.service.retrofit.ApiService
import com.github.chartcoin.data.dto.CoinCurrencysDto
import com.github.chartcoin.data.response.ApiResponse
import java.lang.Exception

class CurrencyRepository {
    private val currencyService = ApiService.currencyService()

    suspend fun getCurrencys(): ApiResponse<CoinCurrencysDto> {
        return try {
            val response = currencyService.getBRLCurrency()

            ApiResponse.Success(
                CoinCurrencysDto(
                    response.base,
                    response.rates
                )
            )
        } catch (e: Exception) {
            ApiResponse.Error(e.message)
        }
    }
}