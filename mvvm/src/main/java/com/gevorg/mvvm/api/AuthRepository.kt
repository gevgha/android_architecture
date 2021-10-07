package com.gevorg.mvvm.api

import com.gevorg.mvvm.model.User
import kotlinx.coroutines.Deferred

interface AuthRepository {
    suspend fun login(email: String, password: String): Deferred<String>
    suspend fun saveCurrentUser(user: User)
}