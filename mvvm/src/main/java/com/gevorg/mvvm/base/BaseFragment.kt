package com.gevorg.mvvm.base

import android.os.Bundle
import android.view.View
import androidx.annotation.ContentView
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gevorg.mvvm.activities.MainActivity
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class BaseFragment : Fragment {

    constructor() : super()

    @ContentView
    constructor(@LayoutRes contentLayoutId: Int) : super(contentLayoutId)

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory

    protected fun <T : ViewModel> getViewModel(cls: Class<T>): T {
        return ViewModelProvider(this, viewModelFactory).get(cls)
    }

    protected fun <T : ViewModel> getSharedViewModel(cls: Class<T>): T {
        return ViewModelProvider(requireActivity(), viewModelFactory).get(cls)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onFragmentViewCreated(view, savedInstanceState)
    }

    open fun onFragmentViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {

    }

    fun getMainActivity(): MainActivity? {
        if (activity is MainActivity){
            return activity as MainActivity
        }
        return null
    }
}