package dk.analog.digitalclipcard.base

import android.app.Application
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.core.CrashlyticsCore
import dk.analog.digitalclipcard.BuildConfig
import io.fabric.sdk.android.Fabric

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val crashlyticsKit = Crashlytics.Builder()
                .core(CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build())
                .build()
        Fabric.with(this, crashlyticsKit)
    }
}
