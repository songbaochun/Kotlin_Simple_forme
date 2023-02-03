package com.test.frame.base.utils

import android.app.ActivityManager
import android.content.Context
import android.os.Process

object MachineUtils {
    fun isMainProcess(context: Context?): Boolean {
        return context != null && context.packageName == getProcessName(context)
    }

    private fun getProcessName(context: Context): String? {
        return try {
            val myPid = Process.myPid()
            val runningAppProcesses =
                (context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager).runningAppProcesses
                    ?: return null
            for (next in runningAppProcesses) {
                if (next.pid == myPid) {
                    return next.processName
                }
            }
            null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}