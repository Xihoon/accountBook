package com.xihoon.moneynote.ui.assets

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xihoon.moneynote.ui.Utils
import com.xihoon.moneynote.ui.theme.MoneyNoteTheme
import com.xihoon.moneynote.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

private const val COUNT = Int.MAX_VALUE / 2
private const val CURRENT = Int.MAX_VALUE / 2 / 2

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MonthlyUi(modifier: Modifier, viewModel: MainViewModel) {
    val formatter = SimpleDateFormat("yyyy", Locale.getDefault())
    val pagerState = rememberPagerState(initialPage = CURRENT)
    val coroutineScope = rememberCoroutineScope()
    Column(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Utils.logger.info { "TopAppBar row" }
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage - 1)
                    }
                },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "이전")
            }
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.YEAR, pagerState.currentPage - CURRENT)
            Text(formatter.format(calendar.time))
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(Icons.Default.KeyboardArrowRight, contentDescription = "다음")
            }
        }
        HorizontalPager(
            pageCount = COUNT,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            state = pagerState,
            verticalAlignment = Alignment.Top
        ) { index ->
            Utils.logger.info { "tab index:$index" }
            val pos = index - CURRENT
            MonthlyContent(viewModel, pos)
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun MonthlyUiPreview() {
    MoneyNoteTheme {
        MonthlyUi(
            modifier = Modifier.fillMaxSize(),
            MainViewModel()
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MonthlyNightUiPreview() {
    MoneyNoteTheme {
        MonthlyUi(
            modifier = Modifier.fillMaxSize(),
            MainViewModel()
        )
    }
}