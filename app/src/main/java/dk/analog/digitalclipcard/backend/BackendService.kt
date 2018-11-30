package dk.analog.digitalclipcard.backend

import dk.analog.digitalclipcard.login.Login
import dk.analog.digitalclipcard.login.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface BackendService {
    @POST("account/login")
    fun login(@Body login: Login): Call<LoginResponse>

    /*@POST("account/register")
    fun registerUser(@Body user: Register): Call<MessageResponse>*/
}
