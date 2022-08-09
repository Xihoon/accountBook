package com.xihoon.moneynote.ui

import android.content.Context
import android.widget.Toast
import com.xihoon.moneynote.Logger
import com.xihoon.moneynote.ui.source.Use
import java.text.DateFormat
import java.text.DecimalFormat

object Utils {
    val logger by lazy { Logger() }

    val decimalFormat by lazy { DecimalFormat("#,###") }
    val timeFormat = DateFormat.getTimeInstance()
    val dateFormat = DateFormat.getDateInstance()

    fun Context.toast(msg: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, msg, duration).show()
    }

    fun DecimalFormat.sum(list: List<Use>?): String {
        val account = format(
            list
                ?.sumOf { it.amount }
                ?: 0
        )
        return "$account 원"
    }

    fun DecimalFormat.convert(amount: Long): String {
        val account = format(amount)
        return "$account 원"
    }
}


