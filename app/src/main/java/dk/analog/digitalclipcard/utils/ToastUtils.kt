package dk.analog.digitalclipcard.utils

import android.content.Context
import android.widget.Toast

fun Context.makeToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.makeToast(resId: Int) {
    makeToast(getString(resId))
}
