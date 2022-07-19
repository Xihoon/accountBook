package com.xihoon.moneynote.ui.account

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xihoon.moneynote.ui.InputUi
import com.xihoon.moneynote.ui.composable.collectAsStateLifecycleAware
import com.xihoon.moneynote.ui.theme.MoneyNoteTheme
import com.xihoon.moneynote.viewmodel.MainViewModel

@Composable
fun AccountUi(viewModel: MainViewModel) {
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
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top
                ) {
                    Text(text = "지출")
                    useList.value?.forEach {
                        AccountField(use = it)
                    }
                }

                InputUi(viewModel = viewModel, open = openAccount)

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

@Preview(showBackground = true)
@Composable
fun AccountUiPreview() {
    MoneyNoteTheme {
        AccountUi(MainViewModel())
    }
}
