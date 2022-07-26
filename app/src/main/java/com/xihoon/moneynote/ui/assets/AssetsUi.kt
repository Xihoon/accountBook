package com.xihoon.moneynote.ui.assets

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.xihoon.moneynote.ui.assets.expenses.ExpensesUi
import com.xihoon.moneynote.ui.composable.collectAsStateLifecycleAware
import com.xihoon.moneynote.ui.theme.MoneyNoteTheme
import com.xihoon.moneynote.viewmodel.MainViewModel
import java.util.*

@Composable
fun AssetsUi(viewModel: MainViewModel, navController: NavController, pos: Int) {
    val openExpense = remember { mutableStateOf(false) }
    val from = Calendar.getInstance()
    from.add(Calendar.MONTH, pos)
    from.set(Calendar.DATE, 1)
    from.set(Calendar.HOUR_OF_DAY, 0)
    from.set(Calendar.MINUTE, 0)
    from.set(Calendar.SECOND, 0)
    val to = Calendar.getInstance()
    to.add(Calendar.MONTH, pos + 1)
    to.set(Calendar.DATE, 0)
    to.set(Calendar.HOUR_OF_DAY, 23)
    to.set(Calendar.MINUTE, 59)
    from.set(Calendar.SECOND, 59)
    val useList = viewModel.getItems(from.time.time, to.time.time)
        .collectAsStateLifecycleAware(initial = emptyList())
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        elevation = 10.dp
    ) {

        Column {
            AssetsHeader(useList) { openExpense.value = true }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                AssetsList(useList, navController)
            }
        }
        ExpensesUi(viewModel = viewModel, open = openExpense)
    }
}


@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Composable
fun AssetsUiPreview() {
    MoneyNoteTheme {
        AssetsUi(MainViewModel(), rememberNavController(), 0)
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun AssetsNightUiPreview() {
    MoneyNoteTheme {
        AssetsUi(MainViewModel(), rememberNavController(), 0)
    }
}


