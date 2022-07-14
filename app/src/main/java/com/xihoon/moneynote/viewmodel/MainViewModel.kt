package com.xihoon.moneynote.viewmodel

import androidx.lifecycle.ViewModel
import com.xihoon.moneynote.repository.Repository
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {
    private val repository by lazy { Repository() }
    fun setMessage(msg: String) {
        repository.setMessage(msg)
    }

    fun input(useType: String, category: String, account: Int, comment: String) {

    }

    private val _isMain by lazy { MutableStateFlow(false) }
    val isMain: StateFlow<Boolean> by lazy { _isMain }
    fun setMain() {
        _isMain.value = true
        _expensesTypes.tryEmit(listOf("고정", "교육", "외식"))
    }

    fun getMessage() = repository.message

    private val _useTypes by lazy {
        MutableSharedFlow<List<String>>(
            1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )
    }

    fun useTypes(): SharedFlow<List<String>> = _useTypes

    private val _expensesTypes by lazy {
        MutableSharedFlow<List<String>>(
            1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )
    }

    fun expensesTypes(): SharedFlow<List<String>> = _expensesTypes
}