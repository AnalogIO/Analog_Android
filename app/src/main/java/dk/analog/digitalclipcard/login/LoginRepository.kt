package dk.analog.digitalclipcard.login

import android.content.Context
import dk.analog.digitalclipcard.BuildConfig
import dk.analog.digitalclipcard.backend.getLoginBackendServiceInstance


class LoginRepository {
    fun login(context: Context, email: String, pin: String) {
        val service = getLoginBackendServiceInstance(context)
        val res = service.login(Login(email, pin, BuildConfig.VERSION_NAME))

    }
}