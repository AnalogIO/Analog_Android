package dk.analog.digitalclipcard.login

import android.content.Context
import dk.analog.digitalclipcard.backend.getBackendServiceInstance

data class Login(val email: String, val password: String, val version: String)
data class LoginResponse(val token: String)

class LoginRepository {

    fun login(context: Context, email: String, pin: String) {
        val service = getBackendServiceInstance(context)

    }
}
