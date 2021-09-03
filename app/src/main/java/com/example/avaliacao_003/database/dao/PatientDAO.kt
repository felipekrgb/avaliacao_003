package com.example.avaliacao_003.database.dao

import androidx.room.*
import com.example.avaliacao_003.models.Patient

@Dao
interface PatientDAO {

    @Query("SELECT * FROM Patient")
    fun getPatients(): List<Patient>

    @Query("SELECT * FROM Patient WHERE patient_id = :id")
    fun getPatient(id: Long): Patient

    @Insert
    fun insertPatient(patient: Patient)

    @Update
    fun updatePatient(patient: Patient)

    @Delete
    fun deletePatient(patient: Patient)

}