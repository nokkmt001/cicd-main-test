package com.phat.mytestcicd.presentation.utils

import android.util.Log
import com.phat.mytestcicd.di.enableLog

object Log {

    fun d(tag: String, message: String) {
        if (enableLog) Log.d(tag, message)
    }

    fun d(tag: String, message: String, e: Exception) {
        if (enableLog) Log.d(tag, message, e)
    }

    fun e(tag: String, message: String) {
        if (enableLog) Log.e(tag, message)
    }

    fun e(tag: String, message: String, e: Exception) {
        if (enableLog) Log.e(tag, message, e)
    }

    fun i(tag: String, message: String) {
        if (enableLog) Log.i(tag, message)
    }

    fun i(tag: String, message: String, e: Exception) {
        if (enableLog) Log.i(tag, message, e)
    }

}
