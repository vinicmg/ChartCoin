package com.github.chartcoin.api.service.retrofit

import com.github.chartcoin.api.service.BASE_URL_BITCOIN
import com.github.chartcoin.api.service.BASE_URL_CURRENCY
import com.github.chartcoin.api.gson.GsonDeserializer
import com.github.chartcoin.api.service.BitcoinService
import com.github.chartcoin.api.service.CurrencyService
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime

object ApiService {

    val gson =
        GsonBuilder().registerTypeAdapter(LocalDateTime::class.java, GsonDeserializer()).create()

    private val retrofitBitcoin = Retrofit.Builder()
        .baseUrl(BASE_URL_BITCOIN)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    fun bitcoinService(): BitcoinService = retrofitBitcoin.create(BitcoinService::class.java)

    private val retrofitCurrency = Retrofit.Builder()
        .baseUrl(BASE_URL_CURRENCY)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun currencyService(): CurrencyService = retrofitCurrency.create(CurrencyService::class.java)
}