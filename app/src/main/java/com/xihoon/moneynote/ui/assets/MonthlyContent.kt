package com.xihoon.moneynote.ui.assets

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xihoon.moneynote.ui.Utils
import com.xihoon.moneynote.ui.Utils.convert
import com.xihoon.moneynote.ui.Utils.logger
import com.xihoon.moneynote.ui.Utils.sum
import com.xihoon.moneynote.ui.composable.collectAsStateLifecycleAware
import com.xihoon.moneynote.ui.theme.BLUE
import com.xihoon.moneynote.ui.theme.MoneyNoteTheme
import com.xihoon.moneynote.ui.theme.RED
import com.xihoon.moneynote.viewmodel.MainViewModel

@Composable
fun MonthlyContent(viewModel: MainViewModel, pos: Int) {
    logger.info { "MonthlyContent pos:$pos" }
    val useList = viewModel.getTotalUsed(pos)
        .collectAsStateLifecycleAware(initial = emptyList())

    if (useList.value.isEmpty()) return
    Column(modifier = Modifier.padding(5.dp)) {
        Row(modifier = Modifier.padding(horizontal = 5.dp)) {
            Text(text = "수입 : ", style = MaterialTheme.typography.subtitle1)
            Text(
                text = Utils.decimalFormat.sum(null),
                modifier = Modifier
                    .weight(1f)
                    .wrapContentHeight(),
                style = MaterialTheme.typography.subtitle1,
                color = BLUE
            )
            Text(text = "지출 : ", style = MaterialTheme.typography.subtitle1)
            Box(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentHeight()
            ) {
                Text(
                    text = Utils.decimalFormat.convert(useList.value.sum()),
                    style = MaterialTheme.typography.subtitle1,
                    color = RED
                )
            }
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.Top
        ) {
            items(12) { index ->
                Card(
                    modifier = Modifier.padding(5.dp),
                    elevation = 5.dp
                ) {
                    Column(modifier = Modifier.padding(5.dp)) {
                        Text(
                            "${index + 1} 월",
                            style = TextStyle(fontSize = 10.sp, fontWeight = FontWeight.W700)
                        )
                        Row {
                            Text("수입: ")
                            Text("0원", color = BLUE)
                        }
                        Row {
                            Text("지출: ")
                            Text(
                                Utils.decimalFormat.convert(useList.value[index]),
                                color = RED
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun MonthlyContentPreview() {
    MoneyNoteTheme {
        MonthlyContent(MainViewModel(), 0)
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MonthlyContentNightPreview() {
    MoneyNoteTheme {
        MonthlyContent(MainViewModel(), 0)
    }
}