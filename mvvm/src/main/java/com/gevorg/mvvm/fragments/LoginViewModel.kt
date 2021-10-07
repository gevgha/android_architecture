package com.gevorg.mvvm.fragments

import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.gevorg.mvvm.api.AuthRepositoryImpl
import com.gevorg.mvvm.base.BaseViewModel
import com.gevorg.mvvm.model.ViewState
import kotlinx.coroutines.*
import javax.inject.Inject

class LoginViewModel @Inject constructor(var repository: AuthRepositoryImpl) : BaseViewModel() {

    val currentUser = repository.currentUser.asLiveData()

    init {
        _view.value = ViewState.START_PROGRESS
        viewModelScope.launch(Dispatchers.IO) {
            delay(3000)
            withContext(Dispatchers.Main) {
                _view.value = ViewState.STOP_PROGRESS
            }
        }
    }

    fun login(email: String, password: String) {
        if (!validateEmail(email)) {
            _view.value = ViewState.SHOW_ERROR
            return
        }
        if (!validatePassword(password)) {
            _view.value = ViewState.SHOW_ERROR
            return
        }
        performLogin(email, password)
    }

    private fun validatePassword(password: String): Boolean {
        return password.length ?: 0 >= 8
    }

    private fun validateEmail(email: String): Boolean {
        return email.contains("@") && email.contains(".")
    }

    //Model implementation
    @OptIn(DelicateCoroutinesApi::class)
    private fun performLogin(email: String, password: String) {
//        view?.showProgressDialog()
        GlobalScope.launch(Dispatchers.IO) {
            val errorMassage = repository.login(
                email = email,
                password = password
            ).await()
            withContext(Dispatchers.Main) {
//                ProgressDialog.dismiss()
                if (errorMassage.isEmpty()) {
//                    view?.showMessage("Success")
                } else {
//                    view?.showError(errorMassage)
                }
            }
        }
    }
}