package com.example.avaliacao_003.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.avaliacao_003.database.dao.MedicDAO
import com.example.avaliacao_003.database.dao.PatientDAO
import com.example.avaliacao_003.database.dao.SpecialityDAO
import com.example.avaliacao_003.models.Medic
import com.example.avaliacao_003.models.Patient
import com.example.avaliacao_003.models.Speciality

@Database(entities = [Patient::class, Speciality::class, Medic::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun patientDAO(): PatientDAO
    abstract fun specialityDAO(): SpecialityDAO
    abstract fun medicDAO(): MedicDAO

    companion object {
        fun getDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "avaliacao_app_db"
            ).allowMainThreadQueries().build()
        }
    }

}