package com.example.avaliacao_003.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Patient(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "patient_id")
    val id: Long = 0,

    @ColumnInfo(name = "patient_name")
    val name: String,

    @ColumnInfo(name = "patient_age")
    val age: Int,

    @ColumnInfo(name = "patient_gender")
    val gender: Gender
) : Serializable {
    override fun toString(): String {
        return name
    }
}

enum class Gender(val id: Int, val type: String) {
    MALE(0, "Masculino"),
    FEMALE(1, "Feminino"),
}