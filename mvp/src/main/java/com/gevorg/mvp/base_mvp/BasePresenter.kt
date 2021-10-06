package com.luseen.ayo.base_mvp

import android.os.Bundle
import androidx.fragment.app.FragmentManager

import com.gevorg.mvp.model.AuthRepositoryImpl
import com.gevorg.mvp.util.isInternetAvailable


abstract class BasePresenter<V : BaseContract.View> : BaseContract.Presenter<V>{

    protected var view: V? = null
    protected lateinit var repository: AuthRepositoryImpl
    override fun attachView(view: V) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun isViewAttached(): Boolean {
        return view != null
    }

    override fun onCreate() {
        repository = AuthRepositoryImpl()
    }

    override fun onCreate(bundle: Bundle?) {
        repository = AuthRepositoryImpl()
    }

    override fun onDestroy() {
        repository.clear()
        detachView()
    }

    override fun getFm(): FragmentManager? {
        return view?.a()?.supportFragmentManager
    }

    protected open fun hasInternetConnection(): Boolean {
        return isInternetAvailable(view?.c())
    }
}