package org.sopt.stamp

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.soloader.SoLoader
import dagger.hilt.android.HiltAndroidApp
import org.sopt.stamp.data.local.SoptampDataStoreModule

@HiltAndroidApp
class App : Application() {

    private lateinit var dataStore: SoptampDataStoreModule
    fun getDataStore(): SoptampDataStoreModule = dataStore
    override fun onCreate() {
        super.onCreate()
        soptampApplication = this
        dataStore = SoptampDataStoreModule(this)
        initFlipper()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    private fun initFlipper() {
        SoLoader.init(this, false)
        if (BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(this)) {
            AndroidFlipperClient.getInstance(this).apply {
                addPlugin(InspectorFlipperPlugin(this@App, DescriptorMapping.withDefaults()))
                addPlugin(networkFlipperPlugin)
            }.start()
        }
    }

    companion object {
        val networkFlipperPlugin = NetworkFlipperPlugin()
        private lateinit var soptampApplication: App
        fun getInstance(): App = soptampApplication
    }
}
