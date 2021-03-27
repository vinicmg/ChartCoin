package com.github.chartcoin.data.repository

import com.github.chartcoin.api.service.retrofit.ApiService
import com.github.chartcoin.data.dto.BitcoinPricesDto
import com.github.chartcoin.data.response.ApiResponse
import java.lang.Exception

class BitcoinRepository {
    private val bitcoinService = ApiService.bitcoinService()

    suspend fun getPrices(): ApiResponse<BitcoinPricesDto> {
        return try {
            val response = bitcoinService.listBitcoinPrices()

            ApiResponse.Success(
                BitcoinPricesDto(
                    response.name,
                    response.unit,
                    response.values
                )
            )
        } catch (e: Exception) {
            ApiResponse.Error(e.message)
        }
    }
}