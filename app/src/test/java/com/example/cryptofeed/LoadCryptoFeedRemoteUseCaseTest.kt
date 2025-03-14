package com.example.cryptofeed

import org.junit.Assert.assertTrue
import org.junit.Test

// TDD membantu kita dalam
// 1. getting fast feedback
// 2. kita tau apa yang kita expect

// sut = system under test

class LoadCryptoFeedRemoteUseCase {

}

class HttpClient {
    var count = 0
}

class LoadCryptoFeedRemoteUseCaseTest {
    @Test
    fun testInitDoesNotLoad() {
        val client = HttpClient()
        LoadCryptoFeedRemoteUseCase()
        assertTrue(client.count == 0)
    }
}