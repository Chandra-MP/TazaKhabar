package com.example.tazakhabar

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

//The line @HiltAndroidApp is an annotation that is used to mark the Application class as the entry point for Hilt
// dependency injection in the app. This means that Hilt will generate code to initialize the dependency graph
// for the entire app at this point.

@HiltAndroidApp
class tazaKhabarHiltEntryPoint:Application() {

}