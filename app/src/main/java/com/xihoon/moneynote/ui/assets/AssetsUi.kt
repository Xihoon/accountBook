package com.xihoon.moneynote.ui.assets

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.xihoon.moneynote.ui.assets.expenses.ExpensesUi
import com.xihoon.moneynote.ui.composable.collectAsStateLifecycleAware
import com.xihoon.moneynote.ui.source.UseItem
import com.xihoon.moneynote.ui.theme.MoneyNoteTheme
import com.xihoon.moneynote.viewmodel.MainViewModel

@Composable
fun AssetsUi(viewModel: MainViewModel, navController: NavController) {
    val openExpense = remember { mutableStateOf(false) }
    val useList = viewModel.useList.collectAsStateLifecycleAware(initial = emptyList())
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        elevation = 10.dp
    ) {

        Column {
            AssetsHeader(useList) { openExpense.value = true }
            Box(modifier = Modifier.fillMaxWidth().weight(1f)) {
                AssetsList(useList, navController)
            }
        }
        ExpensesUi(viewModel = viewModel, open = openExpense)
    }
}


@Composable
private fun AssetsList(
    useList: State<List<UseItem>?>,
    navController: NavController
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top
    ) {
        useList.value
            ?.also { list ->
                items(list.size) { item ->
                    AccountField(list[item]) { useItem ->
                        navController.moveDetail(useItem.key)
                    }
                }
            }
            ?: item {
                Text(text = "항목없음")
            }
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Composable
fun AssetsUiPreview() {
    MoneyNoteTheme {
        AssetsUi(MainViewModel(), rememberNavController())
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun AssetsNightUiPreview() {
    MoneyNoteTheme {
        AssetsUi(MainViewModel(), rememberNavController())
    }
}


