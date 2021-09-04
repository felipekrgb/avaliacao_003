package com.example.avaliacao_003.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Speciality(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "speciality_id")
    val id: Long = 0,

    @ColumnInfo(name = "speciality_name")
    val name: String
) {
    override fun toString(): String {
        return name
    }
}