package com.test.frame.base.utils

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class TimeUtils {

    companion object {
        private val dateFormat = SimpleDateFormat("yyyy年MM月dd日")
        private val mCalendar = Calendar.getInstance()
        private val weekStrings = arrayOf("日", "一", "二", "三", "四", "五", "六")
        private val rWeekStrings = arrayOf("周日", "周一", "周二", "周三", "周四", "周五", "周六")


        fun secondToTime2(timeSeconds: Long): String? {
            val day = timeSeconds / (60 * 60 * 24)
            val hour = (timeSeconds - day * (60 * 60 * 24)) / (60 * 60)
            val minute = (timeSeconds - day * (60 * 60 * 24) - hour * (60 * 60)) / 60
            val builder = java.lang.StringBuilder()
            if (day > 0) {
                builder.append(day).append("天")
            }
            if (hour > 0) {
                builder.append(hour).append("小时")
            }
            if (minute > 0) {
                builder.append(minute).append("分钟")
            }
            if (minute <= 0) {
                builder.append("1分钟")
            }
            return builder.toString()
        }

        /**
         * 当天最晚时间
         */
        fun getNow23HTimeLong(): Long {
            val calendar = Calendar.getInstance()
            calendar[Calendar.HOUR_OF_DAY] = 23
            calendar[Calendar.SECOND] = 59
            calendar[Calendar.MINUTE] = 59
            calendar[Calendar.MILLISECOND] = 999
            return calendar.timeInMillis
        }

        /**
         * 获取当前时间戳
         *
         * @return
         */
        fun getTimeLong(): Long {
            val time = System.currentTimeMillis()  //获取系统时间的10位的时间戳
            val str = time.toString()
            return str.toLong()
        }

        /**
         * 获取当天日期
         *
         * @return
         */
        fun getTodayDate(): String? {
            val date = Date(System.currentTimeMillis())
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            return sdf.format(date)
        }

        /**
         * 改变日期格式
         * @param date  2017年02月09日
         * @return 2017-02-09
         */
        fun changeFormatDate(date: String): String? {
            val dFormat = SimpleDateFormat("yyyy-MM-dd")
            var curDate: String? = null
            try {
                val dt = dateFormat.parse(date)
                curDate = dFormat.format(dt)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return curDate
        }

        /**
         * 判断日期是否与当前日期差7天
         * @param date  2020年04月22日
         * @return true
         */
        fun isDateOutDate(date: String): Boolean {
            try {
                if ((Date().time - dateFormat.parse(date).time) > 7 * 24 * 60 * 60 * 1000) return true

            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return false
        }

        /**
         * 返回当前的时间
         * @return  今天 09:48
         */
        private fun getCurTime(): String {
            val dFormat = SimpleDateFormat("HH:mm")
            return dFormat.format(System.currentTimeMillis())
        }

        /**
         * 获取运动记录是周几，今天则返回具体时间，其他则返回具体周几
         * @param dateStr
         * @return
         */
        fun getWeekStr(dateStr: String): String {

            val todayStr = dateFormat.format(mCalendar.time)

            if (todayStr == dateStr) {
                return getCurTime()
            }

            val preCalendar = Calendar.getInstance()
            preCalendar.add(Calendar.DATE, -1)
            val yesterdayStr = dateFormat.format(preCalendar.time)
            if (yesterdayStr == dateStr) {
                return "昨天"
            }

            var w = 0
            try {
                val date = dateFormat.parse(dateStr)
                val calendar = Calendar.getInstance()
                calendar.time = date
                w = calendar.get(Calendar.DAY_OF_WEEK) - 1
                if (w < 0) {
                    w = 0
                }

            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return rWeekStrings[w]
        }

        // 根据 星期几到获取与 星期一 相差几天
        private fun getHowManyDayFromMonday(someDay: String): Int {
            var day = 0;
            when (someDay) {
                "星期一" -> day = 0
                "星期二" -> day = 1
                "星期三" -> day = 2
                "星期四" -> day = 3
                "星期五" -> day = 4
                "星期六" -> day = 5;
                "星期日" -> day = 6;
                else -> LogUtils.e("不存在这样的星期几 : $someDay")
            }
            return day;
        }

        private fun getWhatDaySomeDateMillis(someDataStr: String?): Long {
            var date = Date()
            val format = SimpleDateFormat("yyyy-MM-dd")
            try {
                date = format.parse(someDataStr)
                return date.time
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return -1
        }

        // 获取指定任意日期当周的一星期的日期
        fun getSomedayWeekAllDate(): List<String> {
            // 指定天的毫秒时间
            val currentTimeMillis = getWhatDaySomeDateMillis("2019-10-15")
            return getAllWeekDayDateByMillis(currentTimeMillis)
        }

        // 获取指定日期毫秒时间的当周一星期的日期
        private fun getAllWeekDayDateByMillis(timeMills: Long): List<String> {
            val list = ArrayList<String>()
            // 得到指定时间是周几
            val week = getWhatDaySomeday(timeMills)
            LogUtils.e("日期是$week")
            // 记录与周一的间隔天数
            val dayFromMonday = getHowManyDayFromMonday(week);
            LogUtils.e("dayFromMonday : $dayFromMonday")
            // 获取这周第一天毫秒值
            val dayMillis = 24 * 60 * 60 * 1000;
            // 获取这周第一天的日子
            val firstOfWeekMillis = timeMills - dayFromMonday * dayMillis;
            // 使用 for 循环得到当前一周的日子（7天的日子）
            run {
                var i = firstOfWeekMillis
                while (i < firstOfWeekMillis + 7 * dayMillis) {
                    val targetDate = Date(i)
                    val format = SimpleDateFormat("yyyy-MM-dd")
                    val targetDay = format.format(targetDate)
                    list.add(targetDay)
                    i += dayMillis
                }
            }
            return list;
        }

        // 获取指定日期毫秒时间得到 星期几
        private fun getWhatDaySomeday(timeMillis: Long): String {
            val toDayDate = Date(timeMillis);
            val formatE = SimpleDateFormat("E")
            var week = ""
            try {
                week = formatE.format(toDayDate)
                LogUtils.e("WEEK$week")
            } catch (e: Exception) {
                e.printStackTrace();
            }
            return week;
        }


        fun getCurrentWeekAllDay(): List<String> {
            val weekList: List<String>
            val currentTimeMillis = System.currentTimeMillis()
            weekList = getAllWeekDayDateByMillis(currentTimeMillis)
            return weekList
        }


        /**
         * 获取是几号
         *
         * @return dd
         */
        public fun getCurrentDay(): Int {
            return mCalendar.get(Calendar.DATE)
        }

        /**
         * 获取当前的日期
         *
         * @return yyyy年MM月dd日
         */
        fun getCurrentDate(): String {
            return dateFormat.format(mCalendar.getTime())
        }

        /**
         * 获取昨天的日期
         *
         * @return yyyy年MM月dd日
         */
        fun getYesterdayDate(): String {
            val yesterday = Calendar.getInstance()
            yesterday.add(Calendar.DATE, -1)
            return dateFormat.format(yesterday.time)
        }

        /**
         * 根据date列表获取day列表
         *
         * @param dateList
         * @return
         */
        fun dateListToDayList(dateList: List<String>): List<Int> {
            val calendar = Calendar.getInstance()
            val dayList: MutableList<Int> = ArrayList()
            for (date in dateList) {
                try {
                    calendar.setTime(dateFormat.parse(date))
                    val day = calendar.get(Calendar.DATE)
                    dayList.add(day)
                } catch (e: ParseException) {
                    e.printStackTrace()
                }

            }
            return dayList
        }


        /**
         * 根据当前日期获取以含当天的前一周日期
         * @return [2017年02月21日, 2017年02月22日, 2017年02月23日, 2017年02月24日, 2017年02月25日, 2017年02月26日, 2017年02月27日]
         */
        fun getBeforeDateListByNow(): List<String> {
            val weekList: MutableList<String> = ArrayList()

            for (i in -6..0) {
                //以周日为一周的第一天
                val calendar = Calendar.getInstance()
                calendar.add(Calendar.DATE, i)
                val date = dateFormat.format(calendar.time)
                weekList.add(date)
            }
            return weekList
        }

        /**
         * 判断当前日期是周几
         * @param curDate
         * @return
         */
        fun getCurWeekDay(curDate: String): String {
            var w = 0
            try {
                val date = dateFormat.parse(curDate)
                val calendar = Calendar.getInstance()
                calendar.time = date
                w = calendar.get(Calendar.DAY_OF_WEEK) - 1
                if (w < 0) {
                    w = 0
                }

            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return weekStrings[w]
        }


        /**
         * 时间工具
         *
         * @author D10NG
         * @date on 2019-10-08 11:28
         */

        /**
         * 获取当前系统时间戳，单位毫秒
         */
        val curTime: Long
            get() = System.currentTimeMillis()

        /**
         * 获取当前年份
         */
        val curYear: Int
            get() = curTime.getDateYear()

        /**
         * 获取当前月份
         */
        val curMonth: Int
            get() = curTime.getDateMonth()

        /**
         * 获取当前日
         */
        val curDay: Int
            get() = curTime.getDateDay()

        /**
         * 获取当前小时
         */
        val curHour: Int
            get() = curTime.getDateHour()

        /**
         * 获取当前分钟
         */
        val curMinute: Int
            get() = curTime.getDateMinute()

        /**
         * 获取当前秒钟
         */
        val curSecond: Int
            get() = curTime.getDateSecond()

        /**
         * 获取时间戳中的年份
         * @return [Int] 年份
         */
        fun Long.getDateYear(): Int {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = this
            return calendar.get(Calendar.YEAR)
        }

        /**
         * 获取时间戳中的月份
         * @return [Int] 月份
         */
        fun Long.getDateMonth(): Int {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = this
            return calendar.get(Calendar.MONTH) + 1
        }

        /**
         * 获取时间戳中的日
         * @return [Int] 日
         */
        fun Long.getDateDay(): Int {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = this
            return calendar.get(Calendar.DAY_OF_MONTH)
        }

        /**
         * 获取时间戳中的小时 24小时
         * @return [Int] 小时
         */
        fun Long.getDateHour(): Int {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = this
            return calendar.get(Calendar.HOUR_OF_DAY)
        }

        /**
         * 获取时间戳中的分钟
         * @return [Int] 分钟
         */
        fun Long.getDateMinute(): Int {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = this
            return calendar.get(Calendar.MINUTE)
        }

        /**
         * 获取时间戳中的秒钟
         * @return [Int] 秒钟
         */
        fun Long.getDateSecond(): Int {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = this
            return calendar.get(Calendar.SECOND)
        }

        /**
         * 时间戳转换成字符窜
         * @param pattern 时间样式 yyyy-MM-dd HH:mm:ss
         * @return [String] 时间字符串
         */
        @SuppressLint("SimpleDateFormat")
        fun Long.toDateStr(pattern: String = "yyyy-MM-dd HH:mm:ss"): String {
            val date = Date(this)
            val format = SimpleDateFormat(pattern)
            return format.format(date)
        }

        fun getCurrentTime(): String {
            return curTime.toDateStr()
        }

        /**
         * 将字符串转为时间戳
         * @param pattern 时间样式 yyyy-MM-dd HH:mm:ss
         * @return [String] 时间字符串
         */
        fun String.toDateLong(pattern: String = "yyyy-MM-dd HH:mm:ss"): Long {
            @SuppressLint("SimpleDateFormat")
            val dateFormat = SimpleDateFormat(pattern)
            var date: Date? = Date()
            try {
                date = dateFormat.parse(this)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return date?.time ?: 0
        }

        /**
         * 根据年月日获取时间戳
         * @param year 年
         * @param month 月
         * @param day 日
         * @return [Long] 时间戳
         */
        fun getDateFromYMD(year: Int = curYear, month: Int = curMonth, day: Int = curDay): Long {
            return getDateFromYMDHMS(year, month, day, 0, 0, 0)
        }

        /**
         * 根据年月日时分秒获取时间戳
         * @param year Int 年
         * @param month Int 月
         * @param day Int 日
         * @param hour Int 时
         * @param minute Int 分
         * @param second Int 秒
         * @return [Long] 时间戳
         */
        fun getDateFromYMDHMS(
            year: Int = curYear,
            month: Int = curMonth,
            day: Int = curDay,
            hour: Int = curHour,
            minute: Int = curMinute,
            second: Int = curSecond
        ): Long {
            val calendar = Calendar.getInstance()
            calendar.set(year, month - 1, day, hour, minute, second)
            calendar.set(Calendar.MILLISECOND, 0)
            return calendar.timeInMillis
        }

        /**
         * 获取第n天的时间戳
         * @param offset n
         * @return [Long] 时间戳
         */
        fun getNextDate(offset: Int): Long {
            val calendar = Calendar.getInstance()
            calendar.time = Date(getDateFromYMD(curYear, curMonth, curDay))
            calendar.add(Calendar.DAY_OF_MONTH, offset)
            return calendar.timeInMillis
        }

        /**
         * 获取某个日子为标点的附近的日子时间戳
         * @receiver Long
         * @param offset Int
         * @return Long
         */
        fun Long.getNextDay(offset: Int): Long {
            val calendar = Calendar.getInstance()
            calendar.time = Date(this)
            calendar.add(Calendar.DAY_OF_MONTH, offset)
            return calendar.timeInMillis
        }

        /**
         * 获取指定月份的天数
         * @param year 年
         * @param month 月
         * @return [Int] 天数
         */
        @SuppressLint("SimpleDateFormat")
        fun getDaysOfMonth(year: Int, month: Int): Int {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month - 1)
            return calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        }

        /**
         * 获取今天星期几
         * @return [Int] [Calendar.SUNDAY]
         */
        fun getCurWeek(): Int {
            return curTime.getDateWeek()
        }

        /**
         * 获取时间戳是星期几
         * @return [Int] [Calendar.SUNDAY]
         */
        fun Long.getDateWeek(): Int {
            val calendar = Calendar.getInstance()
            calendar.time = Date(this)
            return calendar.get(Calendar.DAY_OF_WEEK)
        }

        /**
         * 时间戳是否为今天的
         * @receiver Long
         * @return Boolean
         */
        fun Long.isToday(): Boolean {
            return getDateYear() == curYear && getDateMonth() == curMonth && getDateDay() == curDay
        }

        /**
         * 时间戳是否为昨天的
         * @receiver Long
         * @return Boolean
         */
        fun Long.isYesterday(): Boolean {
            val yesterday = curTime.getNextDay(-1)
            return getDateYear() == yesterday.getDateYear() && getDateMonth() == yesterday.getDateMonth() && getDateDay() == yesterday.getDateDay()
        }

        /**
         * 本地时间转化为UTC时间
         * @receiver Long
         * @return Long
         */
        fun Long.toUTCDate(): Long {
            val calendar = Calendar.getInstance().apply {
                timeInMillis = this@toUTCDate
            }
            val zoneOffset = calendar.get(Calendar.ZONE_OFFSET)
            val dstOffset = calendar.get(Calendar.DST_OFFSET)
            calendar.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset))
            return calendar.timeInMillis
        }

        /**
         * UTC时间转化为本地时间
         * @receiver Long
         * @return Long
         */
        @SuppressLint("SimpleDateFormat")
        fun Long.toLocalDate(): Long {
            val pattern = "yyyyMMddHHmmssSSS"
            val utcSdf = SimpleDateFormat(pattern).apply {
                timeZone = TimeZone.getTimeZone("UTC")
            }
            val utcD = utcSdf.parse(this.toDateStr(pattern)) ?: return 0L
            val localSdf = SimpleDateFormat(pattern).apply {
                timeZone = TimeZone.getDefault()
            }
            val localStr = localSdf.format(utcD.time)
            return localStr.toDateLong(pattern)
        }

        /**
         * UTC时间转化为指定timeZone时间
         * @receiver Long
         * @param timeZoneInt Int
         * @return Long
         */
        @SuppressLint("SimpleDateFormat")
        fun Long.toCustomDate(timeZoneInt: Int): Long {
            val pattern = "yyyyMMddHHmmssSSS"
            val utcSdf = SimpleDateFormat(pattern).apply {
                timeZone = TimeZone.getTimeZone("UTC")
            }
            val utcD = utcSdf.parse(this.toDateStr(pattern)) ?: return 0L
            val localSdf = SimpleDateFormat(pattern).apply {
                timeZone =
                    TimeZone.getTimeZone("GMT" + (if (timeZoneInt >= 0) "+" else "") + timeZoneInt)
            }
            val localStr = localSdf.format(utcD.time)
            return localStr.toDateLong(pattern)
        }

        fun secondToTime(timeMillis: Long): String? {
            val hour = timeMillis / (1000 * 60 * 60)
            val minute = (timeMillis - hour * 1000 * 60 * 60) / (1000 * 60)
            val builder = StringBuilder()
            if (hour > 0) {
                builder.append(hour).append("小时")
            }
            if (minute > 0) {
                builder.append(minute).append("分钟")
            }
            return builder.toString()
        }
    }


}