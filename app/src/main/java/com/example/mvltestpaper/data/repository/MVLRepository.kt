package com.example.mvltestpaper.data.repository

import com.example.mvltestpaper.data.api.AirQualityService
import com.example.mvltestpaper.data.api.BookService
import com.example.mvltestpaper.data.model.*
import com.example.mvltestpaper.domain.GetFormattedAddressUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.roundToInt

@Singleton
class MVLRepository @Inject constructor(
    private val airQualityService: AirQualityService,
    private val getFormattedAddressUseCase: GetFormattedAddressUseCase,
    private val bookService: BookService
) {
    private val _cachedLocations = MutableStateFlow<List<LocationPoint>>(emptyList())
    val cachedLocations: Flow<List<LocationPoint>> = _cachedLocations

    suspend fun getAirQuality(lat: Double, lng: Double): Int {
        return try {
            val request = GoogleAirQualityRequest(location = LatLngLiteral(lat, lng))
            val response = airQualityService.getAirQuality(
                apiKey = com.example.mvltestpaper.BuildConfig.MAPS_API_KEY,
                request = request
            )
            response.indexes?.firstOrNull()?.aqi ?: 0
        } catch (e: Exception) {
            0
        }
    }

    suspend fun getAddressName(lat: Double, lng: Double): String {
        val cached = _cachedLocations.value.find { isSameLocation(it.latitude, it.longitude, lat, lng) }
        if (cached != null) return cached.name

        val formattedName = getFormattedAddressUseCase(lat, lng)
        
        if (formattedName != "Error fetching address") {
            saveToCache(lat, lng, formattedName)
        }
        
        return formattedName
    }

    private fun saveToCache(lat: Double, lng: Double, name: String) {
        val aqiPlaceholder = 0
        cacheLocation(LocationPoint(lat, lng, aqiPlaceholder, name))
    }

    suspend fun createBook(a: LocationPoint, b: LocationPoint): BookResponse {
        return bookService.createBook(BookRequest(a, b))
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
