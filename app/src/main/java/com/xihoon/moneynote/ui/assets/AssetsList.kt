package com.xihoon.moneynote.ui.assets

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.xihoon.moneynote.ui.Utils.dateFormat
import com.xihoon.moneynote.ui.Utils.decimalFormat
import com.xihoon.moneynote.ui.Utils.sum
import com.xihoon.moneynote.ui.source.Use
import com.xihoon.moneynote.ui.source.UseItem
import com.xihoon.moneynote.ui.theme.MoneyNoteTheme
import com.xihoon.moneynote.ui.theme.RED
import java.util.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AssetsList(
    useList: State<List<UseItem>?>,
    navController: NavController
) {
    val data = useList.value ?: run {
        Text(
            text = "항목없음",
            modifier = Modifier.fillMaxSize()
        )
        return
    }

    val sorted = data.sortedByDescending { it.use.time }
    val grouped = sorted.groupBy { dateFormat.format(Date(it.use.time)) }
    val listState = rememberLazyListState()
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        state = listState,
        verticalArrangement = Arrangement.Top
    ) {
        grouped.forEach { (date, list) ->
            stickyHeader { DateHeader(date, decimalFormat.sum(list.map { it.use })) }
            items(list.size, key = { index -> list[index].key }) { index ->
                AssetsField(list[index]) { navController.moveDetail(it.key) }
            }
        }
    }
}

@Composable
private fun DateHeader(date: String, amount: String) {
    Box(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(MaterialTheme.colors.secondary)
    ) {
        Row {
            Text(
                text = date, modifier = Modifier
                    .weight(1f)
                    .wrapContentHeight()
            )
            Text(text = amount, color = RED)
        }

    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun DateHeaderPreview() {
    MoneyNoteTheme {
        Row(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            DateHeader("2022.07.22", "3,000원")
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DateHeaderNightPreview() {
    MoneyNoteTheme {
        Row(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            DateHeader("2022.07.22", "3,000원")
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun AssetsListPreview() {
    MoneyNoteTheme {
        Row(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            AssetsList(remember { mutableStateOf(previewItem) }, rememberNavController())
        }
    }
}

@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AssetsListNightUiPreview() {
    MoneyNoteTheme {
        Row(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            AssetsList(remember { mutableStateOf(previewItem) }, rememberNavController())
        }
    }
}

private val previewItem = listOf(
    UseItem(
        "key",
        Use(
            useType = "카드",
            category = "간식",
            amount = 10000,
            comment = "하하"
        )
    )
)