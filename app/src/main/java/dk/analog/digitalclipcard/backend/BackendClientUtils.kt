package dk.analog.digitalclipcard.backend

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dk.analog.digitalclipcard.BuildConfig
import dk.analog.digitalclipcard.base.BaseApplication
import dk.analog.digitalclipcard.utils.UserUtils
import dk.analog.digitalclipcard.utils.UserUtils.getToken
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit


private const val URL = "https://api.hopper.dk/"

internal var userToken: String? = null

private var instance: BackendService? = null

private val errorHandlerInterceptor = Interceptor { chain ->
    val request = chain.request()
    val response = chain.proceed(request)

    if (response.code() == 401) {
        UserUtils.logOut(BaseApplication.applicationContext())
    }
    response
}

fun getBackendServiceInstance(context: Context): BackendService {
    val token = getToken(context)
    if (instance == null || token != userToken) {
        userToken = token
        val retrofit = getDefaultRetrofitInstance(token, getGson())
        instance = retrofit.create(BackendService::class.java)
    }
    return instance!!
}

internal fun getDefaultRetrofitInstance(token: String?, gson: Gson): Retrofit {
    return getDefaultRetrofitBuilder(gson, getDefaultClient(token)).build()
}

internal fun getDefaultRetrofitBuilder(gson: Gson, client: OkHttpClient): Retrofit.Builder {
    return Retrofit.Builder()
            .baseUrl(URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
}

internal fun getGson(): Gson {
    return GsonBuilder()
            .registerTypeAdapter(Date::class.java, GsonUTCDateAdapter(DATE_FORMAT_BACKEND))
            .create()
}

internal fun getDefaultClient(token: String?): OkHttpClient {
    return getDefaultClientBuilder(token)
            .addInterceptor(errorHandlerInterceptor)
            .build()
}

internal fun getDefaultClientBuilder(token: String?): OkHttpClient.Builder {
    val builder = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)

    val loggingInterceptor = HttpLoggingInterceptor()
    if (BuildConfig.DEBUG) {
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    } else {
        loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
    }
    builder.addInterceptor(loggingInterceptor)

    if (token != null && token.isNotEmpty()) {
        builder.authenticator { _, response ->
            if (response.request().header("Authorization") != null) {
                return@authenticator null // Give up, we've already attempted to authenticate.
            }
            response.request().newBuilder()
                    .header("Authorization", token)
                    .build()
        }
    }

    return builder
}