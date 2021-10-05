package com.gevorg.mvc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.gevorg.mvc.databinding.ActivityMainBinding
import com.gevorg.myapplication.AuthRepositoryImpl
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val authRepository = AuthRepositoryImpl()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginBtn.setOnClickListener {
            if (!validateEmail()) {
                Toast.makeText(this@MainActivity, "Input required fields", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            if (!validatePassword()) {
                Toast.makeText(this@MainActivity, "Input required fields", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            performLogin()
        }
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
        ProgressDialog.show(this)
        GlobalScope.launch(Dispatchers.IO) {
            val errorMassage = authRepository.login(
                email = binding.textEmail.toText(),
                password = binding.textPassword.toText()
            ).await()
            launch(Dispatchers.Main) {
                ProgressDialog.dismiss()
                if (errorMassage.isEmpty()) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@MainActivity, "Success", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@MainActivity, errorMassage, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}