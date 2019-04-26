package com.amaro.todolist.logger

import android.util.Log
import com.amaro.todolist.BuildConfig

class AppLog : Logger{

    val TAG = "TodoList"

    override fun d(tag: String, message: String) {
        if (Log.isLoggable(TAG, Log.DEBUG) || BuildConfig.DEBUG) {
            Log.d(TAG, "[$tag] : $message")
        }
    }

    override fun w(tag: String, message: String) {
        Log.w(TAG, "[$tag] : $message")
    }

    override fun e(tag: String, message: String) {
        Log.e(TAG, "[$tag] : $message")
    }

    override fun v(tag: String, message: String) {
        if (Log.isLoggable(TAG, Log.VERBOSE)) {
            Log.v(TAG, "[$tag] : $message")
        }
    }

    override fun i(tag: String, message: String) {
        if (Log.isLoggable(TAG, Log.INFO)) {
            Log.i(TAG, "[$tag] : $message")
        }
    }
}