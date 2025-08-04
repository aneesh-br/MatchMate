# MatchMate

MatchMate is a matrimonial-style Android app that simulates match cards similar to Shaadi.com. Users can accept or decline profiles, and their choices are saved locally, allowing offline functionality with sync on reconnection.



##  Features:

- API Integration with [RandomUser.me](https://randomuser.me)
- Match cards with user image, name, age, and location
- Accept / Decline functionality per profile
- Local persistence using Room DB
- Offline mode with action queue syncing when online
- Pull-to-refresh for fresh data
- LazyColumn paging: auto-load next 10 users on scroll
- Jetpack Compose UI



## Libraries Used:


Retrofit for Networking / API calls
Room for Local DB                       
Glide / Coil for Image loading                  
ViewModel for State management               
StateFlow for Reactive data handling         
Material 3 for UI components                  
PullRefresh (Compose) for Pull to refresh gesture 
Lifecycle + Coroutine for Async + Lifecycle aware
Accompanist / Material PullRefresh for Paging/refreshing

## Architecture:

**MVVM + Repository Pattern**


View (Compose UI)
   
ViewModel (MainViewModel)
   
Repository (UserRepositoryImpl)
   
Room DB + Retrofit API


- ViewModel observes DB via Flow
- Repository abstracts local + remote data sources
- Offline Accept/Decline actions are queued and flushed when online


## How to Run

1. Clone the repo:
```bash
git clone https://github.com/your_username/MatchMate.git
```

2. Open in **Android Studio**.

3. Make sure your emulator or device has an internet connection.

4. Run the app (`Shift + F10`)



## Notes

- Swipe-to-refresh pulls fresh data and clears old cache
- App clears local DB when it's removed from background
- Pagination auto-loads the next 10 profiles when the user scrolls down


Built with ❤️ by Aneesh.
