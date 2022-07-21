package com.xihoon.moneynote.ui.account.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.xihoon.moneynote.ui.account.AddDialog
import com.xihoon.moneynote.ui.account.expenses.ExpensesContent
import com.xihoon.moneynote.ui.composable.collectAsStateLifecycleAware
import com.xihoon.moneynote.ui.logger
import com.xihoon.moneynote.ui.source.UseItem
import com.xihoon.moneynote.viewmodel.MainViewModel

@Composable
fun DetailUi(
    viewModel: MainViewModel,
    navController: NavController,
    backStackEntry: NavBackStackEntry
) {
    val useKey = backStackEntry.arguments?.getString("useKey")
    logger.info { "DetailUi useKey:$useKey" }
    if (useKey.isNullOrEmpty()) {
        navController.navigateUp()
        return
    }
    val openUseType = remember { mutableStateOf(false) }
    val openCategory = remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        elevation = 10.dp
    ) {
        logger.info { "DetailUi Card" }
        Box(modifier = Modifier.fillMaxSize()) {
            logger.info { "DetailUi Box" }
            DetailContent(
                viewModel,
                navController,
                useKey,
                { openUseType.value = it },
                { openCategory.value = it }
            )
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

@Composable
private fun DetailContent(
    viewModel: MainViewModel,
    navController: NavController,
    useKey: String,
    openUseType: (Boolean) -> Unit,
    openCategory: (Boolean) -> Unit,
) {
    logger.info { "DetailUi DetailContent" }
    val item: State<UseItem?> = viewModel.getItemFlow(useKey)
        .collectAsStateLifecycleAware(initial = null)
    ExpensesContent(
        true,
        viewModel,
        { openUseType(it) },
        { openCategory(it) },
        item.value?.use?.useType,
        item.value?.use?.category,
        item.value?.use?.amount?.toString(),
        item.value?.use?.comment,
        {
            viewModel.removeUse(useKey)
            navController.navigateUp()
        }
    ) {
        viewModel.updateUse(UseItem(useKey, it))
        navController.navigateUp()
    }
}