# Drink_Reminder

This repository contains "Drink Reminder" app source code. 

Its purpose being, to quickly demonstrate Android, Kotlin and software development in general. The main focus of this project is:
- Setup and Gradle configuration, 
- Gradle modules,
- Clean architecture,
- Clean code,
- Best practices,
- Testing and 
- All those other must know goodies.

Below is a list of goodies that are being showcased:

1. Architectural Patterns
    1. [Modularization](https://medium.com/google-developer-experts/modularizing-android-applications-9e2d18f244a0) 
    ```(Modularize the App Horizontally by Features)```
    2. [Navigation Component](https://developer.android.com/guide/navigation) ```(Navigation refers to the interactions that 
    allow users to navigate across, into, and back out from the different pieces of content within your app)```
    3. [Clean Architecture](https://8thlight.com/blog/uncle-bob/2012/08/13/the-clean-architecture.html) ```(By employing 
    clean architecture, you can design applications with very low coupling and independent of technical implementation 
    details, such as databases and frameworks. That way, the application becomes easy to maintain and flexible to change. 
    It also becomes intrinsically testable.)```
    4. [Result](https://arturdryomov.dev/posts/designing-errors-with-kotlin/) ```(Add Result for Error Handling)```
2. UI Patterns
    1. [MVVM](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel) ```(Model View ViewModel)```
    2. [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) ```(Architecture Components
    ViewModel Class)```
3. Kotlin
    1. [Kotlin](https://kotlinlang.org/) ```(A modern programming language that makes developers happier)```
    2. [Kotlin Symbol Processing](https://kotlinlang.org/docs/ksp-overview.html) ```(Kotlin Symbol Processing (KSP) is
    an API that you can use to develop lightweight compiler plugins. KSP provides a simplified compiler plugin API that
    leverages the power of Kotlin while keeping the learning curve at a minimum. Compared to kapt, annotation processors
    that use KSP can run up to 2 times faster)```
    3. [Kotlin Coroutines](https://kotlinlang.org/docs/reference/coroutines.html) ```(Coroutines simplify asynchronous
    programming by putting the complications into libraries. The logic of the program can be expressed sequentially in a
    coroutine, and the underlying library will figure out the asynchrony for us)```
    4. [Asynchronous Flow](https://kotlinlang.org/docs/reference/coroutines/flow.html) ```(Suspending functions
    asynchronously returns a single value, but how can we return multiple asynchronously computed values? This is where
    Kotlin Flows come in)```
4. Android Support
    1. [AndroidX](https://developer.android.com/topic/libraries/support-library/androidx-overview) ```(A new package
    structure to make it clearer which packages are bundled with the Android operating system, and which are packaged with
    your app's APK)```
    2. [Android KTX](https://developer.android.com/kotlin/ktx) ```(Android KTX is a set of Kotlin extensions that is part of
    the Android Jetpack family)```
    3. [Room Persistence Library](https://developer.android.com/topic/libraries/architecture/room) ```(The Room persistence
    library provides an abstraction layer over SQLite to allow for more robust database access while harnessing the full
    power of SQLite)```
5. Android UI
    1. [Material Components](https://github.com/material-components/material-components-android) ```(Modular and customizable 
    Material Design UI components for Android)```
    2. [Dark Theme](https://developer.android.com/guide/topics/ui/look-and-feel/darktheme) ```(Dark theme is available in 
    Android 10 (API level 29) and higher)```
    3. [Jetpack Compose](https://developer.android.com/jetpack/compose) ```(Jetpack Compose simplifies and accelerates UI
    development on Android. Quickly bring your app to life with less code, powerful tools, and intuitive Kotlin APIs)```
6. Tests
    1. [JUnit4](https://junit.org/junit4) ```(A programmer-oriented testing framework for Java)```
    2. [MockK](https://mockk.io) ```(MockK is a mocking library for Kotlin)```
7. Build
    1. [Gradle Kotlin DSL](https://github.com/gradle/kotlin-dsl) ```(Kotlin language support for Gradle build scripts)```
    2. [Gradle Versions](https://github.com/ben-manes/gradle-versions-plugin) ```(Gradle plugin to discover dependency
    updates)```

# Build Variant

## Beta Build
The first thing that you need to do in order to be able to build and run the beta build variant is to locate the 'local.properties' 
file (and if it doesn't exist, create it). Then, add the following properties:
```
# Beta Build Singing Configs
beta.upload.storeFile = <YOUR.STORE.FILE.PATH>
beta.upload.storePassword = <YOUR.STORE.PASSWORD>
beta.upload.keyAlias = <YOUR.KEY.ALIAS>
beta.upload.keyPassword = <YOUR.KEY.PASSWORD>
```

# Screenshots

![screenshots](https://github.com/Akshay-kumar79/Drink_Reminder/assets/58460601/2fc86c53-f286-492b-8eff-cca6b36e6a65)

**THANK YOU**
