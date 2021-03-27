package com.github.chartcoin.api.service.retrofit

import com.github.chartcoin.api.BASE_URL
import com.github.chartcoin.api.gson.GsonDeserializer
import com.github.chartcoin.api.service.BitcoinService
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime
import java.util.*

object ApiServiceRetrofit {

    val gson = GsonBuilder().registerTypeAdapter(LocalDateTime::class.java, GsonDeserializer()).create()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    fun bitcoinService(): BitcoinService = retrofit.create(BitcoinService::class.java)
}