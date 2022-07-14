package com.xihoon.moneynote.ui

import androidx.compose.runtime.Composable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.xihoon.moneynote.ui.composable.collectAsStateLifecycleAware
import com.xihoon.moneynote.viewmodel.MainViewModel

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainUi(viewModel: MainViewModel) {
    val main = viewModel.isMain.collectAsStateLifecycleAware(false)
    if (main.value) {
        TextTabsUi(viewModel)
    } else {
        LoadingUi()
    }
}

