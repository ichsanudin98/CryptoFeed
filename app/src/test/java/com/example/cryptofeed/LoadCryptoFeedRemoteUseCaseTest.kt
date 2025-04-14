package com.example.cryptofeed

import app.cash.turbine.test
import com.example.cryptofeed.api.Connectivity
import com.example.cryptofeed.api.HttpClient
import com.example.cryptofeed.api.LoadCryptoFeedRemoteUseCase
import io.mockk.MockKAnnotations
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class LoadCryptoFeedRemoteUseCaseTest {
    private val client = spyk<HttpClient>()
    private lateinit var sut: LoadCryptoFeedRemoteUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        sut = LoadCryptoFeedRemoteUseCase(client)
    }

    @Test
    fun testInitDoesNotRequestData() {
        verify(exactly = 0) {
            client.get()
        }

        confirmVerified(client)
    }

    @Test
    fun testLoadRequestsData() = runBlocking {
        every {
            client.get()
        } returns flowOf()

        sut.load().test {
            awaitComplete()
        }

        verify(exactly = 1) {
            client.get()
        }

        confirmVerified(client)
    }

    @Test
    fun testLoadTwiceRequestsDataTwice() = runBlocking {
        every {
            client.get()
        } returns flowOf()

        sut.load().test {
            awaitComplete()
        }
        sut.load().test {
            awaitComplete()
        }
        verify(exactly = 2) {
            client.get()
        }

        confirmVerified(client)
    }

    @Test
    fun testLoadDeliversErrorOnClientError() = runBlocking {
        every {
            client.get()
        } returns flowOf(Connectivity())

        sut.load().test {
            assertEquals(Connectivity::class.java, awaitItem()::class.java)
            awaitComplete()
        }

        verify(exactly = 1) {
            client.get()
        }

        confirmVerified(client)
    }
}