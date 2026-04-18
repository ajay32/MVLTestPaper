package com.example.mvltestpaper.data.model

import com.google.gson.annotations.SerializedName

data class LocationPoint(
    val latitude: Double,
    val longitude: Double,
    val aqi: Int,
    val name: String,
    val nickname: String? = null
) {
    val displayName: String
        get() = if (!nickname.isNullOrBlank()) nickname else name
}

data class BookRequest(
    val a: LocationPoint,
    val b: LocationPoint
)

data class BookResponse(
    val a: LocationPoint,
    val b: LocationPoint,
    val price: Double,
    val id: String? = null
)

// Reverse Geocoding Models (BigDataCloud)
data class ReverseGeocodeResponse(
    val latitude: Double,
    val longitude: Double,
    val localityInfo: LocalityInfo
)

data class LocalityInfo(
    val administrative: List<AdministrativeArea>,
    val informative: List<InformativeArea>
)

data class AdministrativeArea(
    val order: Int,
    val adminLevel: Int,
    val name: String,
    val description: String? = null
)

data class InformativeArea(
    val order: Int,
    val name: String,
    val description: String? = null
)

// Google Air Quality Models
data class GoogleAirQualityRequest(
    val location: LatLngLiteral
)

data class LatLngLiteral(
    val latitude: Double,
    val longitude: Double
)

data class GoogleAirQualityResponse(
    val indexes: List<AqiIndex>? = null
)

data class AqiIndex(
    val code: String,
    val displayName: String,
    val aqi: Int,
    val category: String? = null
)
