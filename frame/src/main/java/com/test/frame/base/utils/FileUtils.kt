package com.test.frame.base.utils

import java.io.File
import java.io.IOException

object FileUtils {
    fun createFile(path: String, fileName: String): File {
        val file = File(path + File.separator + fileName)
        if (file.exists()) {
            return file
        } else {
            try {
                file.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return file
    }
}