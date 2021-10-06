package com.gevorg.mvc.api

import kotlinx.coroutines.Deferred

interface AuthRepository {
    suspend fun login(email: String, password: String): Deferred<String>
}