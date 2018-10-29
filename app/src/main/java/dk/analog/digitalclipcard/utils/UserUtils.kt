package dk.analog.digitalclipcard.utils

import android.content.Context
import android.content.Intent
import android.preference.PreferenceManager
import dk.analog.digitalclipcard.login.LoginMainActivity
import dk.analog.digitalclipcard.preferences.USER_TOKEN

object UserUtils {
    fun logOut(context: Context) {
        val intent = Intent(context, LoginMainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
    }

    fun getToken(context: Context): String? {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(USER_TOKEN, "")
    }
}