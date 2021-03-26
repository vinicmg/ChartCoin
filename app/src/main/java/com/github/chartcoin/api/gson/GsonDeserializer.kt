package com.github.chartcoin.api.gson

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type


class GsonDeserializer<T> : JsonDeserializer<T> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): T {
        val content: JsonElement = json?.asJsonObject?.get("content")!!

        return Gson().fromJson(content, typeOfT)
    }
}