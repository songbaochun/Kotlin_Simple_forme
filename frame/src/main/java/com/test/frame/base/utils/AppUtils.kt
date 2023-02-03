package com.test.frame.base.utils

import android.content.Context
import java.io.File


fun getAppVersionName(context: Context): String {
    val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
    return packageInfo.versionName
}

fun clearCache(context: Context) {
    val cacheDirectory = context.cacheDir
    val applicationDirectory = File(cacheDirectory.parent)
    if (applicationDirectory.exists()) {
        val fileNames = applicationDirectory.list()
        for (fileName in fileNames) {
            if (fileName != "lib") {
                deleteFile(File(applicationDirectory, fileName))
            }
        }
    }
}

fun deleteFile(file: File) {
    if (file.isDirectory) {
        val children = file.list()
        for (child in children) {
            deleteFile(File(file, child))
        }
    } else {
        file.delete()
    }
}
