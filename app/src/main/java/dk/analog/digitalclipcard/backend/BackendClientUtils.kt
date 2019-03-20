package dk.analog.digitalclipcard.backend

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dk.analog.digitalclipcard.BuildConfig
import dk.analog.digitalclipcard.R
import dk.analog.digitalclipcard.base.BaseApplication
import dk.analog.digitalclipcard.utils.getToken
import dk.analog.digitalclipcard.utils.logOut
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.*
import java.util.concurrent.TimeUnit

internal var userToken: String? = null

private var instance: BackendService? = null

private val errorHandlerInterceptor = Interceptor { chain ->
    val request = chain.request()
    val response = chain.proceed(request)

    if (response.code() == 401) {
        BaseApplication.context.logOut()
    }
    response
}

private fun authInterceptor(token: String): Interceptor {
    return Interceptor { chain ->
        val request = if (token.isNotEmpty()) {
            chain.request().newBuilder()
                    .header("Authorization", "Bearer ${token}")
                    .build()
        } else {
            chain.request()
        }

        chain.proceed(request)
    }
}

fun getBackendServiceInstance(context: Context): BackendService {
    val token = context.getToken()
    if (instance == null || token != userToken) {
        userToken = token
        val url = context.getString(R.string.baseUrl)
        val retrofit = getDefaultRetrofitInstance(token, getGson(), url)
        instance = retrofit.create<BackendService>()
    }
    return instance!!
}

private fun getDefaultRetrofitInstance(token: String, gson: Gson, url: String): Retrofit {
    return getDefaultRetrofitBuilder(gson, getDefaultClient(token), url).build()
}

private fun getDefaultRetrofitBuilder(gson: Gson, client: OkHttpClient, url: String): Retrofit.Builder {
    return Retrofit.Builder()
            .baseUrl(url)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
}

private fun getGson(): Gson {
    return GsonBuilder()
            .registerTypeAdapter(Date::class.java, GsonUTCDateAdapter(DATE_FORMAT_BACKEND))
            .create()
}

private fun getDefaultClient(token: String): OkHttpClient {
    return getDefaultClientBuilder(token)
            .addInterceptor(errorHandlerInterceptor)
            .build()
}

private fun getDefaultClientBuilder(token: String): OkHttpClient.Builder {
    val builder = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)

    builder.addInterceptor(authInterceptor(token))
    val loggingInterceptor = HttpLoggingInterceptor()
    if (BuildConfig.DEBUG) {
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    } else {
        loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
    }
    builder.addInterceptor(loggingInterceptor)

    return builder
}
