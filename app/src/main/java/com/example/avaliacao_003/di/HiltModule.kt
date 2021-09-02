package com.example.avaliacao_003.di

import android.content.Context
import com.example.avaliacao_003.database.AppDatabase
import com.example.avaliacao_003.database.dao.PatientDAO
import com.example.avaliacao_003.repository.PatientRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object HiltModule {

    @Provides
    fun providePatientDAO(@ApplicationContext context: Context) : PatientDAO {
        return AppDatabase.getDatabase(context).patientDAO()
    }

    @Provides
    fun providePatientRepository(dao: PatientDAO): PatientRepository = PatientRepository(dao)

}