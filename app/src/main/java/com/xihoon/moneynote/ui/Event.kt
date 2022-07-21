package com.xihoon.moneynote.ui

import com.xihoon.moneynote.ui.source.UseItem

sealed class Event
data class DetailEvent(val item: UseItem) : Event()