package dk.analog.digitalclipcard.utils

import java.util.regex.Pattern

object StringUtils {
    fun isValidEmail(email: String): Boolean {
        val p = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}")
        return p.matcher(email).matches()
    }
}