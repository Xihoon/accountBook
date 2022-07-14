package com.xihoon.moneynote.ui

import androidx.compose.foundation.clickable
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
import com.xihoon.moneynote.ui.theme.MoneyNoteTheme
import com.xihoon.moneynote.viewmodel.MainViewModel

@Composable
fun AccountUi(viewModel: MainViewModel) {
    val openAccount = remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        elevation = 10.dp
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomEnd
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                }

                InputUi(viewModel = viewModel, open = openAccount)

            }
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

@Preview(showBackground = true)
@Composable
fun AccountUiPreview() {
    MoneyNoteTheme {
        AccountUi(MainViewModel())
    }
}
