package com.example.glesdemo.utils

import android.util.Log
import com.example.glesdemo.BuildConfig

private const val LOG_TAG = "OpenGL"

fun log(message: String) {
    if (BuildConfig.DEBUG_LOG) {
        Log.d(LOG_TAG, message)
    }
}