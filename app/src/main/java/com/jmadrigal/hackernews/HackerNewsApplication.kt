package com.jmadrigal.hackernews

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HackerNewsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}