package com.github.chartcoin.api.service.retrofit

import com.github.chartcoin.api.BASE_URL
import com.github.chartcoin.api.service.BitcoinService
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiServiceRetrofit {

    val gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    fun bitcoinService(): BitcoinService = retrofit.create(BitcoinService::class.java)
}