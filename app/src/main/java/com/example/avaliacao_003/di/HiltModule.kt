package com.example.avaliacao_003.di

import android.content.Context
import com.example.avaliacao_003.database.AppDatabase
import com.example.avaliacao_003.database.dao.MedicDAO
import com.example.avaliacao_003.database.dao.PatientDAO
import com.example.avaliacao_003.database.dao.SpecialityDAO
import com.example.avaliacao_003.repository.MedicRepository
import com.example.avaliacao_003.repository.PatientRepository
import com.example.avaliacao_003.repository.SpecialityRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object HiltModule {

    @Provides
    fun providePatientDAO(@ApplicationContext context: Context): PatientDAO {
        return AppDatabase.getDatabase(context).patientDAO()
    }

    @Provides
    fun provideSpecialityDAO(@ApplicationContext context: Context): SpecialityDAO {
        return AppDatabase.getDatabase(context).specialityDAO()
    }

    @Provides
    fun provideMedicDAO(@ApplicationContext context: Context): MedicDAO {
        return AppDatabase.getDatabase(context).medicDAO()
    }

    @Provides
    fun providePatientRepository(dao: PatientDAO): PatientRepository = PatientRepository(dao)

    @Provides
    fun provideSpecialityRepository(dao: SpecialityDAO): SpecialityRepository =
        SpecialityRepository(dao)

    @Provides
    fun provideMedicRepository(dao: MedicDAO): MedicRepository = MedicRepository(dao)

}