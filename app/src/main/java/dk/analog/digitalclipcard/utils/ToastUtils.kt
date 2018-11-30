package dk.analog.digitalclipcard.utils

import android.content.Context
import android.widget.Toast

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.showToast(resId: Int) {
    showToast(getString(resId))
}
