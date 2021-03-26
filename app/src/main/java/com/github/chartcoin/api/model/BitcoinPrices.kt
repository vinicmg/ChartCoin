package com.github.chartcoin.api.model

data class BitcoinPrices(
    val name: String,
    val unit: String,
    val values: List<Bitcoin>
)
