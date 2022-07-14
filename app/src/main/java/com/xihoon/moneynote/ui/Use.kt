package com.xihoon.moneynote.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xihoon.moneynote.Logger
import com.xihoon.moneynote.ui.theme.MoneyNoteTheme
import com.xihoon.moneynote.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun Use(viewModel: MainViewModel, scope: CoroutineScope) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        elevation = 10.dp
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(15.dp)
        ) {
            val logger by lazy { Logger() }
            var items by remember { mutableStateOf(emptyList<String>()) }
            var selected by remember { mutableStateOf("") }
            scope.launch {
                viewModel.expensesTypes().collect {
                    logger.info { "expensesTypes:${it.joinToString { type -> type }}" }
                    items = it
                    if(items.isNotEmpty() && selected.isEmpty()) selected = items[0]
                }
            }
            Spinner(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                items = items,
                selectedItem = selected,
                onItemSelected = { selected = it },
                selectedItemFactory = { modifier, type ->
                    OutlinedTextField(
                        value = type,
                        modifier = modifier
                            .fillMaxWidth()
                            .height(60.dp),
                        enabled = false,
                        readOnly = true,
                        singleLine = true,
                        onValueChange = { },
                        label = { Text("카테고리") }
                    )
                },
                dropdownItemFactory = { type, index ->
                    Text(
                        modifier = Modifier
                            .width(100.dp)
                            .height(50.dp)
                            .padding(15.dp),
                        text = "$index $type"
                    )
                }
            )

            var amount by remember { mutableStateOf("0") }
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                value = amount,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                onValueChange = { amount = it },
                label = { Text("사용금액") }
            )

            var comment by remember { mutableStateOf("") }
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                value = comment,
                onValueChange = { comment = it },
                label = { Text("내용입력") }
            )

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                onClick = {
                    logger.info { "input button clicked." }
                    viewModel.input("",selected, amount.toInt(), comment)
                }
            ) {
                Text(text = "입력")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MoneyNoteTheme {
        Use(MainViewModel(), CoroutineScope(Dispatchers.Main))
    }
}