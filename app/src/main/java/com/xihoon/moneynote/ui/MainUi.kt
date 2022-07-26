package com.xihoon.moneynote.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.xihoon.moneynote.ui.assets.AssetsPagerUi
import com.xihoon.moneynote.viewmodel.MainViewModel

@Composable
fun MainUi(viewModel: MainViewModel) {
    Utils.logger.info { "MainUi" }
    val isLogin = remember { mutableStateOf(false) }
    if (isLogin.value) {
        Utils.logger.info { "TextTabsUi call" }
        AssetsPagerUi(viewModel)
    } else {
        Utils.logger.info { "LoadingUi call" }
        LoadingUi()
    }
    LaunchedEffect(key1 = isLogin) {
        isLogin.value = true
    }
}

