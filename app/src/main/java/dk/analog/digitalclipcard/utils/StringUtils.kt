package dk.analog.digitalclipcard.utils

import java.util.regex.Pattern

object StringUtils {
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