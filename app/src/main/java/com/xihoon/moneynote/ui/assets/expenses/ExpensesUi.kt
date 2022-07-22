package com.xihoon.moneynote.ui.assets.expenses

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.xihoon.moneynote.ui.assets.AddDialog
import com.xihoon.moneynote.viewmodel.MainViewModel

@Composable
fun ExpensesUi(viewModel: MainViewModel, open: MutableState<Boolean>) {
    val openUseType = remember { mutableStateOf(false) }
    val openCategory = remember { mutableStateOf(false) }
    if (open.value) {
        Dialog(onDismissRequest = { open.value = false }) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .wrapContentHeight(),
                shape = RoundedCornerShape(10.dp),
                color = MaterialTheme.colors.background
            ) {
                ExpensesContent(
                    false,
                    viewModel,
                    { openUseType.value = it },
                    { openCategory.value = it }
                ) {
                    viewModel.use(it)
                    open.value = false
                }
            }
        }
        if (openUseType.value) {
            Dialog(onDismissRequest = { openCategory.value = false }) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .wrapContentHeight(),
                    shape = RoundedCornerShape(10.dp),
                    color = MaterialTheme.colors.background
                ) {
                    AddDialog(openUseType, "지출 타입") {
                        viewModel.addAssetsType(it)
                    }
                }
            }
        }
        if (openCategory.value) {
            Dialog(onDismissRequest = { openCategory.value = false }) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .wrapContentHeight(),
                    shape = RoundedCornerShape(10.dp),
                    color = MaterialTheme.colors.background
                ) {
                    AddDialog(openCategory, "지출 카테고리") {
                        viewModel.addExpenseCategory(it)
                    }
                }
            }
        }
    }
}