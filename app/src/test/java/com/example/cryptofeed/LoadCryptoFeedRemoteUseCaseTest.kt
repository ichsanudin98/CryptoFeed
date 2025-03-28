package com.example.cryptofeed

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

// TDD membantu kita dalam
// 1. getting fast feedback
// 2. kita tau apa yang kita expect

// sut = system under test

class LoadCryptoFeedRemoteUseCase constructor(
    private val client: HttpClient
)  {
    fun load() {
        client.get()
    }
}

// singleton below
// Syarat kelas singleton ada 2
// private dan mutable
/*class HttpClient private constructor() {
    companion object {
        val instance = HttpClient()
    }
    var count = 0
}*/

// Global instance
// Jika global ini sudah dipakai dibanyak tempat, maka menjadi global mutable shared instance
open class HttpClient {
    companion object {
        var instance = HttpClient()
    }

    open fun get() {

    }
}

class HttpClientSpy: HttpClient() {
    var getCount = 0

    override fun get() {
        super.get()
        getCount++
    }
}

/*class HttpClient {
    var count = 0
}*/

class LoadCryptoFeedRemoteUseCaseTest {
    @Test
    fun testInitDoesNotLoad() {
        val client = HttpClientSpy()
        HttpClient.instance = client
        LoadCryptoFeedRemoteUseCase(client)
        assertTrue(client.getCount == 0)
    }

    @Test
    fun testLoadRequestData() {
        // Given (Component and dependencies)
        val client = HttpClientSpy()
        HttpClient.instance = client
        val sut = LoadCryptoFeedRemoteUseCase(client)

        // When (Action)
        sut.load()

        // Then
        assertEquals(1, client.getCount)
    }
}