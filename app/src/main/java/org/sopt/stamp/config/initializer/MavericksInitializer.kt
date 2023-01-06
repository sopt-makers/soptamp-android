package org.sopt.stamp.config.initializer

import android.content.Context
import androidx.startup.Initializer
import com.airbnb.mvrx.mocking.MockableMavericks

class MavericksInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        MockableMavericks.initialize(context)
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}
