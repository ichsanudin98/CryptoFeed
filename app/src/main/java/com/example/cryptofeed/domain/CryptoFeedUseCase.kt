package com.example.cryptofeed.domain

import kotlinx.coroutines.flow.Flow

sealed class CryptoFeedResult {
    data class Success(val cryptoFeedItems: List<CryptoFeed>): CryptoFeedResult()
    data class Error(val ex: Exception): CryptoFeedResult()
}

interface CryptoFeedUseCase {
    fun load(): Flow<CryptoFeedResult>
}