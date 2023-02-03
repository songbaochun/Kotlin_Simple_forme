package com.test.frame.base.utils

import android.annotation.TargetApi
import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.content.pm.PackageManager
import android.hardware.Sensor.TYPE_STEP_COUNTER
import android.hardware.Sensor.TYPE_STEP_DETECTOR
import android.hardware.SensorManager
import android.os.Build

class StepCountCheckUtil(private val mContext: Context) {

    //是否有传感器
    private var hasSensor: Boolean = isSupportStepCountSensor()


    @TargetApi(Build.VERSION_CODES.KITKAT)
    fun isSupportStepCountSensor(): Boolean {
        return mContext.packageManager
            .hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_COUNTER)
    }


    companion object{
        /**
         * 判断该设备是否支持计歩
         *
         * @param context
         * @return
         */
        fun isSupportStepCountSensor(context: Context): Boolean {
            // 获取传感器管理器的实例
            val sensorManager = context
                .getSystemService(SENSOR_SERVICE) as SensorManager
            val countSensor = sensorManager.getDefaultSensor(TYPE_STEP_COUNTER)
            val detectorSensor = sensorManager.getDefaultSensor(TYPE_STEP_DETECTOR)
            return countSensor != null || detectorSensor != null
        }
    }



}