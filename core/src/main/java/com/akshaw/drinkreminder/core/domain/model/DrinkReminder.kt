package com.akshaw.drinkreminder.core.domain.model

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encodeToString
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import java.time.DayOfWeek
import java.time.LocalTime

@Serializable
data class DrinkReminder(
    
    val id: Long = -1,
    @Serializable(with = LocalTimeAsStringSerializer::class)
    var time: LocalTime,
    var isReminderOn: Boolean = true,
    var activeDays: List<DayOfWeek>

) {
    fun encodeToJsonString() = Json.encodeToString(this)
    
    companion object {
        fun decodeFromJsonString(json: String) = Json.decodeFromString<DrinkReminder>(json)
    }
}


object LocalTimeAsStringSerializer : KSerializer<LocalTime> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Date", PrimitiveKind.STRING)
    override fun serialize(encoder: Encoder, value: LocalTime) = encoder.encodeString(value.toString())
    override fun deserialize(decoder: Decoder): LocalTime = LocalTime.parse(decoder.decodeString())
}
