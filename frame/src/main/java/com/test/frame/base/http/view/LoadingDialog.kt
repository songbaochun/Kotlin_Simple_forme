package com.test.frame.base.http.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.test.frame.R

class LoadingDialog(context: Context) : Dialog(context, R.style.frameLoadingDialog) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setCancelable(true)
        setCanceledOnTouchOutside(false)
        setContentView( R.layout.frame_dialog_loading)
    }


}