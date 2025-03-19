package com.example.cryptofeed

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

// TDD membantu kita dalam
// 1. getting fast feedback
// 2. kita tau apa yang kita expect

// sut = system under test

class LoadCryptoFeedRemoteUseCase {
    fun load() {
        HttpClient.instance.count = 1
    }
}

// singleton below
class HttpClient private constructor() {
    companion object {
        val instance = HttpClient()
    }
    var count = 0
}

/*class HttpClient {
    var count = 0
}*/

class LoadCryptoFeedRemoteUseCaseTest {
    @Test
    fun testInitDoesNotLoad() {
        val client = HttpClient.instance
        LoadCryptoFeedRemoteUseCase()
        assertTrue(client.count == 0)
    }

    @Test
    fun testLoadRequestData() {
        // Given (Component and dependencies)
        val client = HttpClient.instance
        val sut = LoadCryptoFeedRemoteUseCase()

        // When (Action)
        sut.load()

        // Then
        assertEquals(1, client.count)
    }
}