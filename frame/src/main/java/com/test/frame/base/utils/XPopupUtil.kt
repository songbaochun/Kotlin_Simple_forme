package com.test.frame.base.utils

import android.content.Context
import android.view.View
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView
import com.lxj.xpopup.interfaces.XPopupCallback
import com.test.frame.base.listencallback.XPopupCallBack


object XPopupUtil {
    /**
     * 展示popup 关闭自动销毁
     */
    fun showPopup(context: Context, basePopupView: BasePopupView?) {
        if (basePopupView != null) {
            XPopup.Builder(context).asCustom(basePopupView).show()
        }
    }

    fun showPopup(
        context: Context,
        basePopupView: BasePopupView?,
        dismiss: () -> Unit,
        show: () -> Unit
    ) {
        if (basePopupView != null) {
            XPopup.Builder(context).setPopupCallback(object : XPopupCallBack() {
                override fun onPopupShow(popupView: BasePopupView?) {
                    show()
                }

                override fun onPopupDismiss(popupView: BasePopupView?) {
                    dismiss()
                }
            }).asCustom(basePopupView).show()
        }
    }

    fun showPopup(
        context: Context,
        basePopupView: BasePopupView?,
        objects: XPopupCallback,
        isTouch: Boolean = true,
        isBack: Boolean = false
    ) {
        if (basePopupView != null) {
            XPopup.Builder(context)
                .setPopupCallback(objects)
                .dismissOnTouchOutside(isTouch)
                .dismissOnBackPressed(isBack)
                .asCustom(basePopupView)
                .show()
        }
    }

    fun showPopup(
        context: Context,
        basePopupView: BasePopupView?,
        view: View,
        objects: XPopupCallback,
        isTouch: Boolean = true,
        isBack: Boolean = false
    ) {
        if (basePopupView != null) {
            XPopup.Builder(context)
                .atView(view)
                .setPopupCallback(objects)
                .dismissOnTouchOutside(isTouch)
                .dismissOnBackPressed(isBack)
                .asCustom(basePopupView)
                .show()
        }
    }

}