package dk.analog.digitalclipcard.backend

import dk.analog.digitalclipcard.login.Login
import dk.analog.digitalclipcard.login.LoginResponse
import dk.analog.digitalclipcard.login.RegisterInfo
import dk.analog.digitalclipcard.profile.AccountResponse
import dk.analog.digitalclipcard.purchase.PurchaseResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface BackendService {
    @POST("account/login")
    fun login(@Body login: Login): Call<LoginResponse>

    @POST("account/register")
    fun registerUser(@Body registerInfo: RegisterInfo): Call<ResponseBody>

    @GET("Account")
    fun getAccountDetails() : Call<AccountResponse>

    @GET("Purchases")
    fun getPurchasese() : Call<PurchaseResponse>
}
