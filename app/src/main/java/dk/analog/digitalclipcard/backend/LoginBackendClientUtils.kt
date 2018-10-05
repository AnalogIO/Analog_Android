package dk.analog.digitalclipcard.backend

import android.content.Context
import com.google.gson.Gson
import dk.analog.digitalclipcard.utils.UserUtils.getToken
import okhttp3.OkHttpClient
import retrofit2.Retrofit

private var loginInstance: LoginBackendService? = null

fun getLoginBackendServiceInstance(context: Context): LoginBackendService {
    val token = getToken(context)
    if (loginInstance == null || token != userToken) {
        userToken = token
        val retrofit = getNonAuthRetrofit(token, getGson())
        loginInstance = retrofit.create(LoginBackendService::class.java)
    }
    return loginInstance!!
}

private fun getNonAuthRetrofit(token: String?, gson: Gson): Retrofit {
    return getDefaultRetrofitBuilder(gson, getNonAuthClient(token))
            .build()
}

private fun getNonAuthClient(token: String?): OkHttpClient {
    return getDefaultClientBuilder(token).build()
}