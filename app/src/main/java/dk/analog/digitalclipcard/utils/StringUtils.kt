package dk.analog.digitalclipcard.utils

import android.util.Base64
import java.nio.charset.Charset
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.regex.Pattern

object StringUtils {
    fun sha256(pin: String): String {
        try {
            val md = MessageDigest.getInstance("SHA-256")
            md.update(pin.toByteArray(Charset.forName("UTF-8")))
            return Base64.encodeToString(md.digest(), Base64.NO_WRAP)
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        return ""
    }

    fun isValidEmail(email: String): Boolean {
        val p = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}")
        return p.matcher(email).matches()
    }

    fun addOrdinalIndicator(i: Int) =
            "$i" +  if (i in 11..13) "th"
                    else when (i % 10){
                    1 -> "st"
                    2 -> "nd"
                    3 -> "rd"
                    else -> "th"
            }

}