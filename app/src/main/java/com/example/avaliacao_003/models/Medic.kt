package com.example.avaliacao_003.models

import androidx.room.*

@Entity
data class Medic(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "medic_id")
    val id: Long = 0,

    @ColumnInfo(name = "medic_name")
    val name: String,

    val specialityFK: Long
)


data class MedicWithSpeciality(
    @Embedded
    val medic: Medic?,
    @Relation(
        parentColumn = "specialityFK",
        entityColumn = "speciality_id"
    )
    val speciality: Speciality?
)
