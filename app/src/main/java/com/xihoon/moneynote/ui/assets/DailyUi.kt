package com.xihoon.moneynote.ui.assets

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
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
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.xihoon.moneynote.ui.Utils
import com.xihoon.moneynote.ui.theme.MoneyNoteTheme
import com.xihoon.moneynote.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

private const val COUNT = Int.MAX_VALUE / 2
private const val CURRENT = Int.MAX_VALUE / 2 / 2

@OptIn(ExperimentalPagerApi::class)
@Composable
fun DailyUi(viewModel: MainViewModel) {
    val formatter = SimpleDateFormat("yyyy MM", Locale.getDefault())
    val pagerState = rememberPagerState(initialPage = CURRENT)
    val coroutineScope = rememberCoroutineScope()
    Column(modifier = Modifier.fillMaxSize()) {
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
            calendar.add(Calendar.MONTH, pagerState.currentPage - CURRENT)
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
            count = COUNT,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            state = pagerState
        ) { index ->
            Utils.logger.info { "tab index:$index" }
            val pos = index - CURRENT
            DailyContent(viewModel, pos)
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun DailyUiPreview() {
    MoneyNoteTheme {
        DailyUi(MainViewModel())
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DailyNightUiPreview() {
    MoneyNoteTheme {
        DailyUi(MainViewModel())
    }
}