# Currency Viewer

<img src="https://github.com/TktkOkym/CurrencyViewer/blob/master/Screenshot_1.png" width="300" />

## Features
* Display list of Currency Items, containing Currency and its amount
* Update currency amount whenever user change input(EditText) or change currency(Spinner)
* Call [Currency Api](https://currencylayer.com/documentation) every 30 minutes by using Periodic WorkManager
* Save data to Room DB once getting currency api response
* Swipe to Refresh List to call api (replace worker)
* Display timestamp of last updated (which is being updated once a day)

## Architecture & Libraries
* [MVVM](https://github.com/googlesamples/android-architecture/tree/todo-mvvm-live-kotlin/) - Separate View from Model to have better **maintainability and testability**. More details in Implementation Details below.
* [Single Activity Concept](https://www.youtube.com/watch?v=2k8x8V77CrU) / [Navigation Architecture Components](https://developer.android.com/guide/navigation/) - To **reduce memory** usage especially when navigating to multiple pages (use replace instead of add when moving to other pages). Easy data sharing between views. Reduce boilerplate codes. 
* [ViewModel](https://developer.android.com/reference/androidx/lifecycle/ViewModel.html) - To store UI-related data. Data does not affected when **screen rotation** when using with LiveData
* [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) -  Lifecycle aware observable data holder, **no memory leaks**. No crashes due to stopped activities. Always up to date data 
* [ConstraintLayout](https://developer.android.com/reference/android/support/constraint/ConstraintLayout) - Allows to position and size widgets in a flexible way. Also can have **better performance** by having flatter view (with less hierarchy)
* [ListAdapter](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/ListAdapter) - Adapter for Recycler View, computing diffs between Lists on a background thread when submitted list is updated. Animation is provided when there's change in the list. Nearly identical to PagedListAdapter, it can be easily converted to it when Paging is needed.
* [DataBinding](https://developer.android.com/topic/libraries/data-binding/) - 2-way dataBinding and easy access to child view when there's nested layouts
* [Koin](https://github.com/InsertKoinIO/koin) - lightweight DI to have less dependency
* [Coroutine](https://kotlinlang.org/docs/reference/coroutines-overview.html) - lightweight thread, to do background work for tasks, such as Api Call, accessing RoomDB or heavy calculation
* [Retrofit2](https://square.github.io/retrofit/) - Type-safe HTTP client for Android. 
* [Android KTX](https://developer.android.com/kotlin/ktx) - reduce boilerplate Kotlin codes
* [AndroidX](https://developer.android.com/jetpack/androidx) - Fully replaces the Support Library by providing feature parity and new libraries. All new Support Library development will occur in the AndroidX library.
* [Espresso](https://developer.android.com/training/testing/espresso) - UI Test

## TODOs
* Error Handling - Properly handle network error in Worker
* Increase test coverage
* Improve Performance

## References
* Architecture and Coding style - https://developer.android.com/arch
