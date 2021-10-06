package com.gevorg.mvp.model

import kotlinx.coroutines.*

class AuthRepositoryImpl : AuthRepository {


    override suspend fun login(email: String, password: String): Deferred<String> {
        return GlobalScope.async {
            Thread.sleep(300)
            ""
        }
    }

    fun clear(){

    }
}