package com.example.mvltestpaper.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mvltestpaper.data.model.LocationPoint
import com.example.mvltestpaper.ui.viewmodel.MapViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationSelectionScreen(
    isA: Boolean,
    viewModel: MapViewModel,
    onLocationSelected: (LocationPoint) -> Unit,
    onBack: () -> Unit
) {
    val cachedLocations by viewModel.cachedLocations.collectAsState(initial = emptyList<LocationPoint>())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isA) "Select Point A" else "Select Point B") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            items(cachedLocations) { location ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onLocationSelected(location)
                            onBack()
                        }
                        .padding(16.dp)
                ) {
                    Text(text = location.name, style = MaterialTheme.typography.bodyLarge)
                    Text(text = "AQI: ${location.aqi}", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                }
                Divider()
            }
            
            if (cachedLocations.isEmpty()) {
                item {
                    Box(modifier = Modifier.fillParentMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                        Text("No cached locations found.")
                    }
                }
            }
        }
    }
}
