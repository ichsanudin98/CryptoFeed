package com.example.cryptofeed

import com.example.cryptofeed.api.HttpClient
import com.example.cryptofeed.api.LoadCryptoFeedRemoteUseCase
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class LoadCryptoFeedRemoteUseCaseTest {
    @Test
    fun testInitDoesNotRequestData() {
        val (_, client) = makeSut()
        assertTrue(client.getCount == 0)
    }

    @Test
    fun testLoadRequestsData() {
        val (sut, client) = makeSut()
        sut.load()
        assertEquals(1, client.getCount)
    }

    @Test
    fun testLoadTwiceRequestsDataTwice() {
        val (sut, client) = makeSut()
        sut.load()
        sut.load()
        assertEquals(2, client.getCount)
    }

    private fun makeSut(): Pair<LoadCryptoFeedRemoteUseCase, HttpClientSpy> {
        val client = HttpClientSpy()
        val sut = LoadCryptoFeedRemoteUseCase(client = client)
        return Pair(sut, client)
    }

    private class HttpClientSpy: HttpClient {
        var getCount = 0

        override fun get() {
            super.get()
            getCount++
        }
    }
}