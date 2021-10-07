package com.gevorg.mvvm.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gevorg.mvvm.api.AuthRepositoryImpl
import com.gevorg.mvvm.model.ErrorResult
import com.gevorg.mvvm.model.User
import com.gevorg.mvvm.model.ViewState
import com.gevorg.mvvm.observer.DataResult

open class BaseViewModel() : ViewModel() {
    val _view = MutableLiveData<ViewState>()
    val view: LiveData<ViewState> = _view
}