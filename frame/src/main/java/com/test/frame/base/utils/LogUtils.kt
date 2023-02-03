package com.test.frame.base.utils

import android.util.Log

object LogUtils {
    var logOpen: Boolean = false
    private const val BASE_TAG: String = "SONG-"
    fun v(tag: String, msg: String) {
        if (logOpen) {
            Log.v(tag, msg)
        }
    }

    fun v(msg: String) {
        if (logOpen) {
            Log.v(BASE_TAG, msg)
        }
    }

    fun d(msg: String) {
        if (logOpen) {
            Log.d(BASE_TAG, msg)
        }

    }

    fun d(tag: String, msg: String) {
        if (logOpen) {
            Log.d(tag, msg)
        }

    }


    fun i(tag: String, msg: String) {
        if (logOpen) {
            Log.i(tag, msg)
        }
    }

    fun i(msg: String) {
        if (logOpen) {
            Log.i(BASE_TAG, msg)
        }
    }

    fun w(tag: String, msg: String) {
        if (logOpen) {
            Log.w(tag, msg)
        }
    }

    fun w(msg: String) {
        if (logOpen) {
            Log.w(BASE_TAG, msg)
        }
    }

    fun e(tag: String, msg: String) {
        if (logOpen) {
            var msgLeng: String = msg
            var length: Int = msgLeng.length
            val max_str_length: Int = 2001 - tag.length
            while (length > max_str_length) {
                Log.e(tag, msgLeng.substring(0, max_str_length))
                msgLeng = msgLeng.substring(max_str_length)
                length -= max_str_length;
            }
            Log.e(tag, msgLeng)
        }

    }

    fun e(msg: String) {
        if (logOpen) {
            Log.e(BASE_TAG, msg)
        }
    }

}
