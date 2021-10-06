package com.gevorg.mvp.fragment.login

import BaseFragment
import android.os.Bundle
import android.view.View
import com.gevorg.mvp.databinding.FragmentLoginBinding
import com.gevorg.mvp.util.toText

class LoginFragment : BaseFragment<LoginContract.View, LoginContract.Presenter, FragmentLoginBinding>(FragmentLoginBinding::inflate),
    LoginContract.View {

    override fun createPresenter(): LoginContract.Presenter {
        return LoginPresenter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginBtn.setOnClickListener {
            presenter?.handleLoginClick()
        }
    }

    override fun getEmail(): String {
        return binding.textEmail.toText()
    }

    override fun getPassword(): String {
        return binding.textPassword.toText()
    }


}