package com.gevorg.mvc.fragment.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.gevorg.mvc.R
import com.gevorg.mvc.databinding.ActivityMainBinding
import com.gevorg.mvc.databinding.FragmentLoginBinding
import com.gevorg.mvc.model.AuthRepositoryImpl
import com.gevorg.mvc.toLength
import com.gevorg.mvc.toText
import com.gevorg.mvc.util.ProgressDialog
import kotlinx.coroutines.*


class LoginFragment : Fragment() {
    companion object {

        @JvmStatic
        fun newInstance() =
            LoginFragment()
    }

    lateinit var binding: FragmentLoginBinding
    private val authRepository = AuthRepositoryImpl()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater)

        binding.loginBtn.setOnClickListener {
            if (!validateEmail()) {
                Toast.makeText(requireContext(), "Input required fields", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            if (!validatePassword()) {
                Toast.makeText(requireContext(), "Input required fields", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            performLogin()
        }
        return binding.root
    }

    private fun validatePassword(): Boolean {
        return binding.textPassword.toLength() >= 8
    }

    private fun validateEmail(): Boolean {
        return binding.textEmail.toText().contains("@") && binding.textEmail.toText()
            .contains(".")
    }

    //Model implementation
    @OptIn(DelicateCoroutinesApi::class)
    private fun performLogin() {
        ProgressDialog.show(requireContext())
        GlobalScope.launch(Dispatchers.IO) {
            val errorMassage = authRepository.login(
                email = binding.textEmail.toText(),
                password = binding.textPassword.toText()
            ).await()
            withContext(Dispatchers.Main) {
                ProgressDialog.dismiss()
                if (errorMassage.isEmpty()) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), errorMassage, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

}