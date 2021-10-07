package com.gevorg.mvvm.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import dagger.android.AndroidInjection
import javax.inject.Inject

abstract class BaseActivity<VB : ViewBinding>(val bindingFactory: (LayoutInflater) -> VB) : AppCompatActivity()  {

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory
    private var _binding: VB? = null
    protected val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingFactory(layoutInflater)
        setContentView(binding.root)
        AndroidInjection.inject(this)
    }

    protected fun <T : ViewModel> getViewModel(cls: Class<T>): T {
        return ViewModelProvider(this, viewModelFactory).get(cls)
    }

}