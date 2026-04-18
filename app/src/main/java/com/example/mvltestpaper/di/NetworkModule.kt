package com.example.mvltestpaper.di

import com.example.mvltestpaper.data.api.AirQualityService
import com.example.mvltestpaper.data.api.BookService
import com.example.mvltestpaper.data.api.GeocodingService
import com.example.mvltestpaper.data.api.MockBookService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Provides
    @Singleton
    fun provideAirQualityService(okHttpClient: OkHttpClient): AirQualityService {
        return Retrofit.Builder()
            .baseUrl("https://airquality.googleapis.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AirQualityService::class.java)
    }

    @Provides
    @Singleton
    fun provideGeocodingService(okHttpClient: OkHttpClient): GeocodingService {
        return Retrofit.Builder()
            .baseUrl("https://api.bigdatacloud.net/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GeocodingService::class.java)
    }

    @Provides
    @Singleton
    fun provideBookService(): BookService {
        // Using Mock implementation as required
        return MockBookService()
    }
}
