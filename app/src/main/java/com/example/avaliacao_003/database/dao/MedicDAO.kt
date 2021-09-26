package com.example.avaliacao_003.database.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.ABORT
import com.example.avaliacao_003.models.Medic
import com.example.avaliacao_003.models.MedicWithSpeciality

@Dao
interface MedicDAO {

    @Transaction
    @Query("SELECT * FROM Medic")
    fun getMedics(): List<MedicWithSpeciality>

    @Transaction
    @Query("SELECT * FROM Medic WHERE medic_id = :id")
    fun getMedic(id: Long): MedicWithSpeciality

    @Insert(onConflict = ABORT)
    fun insertMedic(medic: Medic)

    @Update
    fun updateMedic(medic: Medic)

    @Delete
    fun deleteMedic(medic: Medic)

}