package com.test.frame.base.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.View


fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

/**
 * 复制剪切板
 */
fun copy(context: Context, label: String, msg: String) {
    val clip = ClipData.newPlainText(label, msg)
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    clipboard.setPrimaryClip(clip);
}

private val records: MutableMap<String, Long> = HashMap()

fun isFastClick(time: Long): Boolean {
    if (records.size > 1000) {
        records.clear()
    }
    val stackTraceElement = Throwable().stackTrace[1]
    val key = stackTraceElement.fileName + stackTraceElement.lineNumber
    var recordTime = records[key]
    if (recordTime == null) {
        recordTime = 0L
    }
    val currentTimeMillis = System.currentTimeMillis()
    records[key] = currentTimeMillis
    val ratioTime = currentTimeMillis - recordTime
    return ratioTime in 1 until time
}