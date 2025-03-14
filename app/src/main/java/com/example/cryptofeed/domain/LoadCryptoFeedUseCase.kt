package com.example.cryptofeed.domain

import kotlinx.coroutines.flow.Flow

sealed class LoadCryptoFeedResult {
    data class Success(val cryptoFeedItems: List<CryptoFeed>): LoadCryptoFeedResult()
    data class Error(val ex: Exception): LoadCryptoFeedResult()
}

interface LoadCryptoFeedUseCase {
    fun load(): Flow<LoadCryptoFeedResult>
}