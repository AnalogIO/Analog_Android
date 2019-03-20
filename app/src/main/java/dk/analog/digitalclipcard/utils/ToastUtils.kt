package dk.analog.digitalclipcard.utils

import android.content.Context
import android.util.Log
import android.widget.Toast

fun Context.showToast(message: String?) {
    if (message != null) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    } else {
        Log.e("ToastUtils", "message is null in showToast!")
    }
}

fun Context.showToast(resId: Int) {
    showToast(getString(resId))
}
