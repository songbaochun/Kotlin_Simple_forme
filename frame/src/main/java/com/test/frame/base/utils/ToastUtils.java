package com.test.frame.base.utils;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.test.frame.base.app.CommonApplication;


/**
 * 可在任意线程执行本类方法
 */
public class ToastUtils {
    private static final Handler mHandler = new Handler(Looper.getMainLooper());
    private static Toast mToast;

    public static void show(int msgResId) {
        show(msgResId, false);
    }


    public static void show(int msgResId, boolean timeLong) {
        show(CommonApplication.Companion.instance().getApplication().getString(msgResId), timeLong);
    }

    public static void show(CharSequence msg) {
        show(msg, false);
    }

    public static void show(Object msg) {
        show(msg.toString(), false);
    }

    public static void showBoolean(CharSequence tMsg, CharSequence fMsg, boolean condition) {
        if (condition) {
            show(tMsg, false);
        } else {
            show(fMsg, false);
        }
    }

    public static void show(final CharSequence msg, final boolean timeLong) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mToast != null) {
                    mToast.cancel();
                }
                int duration = timeLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT;
                mToast = Toast.makeText(CommonApplication.Companion.instance().getApplication(), msg, duration);
                mToast.setText(msg);
                mToast.show();
            }
        });
    }

    private static void runOnUiThread(Runnable runnable) {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            runnable.run();
        } else {
            mHandler.post(runnable);
        }
    }
}
