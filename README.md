# MVL Android Test Paper

This project is an interview assignment for a ride-booking application mock, built with modern Android development practices.

## 📋 Project Requirements (What we had to do)

The goal was to implement a ride-booking flow with the following technical constraints:
- **Architecture**: Single Activity with **Jetpack Compose**.
- **State Management**: **AAC ViewModel**.
- **Dependency Injection**: **Hilt**.
- **Network**: **Retrofit** for API definitions.
- **Mocking**: A structure for mocking server responses (since no backend exists).
- **Concurrency**: **Kotlin Coroutines** for network and data streams.
- **Map**: **Google Maps SDK for Android**.

### Screen-Specific Requirements:
1. **Screen 1 (Map)**: Full-screen map with a center marker. Update AQI based on camera position. Sequence: "Set A" -> "Set B" -> "Book".
2. **Screen 2 (Nickname)**: Set an optional nickname (max 20 chars) for location A or B.
3. **Screen 3 (Result)**: Display booking summary (A, B, and Price) after calling `POST /books`.
4. **Screen 4 (History)**: Display usage history for the current month. Show total count and total price sum.
5. **Screen 5 (Optional - Location Selection)**: Select from previously searched/cached locations.

---

## ✅ Implementation Status (What we have done)

I have successfully implemented all mandatory requirements and several optional enhancements:

### 1. UI & Navigation
- **Single Activity**: The entire app runs in `MainActivity` using Compose Navigation.
- **Layout Fidelity**: The UI was updated to strictly match the provided design screenshots, including the yellow action buttons and clean list layouts.
- **Screen Flow**: 
    - **Map Screen**: Handles location permissions, real-time AQI updates, and the progressive "Set A/B/Book" logic.
    - **Nickname Screen**: Enforces the 20-character limit and correctly returns to the map to display nicknames.
    - **Result Screen**: Shows the summary data fetched from the mock API.
    - **History Screen**: Aggregates data for the current month with sum totals.
    - **Location Selection**: Allows picking from recently searched coordinates.

### 2. Data & Mocking
- **Repository Pattern**: `MVLRepository` acts as the single source of truth, managing network calls and local caching.
- **Location Caching**: Implemented a caching strategy that considers two locations identical if their coordinates match up to the **3rd decimal place**.
- **Mock Service**: `MockBookService` provides dynamic responses for booking and history requests, simulating network delays.
- **Address Formatting**: Implemented a custom parser for the Reverse Geocoding response that concatenates the two administrative entries with the highest `order` value (e.g., "Seocho District, Yangjae 2(i)-dong").

### 3. Advanced Integration
- **Hilt DI**: Properly configured to inject either real Retrofit services or mock implementations for testing.
- **History Navigation**: Tapping a history item navigates back to the Map screen with both Point A and Point B pre-filled and ready to re-book.

---

## 🛠 Technical Stack
- **Language**: Kotlin
- **UI**: Jetpack Compose
- **Navigation**: Compose Navigation
- **DI**: Hilt
- **Network**: Retrofit + OkHttp
- **Map**: Google Maps Compose Library
- **Async**: Coroutines & Flow
- **Image Loading**: Coil (Optional)

---

## 🚀 How to Run
1. Add your Google Maps API Key to `local.properties`:
   `MAPS_API_KEY=your_key_here`
2. Sync Project with Gradle Files.
3. Run the `app` module on an emulator or physical device.
