package dk.analog.digitalclipcard.backend

import dk.analog.digitalclipcard.login.Login
import dk.analog.digitalclipcard.login.LoginResponse
import dk.analog.digitalclipcard.profile.AccountResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface BackendService {
    @POST("account/login")
    fun login(@Body login: Login): Call<LoginResponse>

    /*@POST("account/register")
    fun registerUser(@Body user: Register): Call<MessageResponse>*/

    @GET("Account")
    fun getAccountDetails() : Call<AccountResponse>
}
