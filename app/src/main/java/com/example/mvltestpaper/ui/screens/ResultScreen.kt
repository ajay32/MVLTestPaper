package com.example.mvltestpaper.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mvltestpaper.ui.viewmodel.MapViewModel

@Composable
fun ResultScreen(
    viewModel: MapViewModel,
    onNavigateToHistory: () -> Unit,
    onBackToMap: () -> Unit
) {
    val result = viewModel.bookingResult

    Scaffold(
        containerColor = Color.White
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(32.dp)
                .fillMaxSize()
        ) {
            if (result != null) {
                // Point A Info
                SummaryItem("A", result.a.name, result.a.aqi, result.a.nickname ?: "")
                
                Divider(modifier = Modifier.padding(vertical = 16.dp), color = Color.LightGray)

                // Point B Info
                SummaryItem("B", result.b.name, result.b.aqi, result.b.nickname ?: "")

                Spacer(modifier = Modifier.weight(1f))

                // Price Section
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text("price", style = MaterialTheme.typography.titleLarge, color = Color.Black)
                    Text(
                        "${result.price.toInt()}",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Button(
                    onClick = onNavigateToHistory,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC107))
                ) {
                    Text("V", fontSize = 24.sp, color = Color.Black)
                }
            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No booking data available.")
                }
            }
        }
    }
}

@Composable
fun SummaryItem(label: String, name: String, aqi: Int, nickname: String) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(label, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(16.dp))
            Text(name, style = MaterialTheme.typography.titleMedium)
        }
        Text(
            "aqi  $aqi",
            modifier = Modifier.padding(start = 32.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
        Text(
            "nickname  $nickname",
            modifier = Modifier.padding(start = 32.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
    }
}
