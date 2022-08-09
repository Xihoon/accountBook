package com.xihoon.moneynote.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xihoon.moneynote.repository.Repository
import com.xihoon.moneynote.ui.Utils.logger
import com.xihoon.moneynote.ui.source.Use
import com.xihoon.moneynote.ui.source.UseItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

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
    fun getItems(from: Long, to: Long): Flow<List<UseItem>> {
        return repository.getItems(from, to)
    }

    fun getTotalUsed(pos: Int): Flow<List<Long>> {
        logger.info { "getTotalUsed pos:$pos" }
        val from = Calendar.getInstance()
        from.add(Calendar.YEAR, pos)
        from.set(Calendar.MONTH, 0)
        from.set(Calendar.DATE, 1)
        from.set(Calendar.HOUR_OF_DAY, 0)
        from.set(Calendar.MINUTE, 0)
        from.set(Calendar.SECOND, 0)
        val to = Calendar.getInstance()
        to.add(Calendar.YEAR, pos)
        to.set(Calendar.MONTH, 11)
        to.set(Calendar.DATE, 31)
        to.set(Calendar.HOUR_OF_DAY, 23)
        to.set(Calendar.MINUTE, 59)
        to.set(Calendar.SECOND, 59)
        return getItems(from.time.time, to.time.time).map {
            List(12) { index ->
                it
                    .filter { item ->
                        from.set(Calendar.MONTH, index)
                        to.set(Calendar.MONTH, index)
                        to.set(Calendar.DATE, to.getActualMaximum(Calendar.DAY_OF_MONTH))
                        item.use.time >= from.time.time && item.use.time <= to.time.time
                    }
                    .sumOf { item -> item.use.amount }.toLong()
            }
        }
    }

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