package com.test.frame.base.manager

import android.app.Activity
import android.os.Process

class ActivityManager {
    private lateinit var activityList: MutableList<Activity>

    companion object {
        @Volatile
        private var httpManager: ActivityManager? = null
        fun instance(): ActivityManager {
            return httpManager ?: synchronized(this) {
                httpManager ?: buildManager().also {
                    httpManager = it
                }
            }
        }

        private fun buildManager(): ActivityManager {
            return ActivityManager().apply {
                createActivityList()
            }
        }
    }

    private fun createActivityList() {
        activityList = mutableListOf<Activity>()
    }

    /**
     * 添加单个Activity
     */
    fun addActivity(activity: Activity) {
        // 为了避免重复添加，需要判断当前集合是否满足不存在该Activity
        if (!activityList.contains(activity)) {
            activityList.add(activity) // 把当前Activity添加到集合中
        }
    }

    /**
     * 销毁单个Activity
     */
    fun removeActivity(activity: Activity?) {
        // 判断当前集合是否存在该Activity
        if (activityList.contains(activity)) {
            activityList.remove(activity) // 从集合中移除
            activity?.finish()
        }
    }

    /**
     * 销毁单个Activity
     */
    fun removeSpecialActivity(activityName: String?) {
        // 判断当前集合是否存在该Activity
        if (activityName == null || activityList.size <= 0) {
            return
        }
        for (i in activityList.size - 1 downTo 0) {
            if (activityName == activityList.get(i).javaClass.getSimpleName()) {
                activityList[i].finish()
                break
            }
        }
    }

    /**
     * 销毁所有的Activity
     */
    fun removeAllActivity() {
        // 通过循环，把集合中的所有Activity销毁
        for (activity in activityList) {
            activity.finish()
        }
    }

    /**
     * 销毁所有的Activity 并关掉应用进程
     */
    fun editApp() {
        // 通过循环，把集合中的所有Activity销毁
        for (activity in activityList) {
            activity.finish()
        }
        //杀死该应用进程
        Process.killProcess(Process.myPid())
    }

}