package com.xihoon.moneynote.ui.assets

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.xihoon.moneynote.ui.Utils.logger
import com.xihoon.moneynote.ui.assets.detail.DetailUi
import com.xihoon.moneynote.viewmodel.MainViewModel

@Composable
fun DailyContent(viewModel: MainViewModel, pos: Int) {
    logger.info { "AccountMain pos:$pos" }
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = MAIN) {
        logger.info { "NavHost" }
        composable(MAIN) {
            logger.info { "navControl main Ui" }
            AssetsUi(viewModel, navController, pos)
        }

        composable("${DETAIL}/{useKey}") { backStackEntry ->
            logger.info { "navControl detail Ui" }
            DetailUi(viewModel, navController, backStackEntry)
        }
    }
}

fun NavController.moveDetail(useKey: String) = navigate("${DETAIL}/$useKey") { popUpTo(MAIN) }

private const val MAIN = "main"
private const val DETAIL = "detail"