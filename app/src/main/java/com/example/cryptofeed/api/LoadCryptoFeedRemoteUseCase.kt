package com.example.cryptofeed.api

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface HttpClient {
    fun get(): Flow<Exception>
}

class ConnectivityException: Exception()
class InvalidDataException: Exception()
class BadRequestException: Exception()
class InternalServerErrorException: Exception()

class LoadCryptoFeedRemoteUseCase constructor(
    private val client: HttpClient
)  {
    fun load(): Flow<Exception> = flow {
        client.get().collect { error ->
            when(error) {
                is ConnectivityException -> {
                    emit(Connectivity())
                }
                is InvalidDataException -> {
                    emit(InvalidData())
                }
                is BadRequestException -> {
                    emit(BadRequest())
                }
                is InternalServerErrorException -> {
                    emit(InternalServerError())
                }
                else -> {
                    emit(Connectivity())
                }
            }
        }
    }
}

class Connectivity: Exception()
class InvalidData: Exception()
class BadRequest: Exception()
class InternalServerError: Exception()