package com.xihoon.moneynote

import android.util.Log

class Logger(private val tag : String ="Xihoon") {
    fun info(msg : () -> String) {
        Log.i(tag, msg())
    }
}