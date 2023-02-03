package com.test.frame.base.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.gyf.immersionbar.ImmersionBar
import com.test.frame.base.manager.ActivityManager
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType

abstract class BaseActivity<T : ViewBinding> : AppCompatActivity() {
    private lateinit var _binding: T
    protected val binding get() = _binding
    abstract val layoutId: Int
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(initViewBinding())
        ActivityManager.instance().addActivity(this)
        initView()
        initData()
    }

    abstract fun initView()
    abstract fun initData()

    protected fun setBarStatus(isDarkFont: Boolean = true) {
        ImmersionBar.with(this)
            .statusBarDarkFont(isDarkFont)//状态栏字体是深色，不写默认为亮色
            .fitsSystemWindows(false)//解决状态栏和布局重叠问题，任选其一，默认为false，当为true时一定要指定statusBarColor()，不然状态栏为透明色，还有一些重载方法
            .init()
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

    override fun onDestroy() {
        super.onDestroy()
        ActivityManager.instance().removeActivity(this)
    }
}