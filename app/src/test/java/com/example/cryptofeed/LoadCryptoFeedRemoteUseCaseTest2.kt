package com.example.cryptofeed

import org.junit.Assert
import org.junit.Test

class LoadCryptoRemoteUseCase {

}

class HttpClients {
    val getCount = 0
}

class LoadCryptoFeedRemoteUseCaseTest2 {
    @Test
    fun testInit() {
        val client = HttpClients()
        val sut = LoadCryptoRemoteUseCase()

        Assert.assertTrue(client.getCount == 0)
    }
}