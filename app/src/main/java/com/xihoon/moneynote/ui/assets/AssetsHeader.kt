package com.xihoon.moneynote.ui.assets

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xihoon.moneynote.ui.Utils
import com.xihoon.moneynote.ui.Utils.sum
import com.xihoon.moneynote.ui.source.UseItem
import com.xihoon.moneynote.ui.theme.MoneyNoteTheme

@Composable
fun AssetsHeader(useList: State<List<UseItem>?>, openExpenseUI: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(text = "μμ : ", style = MaterialTheme.typography.subtitle1)
        Text(
            text = Utils.decimalFormat.sum(null),
            modifier = Modifier
                .weight(1f)
                .wrapContentHeight(),
            style = MaterialTheme.typography.subtitle1,
            color = Color.Blue
        )
        Text(text = "μ§μΆ : ", style = MaterialTheme.typography.subtitle1)
        Box(
            modifier = Modifier
                .weight(1f)
                .wrapContentHeight(),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(
                text = Utils.decimalFormat.sum(useList.value?.map { it.use }),
                style = MaterialTheme.typography.subtitle1,
                color = Color.Red
            )
        }
        InputButton(openExpenseUI)
    }
}

@Composable
private fun InputButton(openAccount: () -> Unit) {
    Button(
        onClick = { openAccount() },
        modifier = Modifier.wrapContentSize()
    ) {
        Text("μλ ₯")
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun HeaderPreview() {
    MoneyNoteTheme {
        Row(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            AssetsHeader(remember { mutableStateOf(emptyList()) }) {}
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HeaderNightUiPreview() {
    MoneyNoteTheme {
        Row(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            AssetsHeader(remember { mutableStateOf(emptyList()) }) {}
        }
    }
}