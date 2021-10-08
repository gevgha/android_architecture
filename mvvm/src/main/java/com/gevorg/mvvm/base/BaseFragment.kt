package com.gevorg.mvvm.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ContentView
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.gevorg.mvvm.activities.MainActivity
import com.gevorg.mvvm.fragments.LoginViewModel
import com.gevorg.mvvm.model.ViewState
import com.gevorg.mvvm.util.ProgressDialog
import com.gevorg.mvvm.util.toast
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class BaseFragment<VB : ViewBinding>(val bindingFactory: (LayoutInflater) -> VB) :
    Fragment() {

    private lateinit var viewModel: BaseViewModel

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory
    private var _binding: VB? = null
    protected val binding get() = _binding!!

    protected fun <T : ViewModel> getViewModel(cls: Class<T>): T {
        return ViewModelProvider(this, viewModelFactory).get(cls)
    }

    protected fun <T : ViewModel> getSharedViewModel(cls: Class<T>): T {
        return ViewModelProvider(requireActivity(), viewModelFactory).get(cls)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        _binding = bindingFactory(layoutInflater)
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onFragmentViewCreated(view, savedInstanceState)
    }

    open fun onFragmentViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        viewModel = getViewModel(BaseViewModel::class.java)
        viewModel.view.observe(viewLifecycleOwner) {
            when (it) {
                ViewState.START_PROGRESS -> ProgressDialog.show(requireContext())
                ViewState.STOP_PROGRESS -> ProgressDialog.dismiss()
            }
        }
        viewModel.error.observe(viewLifecycleOwner,  { message ->
            message.toast(requireContext())
        })
        viewModel.message.observe(viewLifecycleOwner, { message ->
            message.toast(requireContext())
        })
    }

    fun getMainActivity(): MainActivity? {
        if (activity is MainActivity) {
            return activity as MainActivity
        }
        return null
    }
}