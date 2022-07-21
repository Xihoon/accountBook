package com.xihoon.moneynote.ui

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.google.accompanist.pager.ExperimentalPagerApi
import com.xihoon.moneynote.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainUi(viewModel: MainViewModel) {
    var isLogin = remember { mutableStateOf(false) }
    LocalLifecycleOwner.current.lifecycleScope.launch {
        isLogin.value = true
    }
    if (isLogin.value) {
        TextTabsUi(viewModel)
    } else {
        LoadingUi()
    }
}

