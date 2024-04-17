package com.sopt.now

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NowSoptApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
