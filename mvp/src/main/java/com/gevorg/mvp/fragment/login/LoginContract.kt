package com.gevorg.mvp.fragment.login

import android.widget.Toast
import com.luseen.ayo.base_mvp.BaseContract

interface LoginContract {

    interface View : BaseContract.View {
        fun getEmail(): String
        fun getPassword(): String
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun handleLoginClick()
    }
}