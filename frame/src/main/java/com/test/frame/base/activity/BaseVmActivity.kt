package com.test.frame.base.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.gyf.immersionbar.ImmersionBar
import com.test.frame.base.BaseViewModel
import com.test.frame.base.http.view.LoadingDialog
import com.test.frame.base.manager.ActivityManager
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType

abstract class BaseVmActivity<T : ViewBinding, VM : BaseViewModel> : AppCompatActivity() {
    private lateinit var _binding: T
    private lateinit var _viewModel: VM
    protected val binding get() = _binding
    protected val viewModel get() = _viewModel
    abstract val layoutId: Int
    private var _loadingDialog: LoadingDialog? = null
    protected val loadingDialog get() = loadingDialog()
    protected var savedInstanceBundle: Bundle? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.savedInstanceBundle = savedInstanceState
        initWindow()
        setContentView(initViewBinding())
        setBarStatus()
        ActivityManager.instance().addActivity(this)
        _viewModel = initVmModel() as VM
        initView()
        initData()
        startObserve()
    }

    protected abstract fun initVmModel(): BaseViewModel
    protected abstract fun initView()
    protected abstract fun initData()
    protected abstract fun startObserve()


    protected open fun initWindow() {}


    private fun loadingDialog(): LoadingDialog {
        if (_loadingDialog == null) {
            _loadingDialog = LoadingDialog(this)
        }
        return _loadingDialog as LoadingDialog
    }

    private fun initViewBinding(): View {
        val superclass = javaClass.genericSuperclass
        val aClass = (superclass as ParameterizedType).actualTypeArguments[0] as Class<*>
        try {
            val method: Method = aClass.getDeclaredMethod("inflate", LayoutInflater::class.java)
            _binding = method.invoke(null, layoutInflater) as T
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }
        return _binding.root
    }

    protected fun setBarStatus(isDarkFont: Boolean = true) {
        ImmersionBar.with(this)
            .statusBarDarkFont(isDarkFont)//状态栏字体是深色，不写默认为亮色
            .fitsSystemWindows(false)//解决状态栏和布局重叠问题，任选其一，默认为false，当为true时一定要指定statusBarColor()，不然状态栏为透明色，还有一些重载方法
            .init()
    }

    override fun onDestroy() {
        ActivityManager.instance().removeActivity(this)
        super.onDestroy()
    }

}