package com.example.cryptofeed.api

interface HttpClient {
    fun get() {

    }
}

class LoadCryptoFeedRemoteUseCase constructor(
    private val client: HttpClient
)  {
    fun load() = client.get()
}