package com.gevorg.mvvm.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gevorg.mvvm.model.ViewState

open class BaseViewModel : ViewModel() {

    private val _view = MutableLiveData<ViewState>()
    val view: LiveData<ViewState> = _view
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error
    private val _message = MutableLiveData<Int>()
    val message: LiveData<Int> = _message

    fun showError(error: String) {
        _error.postValue(error)
    }

    fun showMessage(resId: Int) {
        _message.value = resId
    }

    fun showProgress() {
        _view.postValue(ViewState.START_PROGRESS)
    }

    fun hideProgress() {
        _view.postValue(ViewState.STOP_PROGRESS)
    }

}