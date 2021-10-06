import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.gevorg.mvp.BuildConfig
import com.gevorg.mvp.R
import com.gevorg.mvp.util.KeyboardUtils
import com.gevorg.mvp.util.ProgressDialog
import com.gevorg.mvp.util.toast
import com.luseen.ayo.base_mvp.BaseContract

abstract class BaseFragment<V : BaseContract.View, P : BaseContract.Presenter<V>, VB : ViewBinding>(
    val bindingFactory: (LayoutInflater) -> VB
) :
    Fragment(), BaseContract.View {

    override fun c(): Context? = context
    override fun a(): AppCompatActivity = activity as AppCompatActivity
    protected var presenter: P? = null
    abstract fun createPresenter(): P
    private var _binding: VB? = null
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingFactory(layoutInflater)
        return binding.root
    }

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
        val handler = Handler()
        handler.postDelayed({
            view?.findViewById<TextView>(viewId)?.requestFocus()
        }, 100)
    }

    override fun setViewText(viewId: Int, resId: Int) {
        view?.findViewById<TextView>(viewId)?.setText(resId)
    }

    override fun setViewText(viewId: Int, res: String) {
        view?.findViewById<TextView>(viewId)?.text = res
    }

    override fun setViewTextHint(viewId: Int, resId: Int) {
        view?.findViewById<TextView>(viewId)?.setHint(resId)
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
        _binding = null
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

    protected open fun openSettings() {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts(
            "package",
            BuildConfig.APPLICATION_ID, null
        )
        intent.data = uri
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }


}