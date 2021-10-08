package com.gevorg.mvvm.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.gevorg.mvvm.model.ViewState
import com.gevorg.mvvm.util.ProgressDialog
import com.gevorg.mvvm.util.toast
import dagger.android.AndroidInjection
import javax.inject.Inject

abstract class BaseActivity<VB : ViewBinding>(val bindingFactory: (LayoutInflater) -> VB) : AppCompatActivity()  {

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory
    private var _binding: VB? = null
    protected val binding get() = _binding!!
    private lateinit var viewModel: BaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingFactory(layoutInflater)
        setContentView(binding.root)
        AndroidInjection.inject(this)
        viewModel = getViewModel(BaseViewModel::class.java)
        viewModel.view.observe(this) {
            when (it) {
                ViewState.START_PROGRESS -> ProgressDialog.show(this)
                ViewState.STOP_PROGRESS -> ProgressDialog.dismiss()
            }
        }
        viewModel.error.observe(this,  { message ->
            message.toast(this)
        })
        viewModel.message.observe(this, { message ->
            message.toast(this)
        })
    }

    protected fun <T : ViewModel> getViewModel(cls: Class<T>): T {
        return ViewModelProvider(this, viewModelFactory).get(cls)
    }

}