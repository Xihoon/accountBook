package com.xihoon.moneynote.ui.assets

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.xihoon.moneynote.ui.Utils.decimalFormat
import com.xihoon.moneynote.ui.assets.expenses.ExpensesUi
import com.xihoon.moneynote.ui.composable.collectAsStateLifecycleAware
import com.xihoon.moneynote.ui.source.Use
import com.xihoon.moneynote.ui.theme.MoneyNoteTheme
import com.xihoon.moneynote.viewmodel.MainViewModel
import java.text.DecimalFormat

@Composable
fun AccountUi(viewModel: MainViewModel, navController: NavController) {
    val openAccount = remember { mutableStateOf(false) }
    val useList = viewModel.useList.collectAsStateLifecycleAware(initial = emptyList())
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        elevation = 10.dp
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Row {
                    Text(
                        text = "지출 : ", Modifier.padding(top = 10.dp, bottom = 10.dp),
                        style = MaterialTheme.typography.subtitle1
                    )
                    Text(
                        text = decimalFormat.sum(useList.value?.map { it.use }),
                        Modifier.padding(top = 10.dp, bottom = 10.dp),
                        style = MaterialTheme.typography.subtitle1,
                        color = Color.Red

                    )
                }
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
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

                ExpensesUi(viewModel = viewModel, open = openAccount)

            }
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomEnd
            ) {
                Button(
                    onClick = {
                        openAccount.value = true
                    },
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(15.dp)
                ) {
                    Text("입력")
                }
            }
        }
    }
}

private fun DecimalFormat.sum(list: List<Use>?): String {
    val account = format(
        list
            ?.sumOf { it.amount }
            ?: 0)
    return "$account 원"
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Composable
fun AssetsUiPreview() {
    MoneyNoteTheme {
        AccountUi(MainViewModel(), rememberNavController())
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun AssetsNightUiPreview() {
    MoneyNoteTheme {
        AccountUi(MainViewModel(), rememberNavController())
    }
}
