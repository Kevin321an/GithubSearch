# GitHub User Search App 

Communicates with the public Github API to display information about a specific user.

## Install and test the compiled APK  by the link below: 
[![](https://img.shields.io/badge/Github_Search-APK-green.svg?style=for-the-badge&logo=android)](https://github.com/Kevin321an/GithubSearch/releases/download/gitSearch.apk)

## Screenshots
![gitSearch](https://github.com/Kevin321an/GithubSearch/assets/12762835/aae9bb87-932c-4ab7-852b-a3804fa27909) ![image](https://github.com/Kevin321an/GithubSearch/assets/12762835/860a31f9-4e97-466f-912e-40b67b3dfc91)

# Architecture
An MVVM pattern is implemented in a single module, using a remote data source and memory cache 
![image](https://github.com/Kevin321an/GithubSearch/assets/12762835/e43f79a0-dec4-4c5f-89bb-b80004761a53)


# Technologies and libraries: 

- User Interface built with **[Jetpack Compose](https://developer.android.com/jetpack/compose)**
- Reactive UIs using **[Flow](https://developer.android.com/kotlin/flow)** and **[coroutines](https://kotlinlang.org/docs/coroutines-overview.html)** for asynchronous operations.
- A presentation layer that contains a Compose screen (View) and a ViewModel per screen.
- A data layer with a repository and one remote data source; use memory cache the result
- Dependency injection using [Hilt](https://developer.android.com/training/dependency-injection/hilt-android).
- Use Retrofit for REST-based web service and [Kotlin Serialization](https://kotlinlang.org/docs/serialization.html) for JSON decoding 
# Testing
- A collection of unit tests for two ViewModels
- A collection of Android tests with Compose UI testing 

# Building environment: 
- Android Studio Hedgehog | 2023.1.1
- Runtime version: 17.0.7+0-b2043.56-10550314 amd64
- VM: OpenJDK 64-Bit Server VM by JetBrains s.r.o.
- Windows 11.0
