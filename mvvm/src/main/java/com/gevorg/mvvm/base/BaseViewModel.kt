package com.gevorg.mvvm.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class  BaseViewModel : ViewModel() {

    val baseCommand = MutableLiveData<BaseCommand>()

    fun showError(error: String) {
        baseCommand.value = BaseCommand.ShowError(error)
    }

    fun showMessage(resId: Int) {
        baseCommand.value = BaseCommand.ShowMessage(resId)
    }

    fun showProgress(){
        baseCommand.value = BaseCommand.ShowProgress
    }

    fun hideProgress(){
        baseCommand.value = BaseCommand.HideProgress
    }

    sealed class BaseCommand {
        data class ShowError(val text: String) : BaseCommand()
        data class ShowMessage(val resId: Int) : BaseCommand()
        object ShowProgress : BaseCommand()
        object HideProgress : BaseCommand()
    }
}