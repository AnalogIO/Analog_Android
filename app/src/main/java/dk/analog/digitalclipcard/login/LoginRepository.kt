package dk.analog.digitalclipcard.login

import dk.analog.digitalclipcard.BuildConfig
import dk.analog.digitalclipcard.backend.ApiResponse
import dk.analog.digitalclipcard.backend.getBackendServiceInstance
import dk.analog.digitalclipcard.backend.makeCall
import dk.analog.digitalclipcard.base.BaseApplication

data class Login(val email: String, val password: String, val version: String = BuildConfig.VERSION_NAME)
data class LoginResponse(val token: String)

object LoginRepository {

    fun login(email: String, pin: String, onResponse: (ApiResponse<LoginResponse>) -> Unit) {
        getBackendServiceInstance(BaseApplication.context).login(Login(email, pin)).makeCall { onResponse(it) }
    }
}
