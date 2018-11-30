package dk.analog.digitalclipcard.base

import android.app.Application
import android.content.Context
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.core.CrashlyticsCore
import dk.analog.digitalclipcard.BuildConfig
import io.fabric.sdk.android.Fabric

class BaseApplication : Application() {

    companion object {
        var instance: BaseApplication? = null
            private set

        val context: Context
            get() = instance!!.applicationContext
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        val crashlyticsKit = Crashlytics.Builder()
                .core(CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build())
                .build()
        Fabric.with(this, crashlyticsKit)
    }
}
