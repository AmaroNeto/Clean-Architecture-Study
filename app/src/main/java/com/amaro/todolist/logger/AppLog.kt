package com.amaro.todolist.logger

import android.util.Log
import com.amaro.todolist.BuildConfig
import com.amaro.todolist.domain.log.Logger
import timber.log.Timber

class AppLog : Logger {

    override fun d(tag: String, message: String) {
        Timber.tag(tag).d( message)
    }

    override fun w(tag: String, message: String) {
        Timber.tag(tag).w( message)
    }

    override fun e(tag: String, message: String) {
        Timber.tag( tag).e(message)
    }

    override fun v(tag: String, message: String) {
        Timber.tag( tag).v(message)
    }

    override fun i(tag: String, message: String) {
        Timber.tag(tag).i(message)
    }
}