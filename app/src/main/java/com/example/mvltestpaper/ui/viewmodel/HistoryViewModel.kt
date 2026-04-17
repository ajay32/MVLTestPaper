package com.example.mvltestpaper.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvltestpaper.data.model.BookResponse
import com.example.mvltestpaper.data.repository.MVLRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: MVLRepository
) : ViewModel() {

    var historyItems by mutableStateOf<List<BookResponse>>(emptyList())
    var isLoading by mutableStateOf(false)

    val totalCount: Int get() = historyItems.size
    val totalPrice: Double get() = historyItems.sumOf { it.price }

    init {
        fetchHistory()
    }

    private fun fetchHistory() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1 // 1-indexed

        viewModelScope.launch {
            isLoading = true
            historyItems = repository.getBookingHistory(year, month)
            isLoading = false
        }
    }
}
