package com.xihoon.moneynote.ui.assets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight.Companion.W700
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.xihoon.moneynote.ui.Utils.logger
import com.xihoon.moneynote.ui.theme.MoneyNoteTheme
import com.xihoon.moneynote.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AssetsPagerUi(viewModel: MainViewModel) {
    logger.info { "TextTabsUi" }

    val appBarState = remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar {
                logger.info { "TopAppBar" }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                appBarState.value = 0
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "일별 내역",
                            style = if (appBarState.value == 0) {
                                TextStyle(fontSize = 20.sp, fontWeight = W700, textDecoration = TextDecoration.Underline)
                            } else {
                                LocalTextStyle.current
                            }
                        )
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                appBarState.value = 1
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "월별 내역",
                            style = if (appBarState.value == 1) {
                                TextStyle(fontSize = 20.sp, fontWeight = W700, textDecoration = TextDecoration.Underline)
                            } else {
                                LocalTextStyle.current
                            }
                        )
                    }

                }
            }
        }
    ) {
        if(appBarState.value == 0) {
            DailyUi(viewModel)
        } else  {
            MonthlyUi(viewModel)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TabUiPreview() {
    MoneyNoteTheme {
        AssetsPagerUi(MainViewModel())
    }
}