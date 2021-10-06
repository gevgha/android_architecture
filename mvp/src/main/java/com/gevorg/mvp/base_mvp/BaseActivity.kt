import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.gevorg.mvp.R
import com.gevorg.mvp.util.KeyboardUtils
import com.gevorg.mvp.util.ProgressDialog
import com.gevorg.mvp.util.toast
import com.luseen.ayo.base_mvp.BaseContract


abstract class BaseActivity<V : BaseContract.View, P : BaseContract.Presenter<V>> : AppCompatActivity(),
    BaseContract.View {

    override fun c(): Context = this
    override fun a(): AppCompatActivity = this
    open var presenter: P? = null

    abstract fun createPresenter(): P


    @Suppress("UNCHECKED_CAST")
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        presenter = createPresenter()
        presenter?.attachView(this as V)
        presenter?.onCreate(intent.extras)
        presenter?.onCreate()
    }

    override fun showProgressDialog() {
        ProgressDialog.show(c())
    }

    override fun hideProgressDialog() {
        ProgressDialog.dismiss()
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

    override fun setViewText(viewId: Int, resId: Int) {
        findViewById<TextView>(viewId)?.setText(resId)
    }

    override fun setViewTextHint(viewId: Int, resId: Int) {
        findViewById<TextView>(viewId)?.setHint(resId)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.onDestroy()
        presenter?.detachView()
        presenter = null
    }

    override fun hideKeyboard() {
        KeyboardUtils.hide(this)
    }

    override fun showErrorDialog(resId: Int, listener: DialogEventListener) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(resId)
        builder.setCancelable(false)
        builder.setPositiveButton(
            R.string.ok
        ) { _, _ -> listener.onClick() }.show()
    }

    interface DialogEventListener {
        fun onClick()
    }
}