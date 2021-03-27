package com.github.chartcoin.api.gson

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class GsonDeserializer : JsonDeserializer<LocalDateTime> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LocalDateTime {
        return if (json != null) {
            LocalDateTime.ofInstant(Instant.ofEpochSecond(json.asJsonPrimitive.asLong), TimeZone.getDefault().toZoneId())
        } else {
            LocalDateTime.now()
        }
    }
}