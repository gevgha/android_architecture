package com.gevorg.mvvm.api

import kotlinx.coroutines.*

class AuthRepositoryImpl : AuthRepository {


    override suspend fun login(email: String, password: String): Deferred<String> {
        return GlobalScope.async {
            Thread.sleep(300)
            ""
        }
    }
}