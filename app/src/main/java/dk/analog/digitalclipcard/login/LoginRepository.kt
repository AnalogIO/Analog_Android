package dk.analog.digitalclipcard.login

import dk.analog.digitalclipcard.BuildConfig
import dk.analog.digitalclipcard.backend.ApiResponse
import dk.analog.digitalclipcard.backend.getBackendServiceInstance
import dk.analog.digitalclipcard.backend.makeCall
import dk.analog.digitalclipcard.base.BaseApplication
import okhttp3.ResponseBody

data class Login(val email: String, val password: String, val version: String = BuildConfig.VERSION_NAME)
data class LoginResponse(val token: String)
data class RegisterInfo(val email: String, val name: String, val password: String, val programmeId: Int)

object LoginRepository {

    fun login(email: String, pin: String, onResponse: (ApiResponse<LoginResponse>) -> Unit) {
        getBackendServiceInstance(BaseApplication.context).login(Login(email, pin)).makeCall { onResponse(it) }
    }

    fun register(email: String, name: String, pin: String, onResponse: (ApiResponse<ResponseBody>) -> Unit) {
        getBackendServiceInstance(BaseApplication.context).registerUser(RegisterInfo(email, name, pin, 0)).makeCall { onResponse(it) }
    }
}
