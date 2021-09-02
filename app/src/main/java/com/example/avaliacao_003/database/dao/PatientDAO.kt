package com.example.avaliacao_003.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.avaliacao_003.models.Patient

@Dao
interface PatientDAO {

    @Query("SELECT * FROM Patient")
    fun getPatients() : List<Patient>

    @Insert
    fun insertPatient(patient: Patient)

}