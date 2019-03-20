package dk.analog.digitalclipcard.backend

import dk.analog.digitalclipcard.login.Login
import dk.analog.digitalclipcard.login.LoginResponse
import dk.analog.digitalclipcard.login.RegisterInfo
import dk.analog.digitalclipcard.profile.AccountResponse
import dk.analog.digitalclipcard.purchase.PurchaseResponse
import dk.analog.digitalclipcard.ticketHistory.TicketResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface BackendService {
    @POST("account/login")
    fun login(@Body login: Login): Call<LoginResponse>

    @POST("account/register")
    fun registerUser(@Body registerInfo: RegisterInfo): Call<ResponseBody>

    @GET("Account")
    fun getAccountDetails() : Call<AccountResponse>

    @GET("Purchases")
    fun getPurchases() : Call<List<PurchaseResponse>>

    @GET("Tickets")
    fun getTickets(@Query("used") isUsed: Boolean): Call<List<TicketResponse>>

    @GET("Products")
    fun getProducts(): Call<List<TicketResponse>>
}
