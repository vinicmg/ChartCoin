package com.github.chartcoin.data.dto

import com.github.chartcoin.api.model.Coin

data class CoinCurrencysDto(
    val base: String,
    val rates: Coin
)
