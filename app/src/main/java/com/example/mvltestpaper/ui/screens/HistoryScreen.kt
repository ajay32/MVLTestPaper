package com.example.mvltestpaper.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mvltestpaper.data.model.LocationPoint
import com.example.mvltestpaper.ui.viewmodel.HistoryViewModel

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel = hiltViewModel(),
    onBack: () -> Unit,
    onHistoryItemSelected: (LocationPoint, LocationPoint) -> Unit
) {
    Scaffold(
        containerColor = Color.White
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            // Summary Header (Screen 4)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp, horizontal = 32.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Total Count", style = MaterialTheme.typography.labelLarge, color = Color.Gray)
                    Text(
                        text = "${viewModel.totalCount}",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Total Price", style = MaterialTheme.typography.labelLarge, color = Color.Gray)
                    Text(
                        text = "${viewModel.totalPrice.toInt()}",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Divider(thickness = 1.dp, color = Color.LightGray)

            // History List (Screen 5)
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(viewModel.historyItems.withIndex().toList()) { (index, item) ->
                    HistoryListItem(index, item.a.name) {
                        onHistoryItemSelected(item.a, item.b)
                    }
                    Divider(thickness = 0.5.dp, color = Color.LightGray)
                }
            }
        }
    }
}

@Composable
fun HistoryListItem(index: Int, name: String, onClick: () -> Unit) {
    val labels = listOf("A", "B", "C", "D", "E", "F", "G", "H")
    val label = labels.getOrElse(index) { " " }
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 16.dp, horizontal = 32.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.width(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = name,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black
        )
    }
}
