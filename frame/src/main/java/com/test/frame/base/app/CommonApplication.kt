package com.test.frame.base.app

import android.app.Application
import android.content.Context


class CommonApplication {
    private lateinit var application: Application

    companion object {
        @Volatile
        private var commonApplication: CommonApplication? = null
        fun instance(): CommonApplication {
            return commonApplication ?: synchronized(this) {
                commonApplication ?: createApplication().also {
                    commonApplication = it
                }
            }
        }

        private fun createApplication(): CommonApplication {
            return CommonApplication()
        }
    }

    fun getApplication(): Application {
        return application
    }

    fun getContent(): Context {
        return application.applicationContext
    }

    fun setApplication(application: Application) {
        this.application = application
    }


}