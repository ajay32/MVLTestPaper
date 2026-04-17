package com.example.mvltestpaper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.mvltestpaper.ui.navigation.NavGraph
import com.example.mvltestpaper.ui.theme.MVLTestPaperTheme
import com.example.mvltestpaper.ui.viewmodel.MapViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MVLTestPaperTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val mapViewModel: MapViewModel = hiltViewModel()
                    NavGraph(navController = navController, mapViewModel = mapViewModel)
                }
            }
        }
    }
}
