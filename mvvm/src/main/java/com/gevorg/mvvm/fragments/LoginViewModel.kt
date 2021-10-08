package com.gevorg.mvvm.fragments

import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.gevorg.mvvm.R
import com.gevorg.mvvm.api.AuthRepositoryImpl
import com.gevorg.mvvm.base.BaseViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

class LoginViewModel @Inject constructor(var repository: AuthRepositoryImpl) : BaseViewModel() {

    val currentUser = repository.currentUser.asLiveData()

    init {
        showProgress()
        viewModelScope.launch(Dispatchers.IO) {
            delay(3000)
            withContext(Dispatchers.Main) {
                hideProgress()
                showMessage(R.string.success)
            }
        }
    }

    fun login(email: String, password: String) {
        showProgress()
        if (!validateEmail(email)) {
            showError("Input email")
            return
        }
        if (!validatePassword(password)) {
            showError("Input password")
            return
        }
        performLogin(email, password)
    }

    private fun validatePassword(password: String): Boolean {
        return password.length >= 8
    }

    private fun validateEmail(email: String): Boolean {
        return email.contains("@") && email.contains(".")
    }

    //Model implementation
    @OptIn(DelicateCoroutinesApi::class)
    private fun performLogin(email: String, password: String) {
        showProgress()
        GlobalScope.launch(Dispatchers.IO) {
            val errorMassage = repository.login(
                email = email,
                password = password
            ).await()
            withContext(Dispatchers.Main) {
                hideProgress()
                if (errorMassage.isEmpty()) {
                    showMessage(R.string.success)
                } else {
                    showError(errorMassage)
                }
            }
        }
    }
}