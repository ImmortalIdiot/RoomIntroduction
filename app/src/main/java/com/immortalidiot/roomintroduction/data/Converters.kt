package com.immortalidiot.roomintroduction.data

import androidx.room.TypeConverter
import java.time.Instant

object Converters {
    @TypeConverter
    fun instantToLong(instant: Instant) = instant.epochSecond

    @TypeConverter
    fun longToInstant(value: Long): Instant = Instant.ofEpochSecond(value)
}
