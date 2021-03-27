package com.github.chartcoin.api.service

import com.github.chartcoin.api.model.CoinCurrencys
import retrofit2.http.GET

interface CurrencyService {
    @GET(API_GET_LATEST_CURRENCYS)
    suspend fun getBRLCurrency() : CoinCurrencys
}