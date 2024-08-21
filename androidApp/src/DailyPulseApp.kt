package com.sun5066.dailypulse

import android.app.Application
import com.sun5066.dailypulse.di.initKoin

class DailyPulseApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}