package com.example.mvltestpaper.domain

import com.example.mvltestpaper.data.api.GeocodingService
import javax.inject.Inject

class GetFormattedAddressUseCase @Inject constructor(
    private val geocodingService: GeocodingService
) {
    suspend operator fun invoke(lat: Double, lng: Double): String {
        return try {
            val response = geocodingService.reverseGeocode(lat, lng)
            
            // Business Logic for filtering administrative areas
            val filteredAreas = response.localityInfo.administrative
                .filterNot { 
                    it.name.contains("Taluk", ignoreCase = true) || 
                    it.name.contains("Division", ignoreCase = true) ||
                    it.name.contains("Subdivision", ignoreCase = true) ||
                    it.name.contains("Zone", ignoreCase = true)
                }
                .sortedByDescending { it.order }
            
            if (filteredAreas.size >= 2) {
                "${filteredAreas[0].name}, ${filteredAreas[1].name}"
            } else if (filteredAreas.isNotEmpty()) {
                filteredAreas[0].name
            } else {
                val fallback = response.localityInfo.administrative.sortedByDescending { it.order }
                if (fallback.size >= 2) "${fallback[0].name}, ${fallback[1].name}"
                else fallback.firstOrNull()?.name ?: "Unknown Location"
            }
        } catch (e: Exception) {
            "Error fetching address"
        }
    }
}
