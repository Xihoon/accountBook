package com.xihoon.moneynote

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentActivity
import com.xihoon.moneynote.ui.MainUi
import com.xihoon.moneynote.ui.theme.MoneyNoteTheme
import com.xihoon.moneynote.viewmodel.MainViewModel

class MainActivity : FragmentActivity() {

    private val viewModel: MainViewModel by viewModels()
    private val logger by lazy { Logger() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoneyNoteTheme {
                logger.info { "MoneyNoteTheme" }
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    logger.info { "Surface" }
                    MainUi(viewModel)
                }
            }
        }
    }
}

