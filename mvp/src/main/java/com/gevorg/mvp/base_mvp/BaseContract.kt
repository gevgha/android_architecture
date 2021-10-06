package com.luseen.ayo.base_mvp

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager

interface BaseContract {

    interface Presenter<in V : View> {
        fun attachView(view: V)
        fun detachView()
        fun onCreate()
        fun isViewAttached(): Boolean
        fun onCreate(bundle: Bundle?)
        fun onDestroy()
        fun getFm(): FragmentManager?

    }

    interface View : ProgressDialog {
        fun c(): Context?
        fun a(): AppCompatActivity?
        fun setViewError(viewId: Int, resId: Int) {

        }

        fun setViewError(viewId: Int, res: String?) {

        }

        fun setViewFocus(viewId: Int) {

        }

        fun setViewText(viewId: Int,resId: Int) {

        }
        fun setViewTextHint(viewId: Int,resId: Int) {

        }
        fun setViewText(viewId: Int, res: String) {

        }
        fun showError(error: String?)
        fun showError(stringResId: Int)
        fun showMessage(srtResId: Int)
        fun showMessage(message: String)
        fun showErrorDialog(resId: Int, listener: BaseActivity.DialogEventListener)
        fun hideKeyboard()
        fun showProgress() {

        }

        fun hideProgress() {

        }
        fun setActionBarTitle(res:Int){}
        fun setActionBarTitle(title:String){}
        fun showPanelButton() {

        }


    }
}