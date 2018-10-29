package dk.analog.digitalclipcard.preferences

import android.content.SharedPreferences
import androidx.lifecycle.LiveData

class SharedPreferenceLiveData<T>(private val sharedPrefs: SharedPreferences, private val key: String, private val defValue: T) :
        LiveData<T>(), SharedPreferences.OnSharedPreferenceChangeListener {

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == this.key) {
            value = getValueFromPreferences(key, defValue)
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun getValueFromPreferences(key: String, defValue: T): T {
        val pref: T? = sharedPrefs.all[key] as? T // to avoid accidental mutability
        return pref ?: defValue
    }

    override fun onActive() {
        super.onActive()
        value = getValueFromPreferences(key, defValue)
        sharedPrefs.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onInactive() {
        sharedPrefs.unregisterOnSharedPreferenceChangeListener(this)
        super.onInactive()
    }
}