package com.xihoon.moneynote.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.xihoon.moneynote.Logger
import com.xihoon.moneynote.ui.account.AccountUi
import com.xihoon.moneynote.ui.theme.MoneyNoteTheme
import com.xihoon.moneynote.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@Composable
fun TextTabsUi(viewModel: MainViewModel) {
    val tabData = listOf(
        "가계부",
        "통계",
        "더보기",
    )
    val pagerState = rememberPagerState(
        pageCount = tabData.size,
        initialOffscreenLimit = 2,
        infiniteLoop = true,
        initialPage = 0,
    )
    val tabIndex = pagerState.currentPage
    val coroutineScope = rememberCoroutineScope()
    Column {
        TabRow(selectedTabIndex = tabIndex) {
            tabData.forEachIndexed { index, text ->
                Tab(
                    selected = tabIndex == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = { Text(text = text) }
                )
            }
        }
        HorizontalPager(
            state = pagerState
        ) { index ->
            when (index) {
                0 -> {
                    AccountUi(viewModel)
                }
                1 -> {
                    Use(viewModel, coroutineScope)
                }
                2 -> {
                    Use(viewModel, coroutineScope)
                }
            }
        }
    }
}

@Composable
fun Content(msg: String) {
    Logger().info { "greeting message $msg" }
    Text(
        text = msg,
        Modifier
            .border(2.dp, Color.Black)
            .width(200.dp)
            .height(50.dp)
    )
}

@OptIn(ExperimentalPagerApi::class)
@Preview(showBackground = true)
@Composable
fun TabUiPreview() {
    MoneyNoteTheme {
        TextTabsUi(MainViewModel())
    }
}