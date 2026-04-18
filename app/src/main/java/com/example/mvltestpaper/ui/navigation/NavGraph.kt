package com.example.mvltestpaper.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mvltestpaper.ui.screens.*
import com.example.mvltestpaper.ui.viewmodel.MapViewModel

sealed class Screen(val route: String) {
    object Map : Screen("map")
    object Nickname : Screen("nickname/{isA}") {
        fun createRoute(isA: Boolean) = "nickname/$isA"
    }
    object Result : Screen("result")
    object History : Screen("history")
    object LocationSelection : Screen("locationSelection/{isA}") {
        fun createRoute(isA: Boolean) = "locationSelection/$isA"
    }
}

@Composable
fun NavGraph(
    navController: NavHostController,
    mapViewModel: MapViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Map.route
    ) {
        composable(Screen.Map.route) {
            MapScreen(
                viewModel = mapViewModel,
                onNavigateToNickname = { isA ->
                    navController.navigate(Screen.Nickname.createRoute(isA))
                },
                onNavigateToResult = {
                    navController.navigate(Screen.Result.route)
                },
                onNavigateToHistory = {
                    navController.navigate(Screen.History.route)
                },
                onNavigateToLocationSelection = { isA ->
                    navController.navigate(Screen.LocationSelection.createRoute(isA))
                }
            )
        }
        composable(
            route = Screen.Nickname.route,
            arguments = listOf(navArgument("isA") { type = NavType.BoolType })
        ) { backStackEntry ->
            val isA = backStackEntry.arguments?.getBoolean("isA") ?: true
            NicknameScreen(
                isA = isA,
                viewModel = mapViewModel,
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.Result.route) {
            ResultScreen(
                viewModel = mapViewModel,
                onNavigateToHistory = {
                    navController.navigate(Screen.History.route)
                },
                onBackToMap = {
                    mapViewModel.reset()
                    navController.popBackStack(Screen.Map.route, false)
                }
            )
        }
        composable(Screen.History.route) {
            HistoryScreen(
                onBack = { 
                    mapViewModel.reset()
                    navController.navigate(Screen.Map.route) {
                        popUpTo(Screen.Map.route) { inclusive = true }
                    }
                },
                onHistoryItemSelected = { a, b ->
                    mapViewModel.setFromHistory(a, b)
                    navController.navigate(Screen.Map.route) {
                        popUpTo(Screen.Map.route) { inclusive = true }
                    }
                }
            )
        }
        composable(
            route = Screen.LocationSelection.route,
            arguments = listOf(navArgument("isA") { type = NavType.BoolType })
        ) { backStackEntry ->
            val isA = backStackEntry.arguments?.getBoolean("isA") ?: true
            LocationSelectionScreen(
                isA = isA,
                viewModel = mapViewModel,
                onLocationSelected = { location ->
                    if (isA) mapViewModel.pointA = location else mapViewModel.pointB = location
                },
                onBack = { navController.popBackStack() }
            )
        }
    }
}
