package com.jmadrigal.hackernews.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HackerNewsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}