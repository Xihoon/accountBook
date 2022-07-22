package com.xihoon.moneynote.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xihoon.moneynote.repository.Repository
import com.xihoon.moneynote.ui.source.Use
import com.xihoon.moneynote.ui.source.UseItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val repository by lazy { Repository() }

    val assetsTypes by lazy { repository.assetsTypes }
    fun addAssetsType(type: String) {
        launchIo { repository.addAssetsType(type) }
    }

    val incomeCategory by lazy { repository.incomeCategory }
    fun addIncomeCategory(category: String) {
        launchIo { repository.addIncomeCategory(category) }
    }

    val expenseCategory by lazy { repository.expenseCategory }
    fun addExpenseCategory(category: String) {
        launchIo { repository.addExpenseCategory(category) }
    }

    val useList by lazy { repository.useItems }
    fun getItemFlow(useKey: String): Flow<UseItem?> {
        return useList.map {
            it?.find { item -> item.key == useKey }
        }.distinctUntilChanged()
    }

    fun use(use: Use) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.use(use)
        }
    }

    fun updateUse(useItem: UseItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(useItem)
        }
    }

    fun removeUse(useKey: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.remove(useKey)
        }
    }

    private fun launchIo(action: suspend () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) { action() }
    }
}