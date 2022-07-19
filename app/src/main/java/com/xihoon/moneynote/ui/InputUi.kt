package com.xihoon.moneynote.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.xihoon.moneynote.Logger
import com.xihoon.moneynote.ui.composable.Spinner
import com.xihoon.moneynote.ui.composable.collectAsStateLifecycleAware
import com.xihoon.moneynote.ui.theme.MoneyNoteTheme
import com.xihoon.moneynote.viewmodel.MainViewModel

@Composable
fun InputUi(viewModel: MainViewModel, open: MutableState<Boolean>) {
    if (open.value) {
        Dialog(onDismissRequest = { open.value = false }) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .wrapContentHeight(),
                shape = RoundedCornerShape(10.dp),
                color = Color.White
            ) {
                InputDialog(viewModel, open)
            }
        }
    }
}

@Composable
fun InputDialog(viewModel: MainViewModel, open: MutableState<Boolean>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(15.dp)
    ) {
        val logger by lazy { Logger() }
        val useTypes by viewModel
            .useTypes()
            .collectAsStateLifecycleAware(initial = emptyList())
        var selectedUseType by remember {
            mutableStateOf(if (useTypes.isEmpty()) "None" else useTypes[0])
        }
        Spinner(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            items = useTypes,
            selectedItem = selectedUseType,
            onItemSelected = { selectedUseType = it },
            selectedItemFactory = { modifier, type ->
                OutlinedTextField(
                    value = type,
                    modifier = modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    textStyle = LocalTextStyle.current.copy(fontSize = 10.sp),
                    enabled = false,
                    readOnly = true,
                    singleLine = true,
                    onValueChange = { },
                    label = { Text("지출형태", fontSize = 8.sp) }
                )
            },
            dropdownItemFactory = { type, index ->
                Text(
                    modifier = Modifier.wrapContentSize(),
                    text = "$index $type",
                    fontSize = 8.sp
                )
            }
        )

        val category by viewModel
            .expensesTypes()
            .collectAsStateLifecycleAware(initial = emptyList())
        var selectedCategory by remember {
            mutableStateOf(if (category.isEmpty()) "None" else category[0])
        }
        Spinner(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            items = category,
            selectedItem = selectedCategory,
            onItemSelected = { selectedCategory = it },
            selectedItemFactory = { modifier, type ->
                OutlinedTextField(
                    value = type,
                    modifier = modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    textStyle = LocalTextStyle.current.copy(fontSize = 10.sp),
                    enabled = false,
                    readOnly = true,
                    singleLine = true,
                    onValueChange = { },
                    label = { Text("카테고리", fontSize = 8.sp) }
                )
            },
            dropdownItemFactory = { type, index ->
                Text(
                    modifier = Modifier.wrapContentSize(),
                    text = "$index $type",
                    fontSize = 8.sp
                )
            }
        )

        var amount by remember { mutableStateOf("0") }
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            textStyle = LocalTextStyle.current.copy(fontSize = 10.sp),
            value = amount,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            onValueChange = { amount = it },
            label = { Text(text = "사용금액", fontSize = 8.sp) }
        )

        var comment by remember { mutableStateOf("") }
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            textStyle = LocalTextStyle.current.copy(fontSize = 10.sp),
            value = comment,
            onValueChange = { comment = it },
            label = { Text("내용입력", fontSize = 8.sp) }
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
                viewModel.input(selectedUseType, selectedCategory, amount.toInt(), comment)
                open.value = false
            }
        ) {
            Text(text = "입력")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InputUiPreview() {
    MoneyNoteTheme {
        InputDialog(MainViewModel(), remember { mutableStateOf(true) })
    }
}
