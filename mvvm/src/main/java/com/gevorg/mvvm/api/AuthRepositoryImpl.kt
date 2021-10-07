package com.gevorg.mvvm.api

import android.content.SharedPreferences
import androidx.core.content.edit
import com.gevorg.mvvm.model.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val gson: Gson,
    private val sharedPreferences: SharedPreferences
) :
    AuthRepository {

    companion object {
        const val CURRENT_USER_PREF_KEY = "CURRENT_USER_PREF_KEY"
    }

    private val _currentUser = MutableStateFlow(getSavedCurrentUser())
    val currentUser: StateFlow<User?> = _currentUser

    override suspend fun saveCurrentUser(user: User) {
        val courtString = gson.toJson(user)
        sharedPreferences.edit {
            putString(CURRENT_USER_PREF_KEY, courtString)
        }
        _currentUser.emit(user)
    }

    fun getSavedCurrentUser(): User? {
        val string = sharedPreferences.getString(CURRENT_USER_PREF_KEY, "")
        return try {
            val type = object : TypeToken<User?>() {}.type
            gson.fromJson(string, type)
        } catch (e: Exception) {
            Timber.e(e)
            null
        }
    }

    override suspend fun login(email: String, password: String): Deferred<String> {
        return GlobalScope.async {
            Thread.sleep(300)
            ""
        }
    }
}