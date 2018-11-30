package dk.analog.digitalclipcard.utils

import android.content.Context
import android.content.Intent
import dk.analog.digitalclipcard.login.LoginMainActivity
import dk.analog.digitalclipcard.preferences.STORED_EMAIL
import dk.analog.digitalclipcard.preferences.USER_TOKEN
import dk.analog.digitalclipcard.preferences.preferences
import dk.analog.digitalclipcard.preferences.putValue

fun Context.logOut() {
    val intent = Intent(this, LoginMainActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    startActivity(intent)
}

fun Context.getToken(): String {
    return preferences.getString(USER_TOKEN, "")!!
}

fun Context.putToken(token: String) {
    preferences.putValue(USER_TOKEN, token)
}

fun Context.getStoredEmail(): String {
    return preferences.getString(STORED_EMAIL, "")!!
}

fun Context.putStoredEmail(email: String) {
    preferences.putValue(STORED_EMAIL, email)
}
