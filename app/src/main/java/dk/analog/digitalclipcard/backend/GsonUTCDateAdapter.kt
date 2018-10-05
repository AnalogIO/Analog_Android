package dk.analog.digitalclipcard.backend

import com.google.gson.*
import java.lang.reflect.Type
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

const val DATE_FORMAT_BACKEND = 0
const val DATE_FORMAT_ANALOG = 1

class GsonUTCDateAdapter constructor(format: Int) : JsonSerializer<Date>, JsonDeserializer<Date> {

    private val dateFormat: DateFormat

    init {
        when (format) {
            DATE_FORMAT_BACKEND -> {
                dateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH)   //This is the format I need
                dateFormat.timeZone = TimeZone.getTimeZone("UTC")                        //This is the key line which converts the date to UTC which cannot be accessed with the default serializer
            }
            DATE_FORMAT_ANALOG -> {
                dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)   //This is the format I need
                dateFormat.timeZone = TimeZone.getTimeZone("Europe/Copenhagen")            //This is the key line which converts the date to UTC which cannot be accessed with the default serializer
            }
            else -> {
                dateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH)   //This is the format I need
                dateFormat.timeZone = TimeZone.getTimeZone("UTC")                        //This is the key line which converts the date to UTC which cannot be accessed with the default serializer
            }
        }
    }

    @Synchronized
    override fun serialize(date: Date, type: Type, jsonSerializationContext: JsonSerializationContext): JsonElement {
        return JsonPrimitive(dateFormat.format(date))
    }

    @Synchronized
    override fun deserialize(jsonElement: JsonElement, type: Type, jsonDeserializationContext: JsonDeserializationContext): Date {
        try {
            return dateFormat.parse(jsonElement.asString)
        } catch (e: ParseException) {
            throw JsonParseException(e)
        }

    }
}
