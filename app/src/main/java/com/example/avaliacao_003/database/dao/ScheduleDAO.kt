package com.example.avaliacao_003.database.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.ABORT
import com.example.avaliacao_003.models.Gender
import com.example.avaliacao_003.models.Schedule
import com.example.avaliacao_003.models.ScheduleWithMedicAndPatient

@Dao
interface ScheduleDAO {

    @Transaction
    @Query("SELECT * FROM Schedule s, Medic m WHERE s.medicFK = m.medic_id ORDER BY m.medic_name")
    fun getSchedules(): List<ScheduleWithMedicAndPatient>

    @Transaction
    @Query("SELECT * FROM Schedule where schedule_id = :id")
    fun getScheduleByID(id: Long): ScheduleWithMedicAndPatient

    @Transaction
    @Query("SELECT * FROM Schedule inner join Medic on medic.medic_id = medicFK WHERE medic.medic_name LIKE '%' || :medicName || '%'")
    fun getSchedulesByMedicName(medicName: String): List<ScheduleWithMedicAndPatient>

    @Transaction
    @Query("SELECT * FROM Schedule inner join Medic on medic.medic_id = medicFK where medic.specialityFK = :id")
    fun getSchedulesBySpeciality(id: Long): List<ScheduleWithMedicAndPatient>

    @Transaction
    @Query("SELECT * FROM Schedule inner join Patient on patient.patient_id = patientFK where patient.patient_name like '%' || :patientName || '%'")
    fun getSchedulesByPatient(patientName: String): List<ScheduleWithMedicAndPatient>

    @Transaction
    @Query("SELECT * FROM Schedule inner join Patient on patient.patient_id = patientFK where patient_gender = :gender")
    fun getSchedulesByGender(gender: Gender): List<ScheduleWithMedicAndPatient>

    @Insert(onConflict = ABORT)
    fun insertSchedule(schedule: Schedule)

    @Update
    fun updateSchedule(schedule: Schedule)

    @Delete
    fun deleteSchedule(schedule: Schedule)


}