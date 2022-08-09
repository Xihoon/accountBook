package com.xihoon.moneynote.ui.assets

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xihoon.moneynote.ui.Utils.dateFormat
import com.xihoon.moneynote.ui.Utils.timeFormat
import com.xihoon.moneynote.ui.source.Use
import com.xihoon.moneynote.ui.source.UseItem
import com.xihoon.moneynote.ui.theme.MoneyNoteTheme
import com.xihoon.moneynote.ui.theme.RED
import java.text.DecimalFormat
import java.util.*

@Composable
fun AssetsField(item: UseItem, onClick: (UseItem) -> Unit) {
    val use = item.use
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 10.dp, bottom = 10.dp)
            .clickable { onClick(item) }
    ) {
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
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    Text(
                        use.useType,
                        Modifier.padding(start = 10.dp, end = 10.dp)
                    )
                    Text(
                        timeFormat.format(Date(use.time)),
                        Modifier.padding(start = 10.dp, end = 10.dp)
                    )
                }
                Text(
                    use.comment,
                    Modifier.padding(start = 10.dp, end = 10.dp),
                    maxLines = 2
                )
            }
            val decimalFormat = DecimalFormat("#,###")
            Text(
                decimalFormat.format(use.amount),
                Modifier.padding(10.dp),
                color = RED
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun AccountFieldPreview() {
    MoneyNoteTheme {
        AssetsField(
            UseItem(
                "item", Use(
                    "type",
                    "category",
                    30000,
                    "comment",
                    System.currentTimeMillis()
                )
            )
        ) {}
    }
}
