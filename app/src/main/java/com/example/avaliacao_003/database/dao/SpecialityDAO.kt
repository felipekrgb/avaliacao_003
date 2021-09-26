package com.example.avaliacao_003.database.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.ABORT
import com.example.avaliacao_003.models.Speciality

@Dao
interface SpecialityDAO {

    @Query("SELECT * FROM Speciality")
    fun getSpecialities(): List<Speciality>

    @Query("SELECT * FROM Speciality WHERE speciality_id = :id")
    fun getSpeciality(id: Long): Speciality

    @Insert(onConflict = ABORT)
    fun insertSpeciality(speciality: Speciality )

    @Update
    fun updateSpeciality(speciality: Speciality)

    @Delete
    fun deleteSpeciality(speciality: Speciality)

}