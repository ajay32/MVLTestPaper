package com.example.mvltestpaper.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvltestpaper.data.model.BookResponse
import com.example.mvltestpaper.data.model.LocationPoint
import com.example.mvltestpaper.data.repository.MVLRepository
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val repository: MVLRepository
) : ViewModel() {

    var currentAqi by mutableStateOf(0)
    
    var pointA by mutableStateOf<LocationPoint?>(null)
    var pointB by mutableStateOf<LocationPoint?>(null)
    
    val buttonText: String
        get() = when {
            pointA == null -> "Set A"
            pointB == null -> "Set B"
            else -> "Book"
        }

    var bookingResult by mutableStateOf<BookResponse?>(null)
    var isLoading by mutableStateOf(false)

    val cachedLocations = repository.cachedLocations

    private var fetchJob: Job? = null

    fun onCameraMove(latLng: LatLng) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            delay(300) // Debounce camera moves
            currentAqi = repository.getAirQuality(latLng.latitude, latLng.longitude)
        }
    }

    fun onVButtonClicked(currentLatLng: LatLng) {
        viewModelScope.launch {
            if (pointA != null && pointB != null) {
                book()
                return@launch
            }

            isLoading = true
            val aqi = repository.getAirQuality(currentLatLng.latitude, currentLatLng.longitude)
            val name = repository.getAddressName(currentLatLng.latitude, currentLatLng.longitude)
            val point = LocationPoint(currentLatLng.latitude, currentLatLng.longitude, aqi, name)

            if (pointA == null) {
                pointA = point
            } else {
                pointB = point
            }
            isLoading = false
        }
    }

    fun updateNickname(isA: Boolean, nickname: String) {
        if (isA) {
            pointA = pointA?.copy(nickname = nickname)
        } else {
            pointB = pointB?.copy(nickname = nickname)
        }
    }

    fun book() {
        val a = pointA ?: return
        val b = pointB ?: return
        viewModelScope.launch {
            isLoading = true
            bookingResult = repository.createBook(a, b)
            isLoading = false
        }
    }

    fun reset() {
        pointA = null
        pointB = null
        bookingResult = null
    }

    fun setFromHistory(a: LocationPoint, b: LocationPoint) {
        viewModelScope.launch {
            isLoading = true
            val aAqi = repository.getAirQuality(a.latitude, a.longitude)
            val bAqi = repository.getAirQuality(b.latitude, b.longitude)
            
            pointA = a.copy(aqi = aAqi)
            pointB = b.copy(aqi = bAqi)
            isLoading = false
        }
    }
}
