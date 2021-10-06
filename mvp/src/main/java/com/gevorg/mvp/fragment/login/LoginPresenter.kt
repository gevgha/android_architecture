package com.gevorg.mvp.fragment.login

import com.gevorg.mvp.util.ProgressDialog
import com.luseen.ayo.base_mvp.BasePresenter
import kotlinx.coroutines.*

class LoginPresenter : BasePresenter<LoginContract.View>(), LoginContract.Presenter {

    override fun handleLoginClick() {
        if (!validateEmail()) {
            view?.showError("Input email")
            return
        }
        if (!validatePassword()) {
            view?.showError("Input password")
            return
        }
        performLogin()
    }

    private fun validatePassword(): Boolean {
        return view?.getPassword()?.length ?: 0 >= 8
    }

    private fun validateEmail(): Boolean {
        val email = view?.getEmail() ?: ""
        return email.contains("@") && email.contains(".")
    }

    //Model implementation
    @OptIn(DelicateCoroutinesApi::class)
    private fun performLogin() {
        view?.showProgressDialog()
        GlobalScope.launch(Dispatchers.IO) {
            val errorMassage = repository.login(
                email = view?.getEmail() ?: "",
                password = view?.getPassword() ?: ""
            ).await()
            withContext(Dispatchers.Main) {
                ProgressDialog.dismiss()
                if (errorMassage.isEmpty()) {
                    view?.showMessage("Success")
                } else {
                    view?.showError(errorMassage)
                }
            }
        }
    }

}