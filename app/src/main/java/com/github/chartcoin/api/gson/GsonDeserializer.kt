package com.github.chartcoin.api.gson

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.util.*

class GsonDeserializer : JsonDeserializer<Date> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Date {
        if (json != null) {
            return Date(json.asJsonPrimitive.asLong * 1000)
        } else {
            return Date()
        }
    }
}