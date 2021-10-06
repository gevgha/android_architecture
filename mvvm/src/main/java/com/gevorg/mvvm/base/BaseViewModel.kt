package com.gevorg.mvvm.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gevorg.mvvm.model.ErrorResult

open class BaseViewModel : ViewModel() {
    protected val _errorLiveData = MutableLiveData<ErrorResult>()
    val errorLiveData: LiveData<ErrorResult> = _errorLiveData
}