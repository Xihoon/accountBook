package com.xihoon.moneynote.ui.account

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xihoon.moneynote.repository.Repository
import com.xihoon.moneynote.ui.theme.MoneyNoteTheme
import com.xihoon.moneynote.ui.theme.Purple700
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
@Composable
fun AccountField(use: Repository.Use) {
    val format = SimpleDateFormat("yyyy-MM-dd hh:mm")
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Divider(
            color = Purple700,
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(use.category, Modifier.padding(10.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentHeight()
            ) {
                Text(
                    use.useType,
                    Modifier.padding(start = 10.dp, end = 10.dp)
                )
                Text(
                    format.format(Date(use.time)),
                    Modifier.padding(start = 10.dp, end = 10.dp)
                )
            }
            Text(use.account.toString(), Modifier.padding(10.dp))
        }
        Divider(
            color = Purple700,
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AccountFieldPreview() {
    MoneyNoteTheme {
        AccountField(
            Repository.Use(
                "type",
                "category",
                30000,
                "comment",
                System.currentTimeMillis()
            )
        )
    }
}
