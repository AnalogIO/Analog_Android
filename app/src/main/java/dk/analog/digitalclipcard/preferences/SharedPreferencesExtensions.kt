package dk.analog.digitalclipcard.preferences

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.gson.GsonBuilder

fun <T> SharedPreferences.liveData(key: String, defValue: T): SharedPreferenceLiveData<T> {
    return SharedPreferenceLiveData(this, key, defValue)
}

inline fun <reified T> SharedPreferences.getJson(key: String): T? {
    val json = getString(key, null) ?: return null
    return GsonBuilder().create().fromJson(json, T::class.java)
}

fun <T> SharedPreferences.putValue(key: String, value: T) {
    edit().apply {
        when (value) {
            is Int -> putInt(key, value)
            is Long -> putLong(key, value)
            is Float -> putFloat(key, value)
            is String -> putString(key, value)
            is Boolean -> putBoolean(key, value)
        }
    }.apply()
}

val Context.preferences: SharedPreferences
    get() = PreferenceManager.getDefaultSharedPreferences(this)