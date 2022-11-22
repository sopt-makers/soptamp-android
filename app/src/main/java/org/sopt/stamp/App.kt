package org.sopt.stamp

import android.app.Application
import com.airbnb.mvrx.mocking.MockableMavericks
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        MockableMavericks.initialize(this)
    }
}
