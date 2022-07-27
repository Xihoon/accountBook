package com.xihoon.moneynote.ui.assets.expenses

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xihoon.moneynote.ui.Utils.logger
import com.xihoon.moneynote.ui.Utils.toast
import com.xihoon.moneynote.ui.composable.Spinner
import com.xihoon.moneynote.ui.composable.collectAsStateLifecycleAware
import com.xihoon.moneynote.ui.source.Use
import com.xihoon.moneynote.ui.theme.MoneyNoteTheme
import com.xihoon.moneynote.viewmodel.MainViewModel
import java.util.*

@Composable
fun ExpensesContent(
    isEditable: Boolean,
    viewModel: MainViewModel,
    openUseType: (Boolean) -> Unit,
    openCategory: (Boolean) -> Unit,
    time: Long?,
    initUseType: String? = null,
    initCategory: String? = null,
    initAmount: String? = null,
    initComment: String? = null,
    onRemove: (() -> Unit)? = null,
    onComplete: (Use) -> Unit
) {
    logger.info { "ExpensesContent isEditable:$isEditable" }
    val context = LocalContext.current
    val useTypes by viewModel.assetsTypes
        .collectAsStateLifecycleAware(initial = emptyList())
    val categories by viewModel.expenseCategory
        .collectAsStateLifecycleAware(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(15.dp)
    ) {
        var useTime by remember { mutableStateOf(System.currentTimeMillis()) }
        TimeUi(time) { useTime = it }

        var useType by remember { mutableStateOf("") }
        if (!isEditable || initUseType != null) {
            UseType(useTypes, useType.ifEmpty { initUseType }, openUseType) { useType = it }
        }
        var category by remember { mutableStateOf("") }
        if (!isEditable || initCategory != null) {
            Category(categories, category.ifEmpty { initCategory }, openCategory) { category = it }
        }
        var amount by remember { mutableStateOf("") }
        if (!isEditable || initAmount != null) {
            Amount(amount.ifEmpty { initAmount }) { amount = it }
        }
        var comment by remember { mutableStateOf("") }
        if (!isEditable || initComment != null) {
            Comment(comment.ifEmpty { initComment }) { comment = it }
        }

        Spacer(
            Modifier
                .fillMaxWidth()
                .height(20.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Box(
                Modifier
                    .weight(1f)
                    .height(50.dp)
                    .padding(start = 10.dp, end = 10.dp)
            ) {
                CompleteButton(
                    context,
                    isEditable,
                    useTime,
                    useType.ifEmpty { initUseType ?: "" },
                    category.ifEmpty { initCategory ?: "" },
                    amount.ifEmpty { initAmount ?: "" },
                    comment.ifEmpty { initComment ?: "" },
                    onComplete
                )
            }
            if (isEditable) {
                Box(
                    Modifier
                        .weight(1f)
                        .height(50.dp)
                        .padding(start = 10.dp, end = 10.dp)
                ) {
                    RemoveButton { onRemove?.invoke() }
                }
            }
        }
    }
}

@Composable
private fun TimeUi(time: Long?, update: (time: Long) -> Unit) {
    if (time == null) return
    val calendar = Calendar.getInstance().also { it.time = Date(time) }
    val updateCalendar = Calendar.getInstance().also { it.time = Date(time) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("날짜:")

        ShowDatePicker(
            LocalContext.current,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ) { year, month, day ->
            logger.info { "date changed $year,$month,$day" }
            updateCalendar.set(year, month, day)
            update(updateCalendar.timeInMillis)
        }
        ShowTimePicker(
            LocalContext.current,
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE)
        ) { hour, minute ->
            logger.info { "time changed $hour:$minute" }
            updateCalendar.set(Calendar.HOUR_OF_DAY, hour)
            updateCalendar.set(Calendar.MINUTE, minute)
            update(updateCalendar.timeInMillis)
        }
    }
}

@Composable
private fun ShowDatePicker(
    context: Context,
    initYear: Int,
    initMonth: Int,
    initDay: Int,
    updatedTime: (year: Int, month: Int, day: Int) -> Unit
) {
    val date = remember { mutableStateOf("$initYear-${initMonth + 1}-$initDay") }
    val datePicker = DatePickerDialog(
        context,
        { _, year: Int, month: Int, day: Int ->
            updatedTime(year, month, day)
            date.value = "$year-${month + 1}-$day"
        },
        initYear, initMonth, initDay
    )
    Box(Modifier.clickable { datePicker.show() }) {
        Text(text = date.value)
    }

}

@Composable
private fun ShowTimePicker(
    context: Context,
    initHour: Int,
    initMinute: Int,
    select: (hour: Int, minute: Int) -> Unit
) {
    val time = remember { mutableStateOf("${convertHour(initHour)}:${convertMinute(initMinute)}") }
    val timePickerDialog = TimePickerDialog(
        context,
        { _, hour: Int, minute: Int ->
            time.value = "${convertHour(hour)}:${convertMinute(minute)}"
            select(hour, minute)
        }, initHour, initMinute, true
    )
    Box(Modifier.clickable { timePickerDialog.show() }) {
        Text(text = time.value)
    }
}

private fun convertHour(hour: Int) = when (hour) {
    0 -> "0"
    in 1..9 -> "0$hour"
    else -> hour.toString()
}

private fun convertMinute(minute: Int) = when (minute) {
    0 -> "00"
    in 1..9 -> "0$minute"
    else -> minute.toString()
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun ShowTimePickerUiPreview() {
    MoneyNoteTheme {
        TimeUi(System.currentTimeMillis()) { }
    }
}

@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ShowTimePickerNightUiPreview() {
    MoneyNoteTheme {
        TimeUi(System.currentTimeMillis()) { }
    }
}

@Composable
private fun UseType(
    useTypes: List<String>,
    selected: String?,
    openUseType: (Boolean) -> Unit,
    onAdd: (String) -> Unit
) {
    logger.info { "UseType selected:$selected" }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val useType = rememberUpdatedState(newValue = selected ?: "")
        Spinner(
            modifier = Modifier
                .weight(1f)
                .height(60.dp)
                .padding(end = 10.dp),
            items = useTypes,
            selectedItem = useType.value,
            onItemSelected = { onAdd(it) },
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
                    label = { Text("지출형태") }
                )
            },
            dropdownItemFactory = { type, _ ->
                Text(
                    modifier = Modifier.wrapContentSize(),
                    text = type
                )
            }
        )
        IconButton(
            onClick = { openUseType(true) },
            modifier = Modifier
                .clip(CircleShape)
                .background(Color.Red)
                .size(40.dp)
        ) {
            Icon(Icons.Rounded.Add, contentDescription = "추가")
        }
    }
}

@Composable
private fun Category(
    categoryList: List<String>,
    selected: String?,
    openCategory: (Boolean) -> Unit,
    onAdd: (String) -> Unit
) {
    logger.info { "Category select:$selected" }
    val category = rememberUpdatedState(newValue = selected ?: "")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spinner(
            modifier = Modifier
                .weight(1f)
                .height(60.dp)
                .padding(end = 10.dp),
            items = categoryList,
            selectedItem = category.value,
            onItemSelected = { onAdd(it) },
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
            dropdownItemFactory = { type, _ ->
                Text(
                    modifier = Modifier.wrapContentSize(),
                    text = type
                )
            }
        )
        IconButton(
            onClick = { openCategory(true) },
            modifier = Modifier
                .clip(CircleShape)
                .background(Color.Red)
                .size(40.dp)
        ) {
            Icon(Icons.Rounded.Add, contentDescription = "추가")
        }
    }
}

@Composable
private fun Amount(initAmount: String?, amountChanged: (String) -> Unit) {
    logger.info { "Amount amount:$initAmount" }
    val amount = rememberUpdatedState(newValue = initAmount ?: "")
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        value = amount.value,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
        onValueChange = { amountChanged(it.replace(",", "")) },
        label = { Text(text = "사용금액") }
    )
}

@Composable
private fun Comment(initComment: String?, commentChanged: (String) -> Unit) {
    logger.info { "Comment Comment:$initComment" }
    val comment = rememberUpdatedState(newValue = initComment ?: "")
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        value = comment.value,
        onValueChange = { commentChanged(it) },
        label = { Text("내용입력") }
    )
}

@Composable
private fun CompleteButton(
    context: Context,
    isEditable: Boolean,
    useTime: Long,
    useType: String,
    category: String,
    amount: String,
    comment: String,
    onComplete: (Use) -> Unit
) {
    Button(
        modifier = Modifier.fillMaxSize(),
        onClick = {
            if (useType.isEmpty()) {
                context.toast("지출타입을 선택하세요")
                return@Button
            }
            if (category.isEmpty()) {
                context.toast("카테고리를 선택하세요")
                return@Button
            }
            if (amount.isEmpty()) {
                context.toast("금액을 입력하세요")
                return@Button
            }
            if (amount.isEmpty() || amount.toInt() == 0) {
                context.toast("금액을 입력하세요")
                return@Button
            }
            onComplete(
                Use(
                    useType,
                    category,
                    amount.toInt(),
                    comment,
                    useTime
                )
            )
        }
    ) {
        if (isEditable) {
            Text(text = "업데이트")
        } else {
            Text(text = "입력")
        }
    }
}

@Composable
private fun RemoveButton(onRemove: () -> Unit) {
    Button(
        modifier = Modifier.fillMaxSize(),
        onClick = { onRemove() }
    ) {
        Text(text = "삭제")
    }
}

@Preview(showBackground = true)
@Composable
fun ExpensesContentPreview() {
    MoneyNoteTheme {
        ExpensesContent(
            false,
            MainViewModel(),
            { },
            { },
            System.currentTimeMillis()
        ) {}
    }
}

@Preview(showBackground = true)
@Composable
fun ExpensesContentEditablePreview() {
    MoneyNoteTheme {
        ExpensesContent(
            true,
            MainViewModel(),
            { },
            { },
            System.currentTimeMillis()
        ) {}
    }
}
