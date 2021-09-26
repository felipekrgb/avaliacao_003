package com.example.avaliacao_003.models

import androidx.room.*
import java.io.Serializable

@Entity
data class Schedule(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "schedule_id")
    var id: Long = 0,
    val patientFK: Long,
    val medicFK: Long,
) : Serializable

data class ScheduleWithMedicAndPatient(
    @Embedded
    val schedule: Schedule,

    @Relation(
        parentColumn = "patientFK",
        entityColumn = "patient_id"
    )
    val patient: Patient,

    @Relation(
        parentColumn = "medicFK",
        entityColumn = "medic_id"
    )
    val medic: Medic,
) : Serializable
