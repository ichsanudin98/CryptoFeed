package com.example.cryptofeed.domain

data class CryptoFeed(
    val coinInfo: CoinInfo,
    val raw: Raw
)

data class CoinInfo(
    val id: String,
    val name: String,
    val fullName: String
)

/** Raw bisa dikatakan aggregate jika lebih dari 1 object properties nya */
data class Raw(
    val usd: Usd
)

data class Usd(
    val price: Double,
    val changePctDay: Long
)