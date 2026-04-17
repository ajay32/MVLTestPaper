package com.example.mvltestpaper.data.repository

import com.example.mvltestpaper.data.api.AirQualityService
import com.example.mvltestpaper.data.api.BookService
import com.example.mvltestpaper.data.api.GeocodingService
import com.example.mvltestpaper.data.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.roundToInt

@Singleton
class MVLRepository @Inject constructor(
    private val airQualityService: AirQualityService,
    private val geocodingService: GeocodingService,
    private val bookService: BookService
) {
    // Optional: Local cache for searched locations
    private val _cachedLocations = MutableStateFlow<List<LocationPoint>>(emptyList())
    val cachedLocations: Flow<List<LocationPoint>> = _cachedLocations

    private val AQI_TOKEN = "demo" // User should replace with their token

    suspend fun getAirQuality(lat: Double, lng: Double): Int {
        return try {
            val response = airQualityService.getAirQuality(lat, lng, AQI_TOKEN)
            response.data.aqi
        } catch (e: Exception) {
            0
        }
    }

    suspend fun getAddressName(lat: Double, lng: Double): String {
        return try {
            // Check cache first
            val cached = _cachedLocations.value.find { isSameLocation(it.latitude, it.longitude, lat, lng) }
            if (cached != null) return cached.name

            val response = geocodingService.reverseGeocode(lat, lng)
            val adminAreas = response.localityInfo.administrative
                .sortedByDescending { it.order }
            
            val formattedName = if (adminAreas.size >= 2) {
                "${adminAreas[1].name}, ${adminAreas[0].name}"
            } else if (adminAreas.isNotEmpty()) {
                adminAreas[0].name
            } else {
                "Unknown Location"
            }

            // Cache it
            saveToCache(lat, lng, formattedName)
            
            formattedName
        } catch (e: Exception) {
            "Error fetching address"
        }
    }

    private fun saveToCache(lat: Double, lng: Double, name: String) {
        val aqiPlaceholder = 0 // Will be updated if AQI is fetched
        cacheLocation(LocationPoint(lat, lng, aqiPlaceholder, name))
    }

    suspend fun createBook(a: LocationPoint, b: LocationPoint): BookResponse {
        val response = bookService.createBook(BookRequest(a, b))
        return response
    }

    suspend fun getBookingHistory(year: Int, month: Int): List<BookResponse> {
        return bookService.getBooks(year, month)
    }

    private fun cacheLocation(point: LocationPoint) {
        val currentList = _cachedLocations.value.toMutableList()
        val isAlreadyCached = currentList.any {
            isSameLocation(it.latitude, it.longitude, point.latitude, point.longitude)
        }
        if (!isAlreadyCached) {
            currentList.add(point)
            _cachedLocations.value = currentList
        }
    }

    private fun isSameLocation(lat1: Double, lng1: Double, lat2: Double, lng2: Double): Boolean {
        val precision = 1000.0
        return (lat1 * precision).roundToInt() == (lat2 * precision).roundToInt() &&
               (lng1 * precision).roundToInt() == (lng2 * precision).roundToInt()
    }
}
