package com.xihoon.moneynote.ui.source

import androidx.annotation.Keep
import com.google.firebase.database.IgnoreExtraProperties

sealed class AccountItem
data class UseItem(val key : String, val use:Use) : AccountItem()

@Keep
@IgnoreExtraProperties
data class Use(
    val useType: String = "",
    val category: String = "",
    val amount: Int = 0,
    val comment: String = "",
    val time: Long = 0
)

@Keep
@IgnoreExtraProperties
data class Category(
    val type: String = "",
    val category: String = "",
)
