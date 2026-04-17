# Interview Assignment: Implementation Details & Discussion Points

## 1. Implementation Summary
This project is a Single-Activity Android application built with **Jetpack Compose**. It demonstrates a ride-hailing flow (Point A to Point B) using **Google Maps SDK**.

### Key Components:
- **Dependency Injection:** Hilt is used for providing Singleton instances of Repositories and Services.
- **Networking & Mocking:** 
    - Used **Retrofit** for API interface definitions.
    - Implemented a **Mocking layer** (`MockBookService`) to simulate network responses without a backend. This allows testing of business logic in an isolated environment.
- **Architecture:** Follows **MVVM**. The `MapViewModel` manages the UI state for the map and booking flow, while `MVLRepository` acts as the single source of truth.
- **Reactive UI:** Used `StateFlow` and `collectAsStateWithLifecycle` to ensure the UI reacts to data changes efficiently and safely.

## 2. Technical Decisions (Be prepared to explain these)
- **Why Hilt?** Standardized DI in Android, reduces boilerplate, and makes testing easier by allowing us to swap real services with mocks.
- **Why Coroutines?** Clean, readable asynchronous code for network calls and map updates.
- **Single Activity:** Optimized for Jetpack Compose navigation, leading to better performance and simpler state management.
- **Mocking Strategy:** By implementing the `BookService` interface in `MockBookService`, we keep the `MVLRepository` completely decoupled from whether the data is real or mocked.

## 3. Implementation Process
1. **Infrastructure:** Set up Hilt and Navigation first.
2. **Map Integration:** Integrated Google Maps and added camera movement listeners to update AQI and address info.
3. **Data Layer:** Defined Retrofit interfaces and created the Repository to bridge the UI and Data sources.
4. **UI/UX:** Built Compose screens for Map, Nickname input, and History.

## 4. Potential Improvements (Optional/Future Work)
- **Error Handling:** Adding custom exceptions and UI feedback (Snackbars) for failed API calls.
- **Room Database:** Persisting history locally so it survives app restarts (currently in-memory mock).
- **Advanced Map Features:** Adding Polyline between Point A and Point B.
