package com.xihoon.moneynote.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xihoon.moneynote.ui.Utils.logger
import com.xihoon.moneynote.ui.theme.MoneyNoteTheme
import com.xihoon.moneynote.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@Composable
fun LoadingUi() {
    logger.info { "LoadingUi"}
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.primarySurface, shape = RectangleShape),
        contentAlignment = Alignment.Center
    ) {
        logger.info { "LoadingUi box"}
        Text(
            text = "Loading...",
            modifier = Modifier.wrapContentSize(),
            fontSize = 30.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingUiPreview() {
    MoneyNoteTheme {
        LoadingUi()
    }
}
