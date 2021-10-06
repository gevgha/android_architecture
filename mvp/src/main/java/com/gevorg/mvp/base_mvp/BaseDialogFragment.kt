
import android.content.Context
import android.content.DialogInterface

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.gevorg.mvp.R
import com.gevorg.mvp.util.KeyboardUtils
import com.gevorg.mvp.util.ProgressDialog
import com.gevorg.mvp.util.toast
import com.luseen.ayo.base_mvp.BaseContract


abstract class BaseDialogFragment<V : BaseContract.View, P : BaseContract.Presenter<V>> :
    DialogFragment(), BaseContract.View {

    override fun c(): Context? = context
    override fun a(): AppCompatActivity = activity as AppCompatActivity
    protected  var presenter: P? = null

    abstract fun createPresenter(): P

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = createPresenter()
        presenter?.attachView(this as V)
        presenter?.onCreate()
        presenter?.onCreate(arguments)
    }

    override fun showProgressDialog() {
        c()?.let {
            ProgressDialog.show(it)
        }
    }

    override fun hideProgressDialog() {
        ProgressDialog.dismiss()
    }

    override fun setViewError(viewId: Int, resId: Int) {
        view?.findViewById<TextView>(viewId)?.setError(getString(resId))
        view?.findViewById<TextView>(viewId)?.requestFocus()
    }

    override fun setViewError(viewId: Int, res: String?) {
        view?.findViewById<TextView>(viewId)?.setError(res)
        view?.findViewById<TextView>(viewId)?.requestFocus()
    }

    override fun setViewText(viewId: Int, resId: Int) {
        view?.findViewById<TextView>(viewId)?.setText(resId)
    }

    override fun setViewText(viewId: Int, res: String) {
        view?.findViewById<TextView>(viewId)?.text = res
    }

    override fun showError(error: String?) {
        error?.toast(c())
    }

    override fun showError(stringResId: Int) {
        stringResId.toast(c())
    }

    override fun showMessage(srtResId: Int) {
        srtResId.toast(c())
    }

    override fun showMessage(message: String) {
        message.toast(c())
    }

    override fun hideKeyboard() {
        KeyboardUtils.hide(a())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter?.onDestroy()
        presenter?.detachView()
        presenter = null
    }

    override fun showErrorDialog(resId: Int, listener: BaseActivity.DialogEventListener) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage(resId)
        builder.setCancelable(false)
        builder.setPositiveButton(R.string.ok, object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                listener.onClick()
            }
        }).show()
    }

}