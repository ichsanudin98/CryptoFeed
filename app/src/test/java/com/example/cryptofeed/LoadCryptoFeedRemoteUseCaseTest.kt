package com.example.cryptofeed

import app.cash.turbine.test
import com.example.cryptofeed.api.BadRequest
import com.example.cryptofeed.api.BadRequestException
import com.example.cryptofeed.api.Connectivity
import com.example.cryptofeed.api.ConnectivityException
import com.example.cryptofeed.api.HttpClient
import com.example.cryptofeed.api.InternalServerError
import com.example.cryptofeed.api.InternalServerErrorException
import com.example.cryptofeed.api.InvalidData
import com.example.cryptofeed.api.InvalidDataException
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
        } returns flowOf(ConnectivityException())

        sut.load().test {
            assertEquals(Connectivity::class.java, awaitItem()::class.java)
            awaitComplete()
        }

        verify(exactly = 1) {
            client.get()
        }

        confirmVerified(client)
    }

    @Test
    fun testLoadDeliversInvalidDataError() = runBlocking {
        every {
            client.get()
        } returns flowOf(InvalidDataException())

        sut.load().test {
            assertEquals(InvalidData::class.java, awaitItem()::class.java)
            awaitComplete()
        }

        verify(exactly = 1) {
            client.get()
        }

        confirmVerified(client)
    }

    @Test
    fun testLoadDeliversBadRequestError() = runBlocking {
        every {
            client.get()
        } returns flowOf(BadRequestException())

        sut.load().test {
            assertEquals(BadRequest::class.java, awaitItem()::class.java)
            awaitComplete()
        }

        verify(exactly = 1) {
            client.get()
        }

        confirmVerified(client)
    }

    @Test
    fun testLoadDeliversInternalServerError() = runBlocking {
        every {
            client.get()
        } returns flowOf(InternalServerErrorException())

        sut.load().test {
            assertEquals(InternalServerError::class.java, awaitItem()::class.java)
            awaitComplete()
        }

        verify(exactly = 1) {
            client.get()
        }

        confirmVerified(client)
    }
}