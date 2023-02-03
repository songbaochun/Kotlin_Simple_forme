package com.test.frame.base.utils

import android.text.TextUtils
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import java.util.*
import java.util.regex.Pattern

object StringUtils {
    /**
     * 字符串 是否是数字
     *
     * @param str
     * @return
     */
    fun isNumeric(str: String?): Boolean {
        if (TextUtils.isEmpty(str)) {
            return false
        }
        val pattern = Pattern.compile("-?[0-9]+(.[0-9]+)?")
        val isNum = pattern.matcher(str.toString())
        return isNum.matches()
    }

    /**
     * 字符串去重
     * @param str 目标字符串
     * @return 去重后的字符串
     */
    fun removeRepeatChar(str: String): String {
        val a = arrayOfNulls<String>(str.length)
        for (i in str.indices) {
            val s = str.substring(i, i + 1)
            a[i] = s
        }
        val m: MutableMap<String?, Int> = TreeMap() //Map有序集合
        for (i in a.indices) {
            if (m.containsKey(a[i])) {
                m[a[i]] = m[a[i]]!! + 1
            } else {
                m[a[i]] = 1
            }
        }
        var result = ""
        val ite: Iterator<*> = m.keys.iterator()
        while (ite.hasNext()) {
            result += ite.next() as String
        }
        return result
    }

    //自动生成名字（中文）
    fun getRandomChineseCharacters(length: Int): String? {
        var ret: String? = ""
        for (i in 0 until length) {
            var str: String? = null
            var hightPos: Int
            var lowPos: Int // 定义高低位
            val random = Random()
            hightPos = 176 + Math.abs(random.nextInt(39)) // 获取高位值
            lowPos = 161 + Math.abs(random.nextInt(93)) // 获取低位值
            val b = ByteArray(2)
            b[0] = hightPos.toByte()
            b[1] = lowPos.toByte()
            try {
                str = String(b, Charset.forName("GBK")) // 转成中文
            } catch (ex: UnsupportedEncodingException) {
                ex.printStackTrace()
            }
            ret += str
        }
        return ret
    }

    /**
     *  生成随机用户名，数字和字母组成,
     */
    fun getRandomCharacters(length: Int): String {
        var str = ""
        val random = Random()
        //参数length，表示生成几位随机数
        for (i in 0 until length) {
            val charOrNum = if (random.nextInt(2) % 2 == 0) "char" else "num"
            //输出字母还是数字
            if ("char".equals(charOrNum, ignoreCase = true)) {
                //输出是大写字母还是小写字母
                val temp = if (random.nextInt(2) % 2 == 0) 65 else 97
                str += (random.nextInt(26) + temp).toChar()
            } else if ("num".equals(charOrNum, ignoreCase = true)) {
                str += random.nextInt(10).toString()
            }
        }
        return str
    }


    /**
     * 对字符串处理:将指定位置到指定位置的字符以星号代替
     *
     * @param content
     * 传入的字符串
     * @param begin
     * 开始位置
     * @param end
     * 结束位置
     * @return
     */
    fun getStarString(content: String, begin: Int, end: Int): String {
        if (begin >= content.length || begin < 0) {
            return content
        }
        if (end >= content.length || end < 0) {
            return content
        }
        if (begin >= end) {
            return content
        }
        var starStr = ""
        for (i in begin until end) {
            starStr = "$starStr*"
        }
        return content.substring(0, begin) + starStr + content.substring(end, content.length)
    }

    /**
     * 对字符加星号处理：除前面几位和后面几位外，其他的字符以星号代替
     * @param content
     * 传入的字符串
     * @param frontNum
     * 保留前面字符的位数
     * @param endNum
     * 保留后面字符的位数
     * @return 带星号的字符串
     */
    fun getEncryptionString(content: String, frontNum: Int, endNum: Int): String {
        if (frontNum >= content.length || frontNum < 0) {
            return content
        }
        if (endNum >= content.length || endNum < 0) {
            return content
        }
        if (frontNum + endNum >= content.length) {
            return content
        }
        var starStr = ""
        for (i in 0 until content.length - frontNum - endNum) {
            starStr = "$starStr*"
        }
        return (content.substring(0, frontNum) + starStr
                + content.substring(content.length - endNum, content.length))
    }

    /**
     * 判断是否为一个RUL
     */
    fun isURL(str: String): Boolean {
        //设置正则表达式
        val regex =
            "^(https?:\\/\\/)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w \\.-]*)*\\/?\$".toRegex()
        return regex.matches(str)
    }

    /**
     * 判断是否为一个邮箱地址
     */
    fun isEmail(str: String): Boolean {
        val pattern = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"
        return Regex(pattern).matches(str)
    }

    /**
     *是否为电话号码
     */
    fun isPhoneNumber(str: String): Boolean {
        val pattern = "^(1[3-9])\\d{9}\$"
        return Regex(pattern).matches(str)
    }

    fun isContains(str: String, contains: String): Boolean {
        return str.contains(contains)
    }


}