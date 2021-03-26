package com.github.chartcoin.data.repository

import com.github.chartcoin.api.service.retrofit.ApiServiceRetrofit
import com.github.chartcoin.data.dto.BitcoinPricesDto
import com.github.chartcoin.data.response.ApiResult
import java.lang.Exception

class BitcoinRepository {
    private val bitcoinService = ApiServiceRetrofit.bitcoinService()

    suspend fun getPrices(): ApiResult<BitcoinPricesDto> {
        return try {
            val response = bitcoinService.listBitcoinPrices()

            ApiResult.Success(
                BitcoinPricesDto(
                    response.name,
                    response.unit,
                    response.values
                )
            )
        } catch (e: Exception) {
            ApiResult.Error(e.message)
        }
    }
}