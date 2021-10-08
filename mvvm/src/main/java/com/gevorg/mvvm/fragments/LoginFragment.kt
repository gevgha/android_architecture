package com.gevorg.mvvm.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gevorg.mvvm.R
import com.gevorg.mvvm.base.BaseFragment
import com.gevorg.mvvm.databinding.FragmentLoginBinding
import com.gevorg.mvvm.util.toText
import com.gevorg.mvvm.util.toast

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private lateinit var viewModel: LoginViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = getViewModel(LoginViewModel::class.java)
        baseViewModel = viewModel
        observeCommand()
        viewModel.currentUser.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.textEmail.setText(it.email)
                binding.textPassword.setText(it.password)
            }
        }
         binding.loginBtn.setOnClickListener {
             viewModel.login(binding.textEmail.toText(),binding.textPassword.toText())
         }
    }
}