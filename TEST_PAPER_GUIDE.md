# MVL Android Test Paper Implementation Guide

## Project Goals
Develop a ride-booking mock application using Jetpack Compose, Hilt, and Google Maps.

## Architecture Guidelines
- **UI:** Single Activity + Jetpack Compose + Compose Navigation.
- **DI:** Hilt (for `ApiService`, `Repository`, and `ViewModel`).
- **Data:** Retrofit for networking, with a `MockBookService` implementation for offline/local testing.
- **Threading:** Kotlin Coroutines & Flow for reactive data streams.

## Features to Implement
1. **Map Interface:** 
   - Integrate Google Maps SDK.
   - Allow users to select/input source (A) and destination (B) locations.
   - Use Reverse Geocoding to show human-readable addresses.
2. **Booking Flow:**
   - Implement a flow to confirm booking (A -> B).
   - Mock the API response for creating a booking.
3. **History:**
   - Display a list of previous mock bookings.

## Key Files
- `NavGraph.kt`: Navigation routes and logic.
- `NetworkModule.kt`: Hilt configuration for Retrofit and Mocks.
- `MVLRepository.kt`: Central data hub.
- `MapViewModel.kt` & `HistoryViewModel.kt`: AAC ViewModels for UI state management.
