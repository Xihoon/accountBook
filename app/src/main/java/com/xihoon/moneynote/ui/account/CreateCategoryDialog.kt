package com.xihoon.moneynote.ui.account

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xihoon.moneynote.ui.theme.MoneyNoteTheme

@Composable
fun AddDialog(
    openCategory: MutableState<Boolean>,
    title: String,
    onClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(15.dp)
    ) {
        var content by remember { mutableStateOf("") }
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            textStyle = LocalTextStyle.current,
            value = content,
            onValueChange = { content = it },
            label = { Text(text = title) }
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
                if (content.isEmpty()) {
                    openCategory.value = false
                    return@Button
                }
                onClick(content)
                openCategory.value = false
            }
        ) {
            Text(text = "입력")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExpensesCategoryPreview() {
    MoneyNoteTheme {
        AddDialog(
            remember { mutableStateOf(true) },
            "expenses"
        ) {}
    }
}

@Preview(showBackground = true)
@Composable
fun IncomeCategoryPreview() {
    MoneyNoteTheme {
        AddDialog(
            remember { mutableStateOf(true) },
            "income"
        ) {}
    }
}
