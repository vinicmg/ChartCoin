package com.github.chartcoin.api.service

import com.github.chartcoin.api.API_GET_MARKET_PRICE
import com.github.chartcoin.api.model.BitcoinPrices
import retrofit2.http.GET

interface BitcoinService {
    @GET(API_GET_MARKET_PRICE)
    suspend fun listBitcoinPrices() : BitcoinPrices
}