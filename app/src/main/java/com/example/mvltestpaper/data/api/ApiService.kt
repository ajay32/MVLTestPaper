package com.example.mvltestpaper.data.api

import com.example.mvltestpaper.data.model.*
import retrofit2.http.*

interface AirQualityService {
    @GET("feed/geo:{lat};{lng}/")
    suspend fun getAirQuality(
        @Path("lat") lat: Double,
        @Path("lng") lng: Double,
        @Query("token") token: String
    ): AirQualityResponse
}

interface GeocodingService {
    @GET("data/reverse-geocode-client")
    suspend fun reverseGeocode(
        @Query("latitude") lat: Double,
        @Query("longitude") lng: Double,
        @Query("localityLanguage") lang: String = "en"
    ): ReverseGeocodeResponse
}

interface BookService {
    @POST("books")
    suspend fun createBook(@Body request: BookRequest): BookResponse

    @GET("books")
    suspend fun getBooks(
        @Query("year") year: Int,
        @Query("month") month: Int
    ): List<BookResponse>
}
