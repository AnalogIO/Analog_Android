package dk.analog.digitalclipcard.backend

import dk.analog.digitalclipcard.login.Login
import dk.analog.digitalclipcard.login.LoginResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginBackendService {

    @POST("account/login")
    fun login(@Body login: Login): Single<LoginResponse>

    /*@POST("account/register")
    fun registerUser(@Body user: Register): Call<MessageResponse>*/

}
