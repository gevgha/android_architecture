package com.gevorg.mvvm.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.gevorg.mvvm.util.ProgressDialog
import com.gevorg.mvvm.util.toast
import dagger.android.AndroidInjection
import javax.inject.Inject

abstract class BaseActivity<VB : ViewBinding>(val bindingFactory: (LayoutInflater) -> VB) :
    AppCompatActivity() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory
    private var _binding: VB? = null
    protected val binding get() = _binding!!
    protected open lateinit var baseViewModel: BaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingFactory(layoutInflater)
        setContentView(binding.root)
        AndroidInjection.inject(this)
        attachBaseViewModel()
        observeCommand()
    }

    protected fun observeCommand() {
        baseViewModel.baseCommand.observe(this, {
            when (it) {
                is BaseViewModel.BaseCommand.ShowError -> it.text.toast(this)
                is BaseViewModel.BaseCommand.ShowMessage -> it.resId.toast(this)
                is BaseViewModel.BaseCommand.ShowProgress -> ProgressDialog.show(this)
                is BaseViewModel.BaseCommand.HideProgress -> ProgressDialog.dismiss()

            }
        })
    }

    protected fun <T : ViewModel> getViewModel(cls: Class<T>): T {
        return ViewModelProvider(this, viewModelFactory).get(cls)
    }

    protected fun attachBaseViewModel() {
        baseViewModel = getViewModel(BaseViewModel::class.java)
    }
}