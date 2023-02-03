package com.test.frame.base.utils

import android.content.Context
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object ImageUtils {
    fun saveBitmap(bm: Bitmap?, mContext: Context?, isAlbums: Boolean): Boolean {
        if (mContext == null) {
            return false
        }
        if (bm == null) {
            return false
        }
        var mkdirs = true
        val dataDirectory = Environment.getExternalStorageDirectory()
        val dir = File(dataDirectory.absolutePath + "/" + mContext.packageName + "/Image/")
        if (!dir.exists()) {
            mkdirs = dir.mkdirs()
        }
        return if (mkdirs) {
            val saveFile: File = FileUtils.createFile(
                dir.absolutePath, "code" + TimeUtils.Companion.getTimeLong().toString() + ".png"
            )
            try {
                val saveImgOut = FileOutputStream(saveFile)
                // compress - 压缩的意思
                bm.compress(Bitmap.CompressFormat.JPEG, 80, saveImgOut)
                //存储完成后需要清除相关的进程
                saveImgOut.flush()
                saveImgOut.close()
                if (isAlbums) {
                    MediaScannerConnection.scanFile(
                        mContext, arrayOf(saveFile.absolutePath),
                        null, null
                    )
//                    mContext.sendBroadcast(
//                        Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + saveFile.absolutePath))
//                    )
                }
                true
            } catch (ex: IOException) {
                ex.printStackTrace()
                false
            }
        } else {
            false
        }
    }

}