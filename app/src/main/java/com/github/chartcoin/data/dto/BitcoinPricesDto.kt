package com.github.chartcoin.data.dto

import com.github.chartcoin.api.model.Bitcoin

data class BitcoinPricesDto(
    val name: String,
    val unit: String,
    val values: List<Bitcoin>
)
