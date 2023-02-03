package com.test.frame.base.fragment

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType


abstract class BaseFragment<T : ViewBinding> : Fragment() {
    protected lateinit var mActivity: Activity
    protected lateinit var binding: T
    abstract val layoutId: Int
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return initViewBinding(inflater)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mActivity = requireActivity()
        initView()
        initData()
        super.onViewCreated(view, savedInstanceState)
    }

    protected abstract fun initView()
    protected abstract fun initData()

    private fun initViewBinding(layoutInflater: LayoutInflater): View {
        val superclass = javaClass.genericSuperclass
        val aClass = (superclass as ParameterizedType).actualTypeArguments[0] as Class<*>
        try {
            val method: Method = aClass.getDeclaredMethod("inflate", LayoutInflater::class.java)
            binding = method.invoke(null, layoutInflater) as T
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }
        return binding.root
    }

}
