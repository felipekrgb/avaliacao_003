package com.example.avaliacao_003.database

import androidx.room.TypeConverter
import com.example.avaliacao_003.models.Gender

class Converter {

    @TypeConverter
    fun toGender(value: String) = enumValueOf<Gender>(value)

    @TypeConverter
    fun fromGender(value: Gender) = value.type

}