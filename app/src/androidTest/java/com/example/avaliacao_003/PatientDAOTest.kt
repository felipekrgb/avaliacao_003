package com.example.avaliacao_003

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.avaliacao_003.database.AppDatabase
import com.example.avaliacao_003.database.dao.PatientDAO
import com.example.avaliacao_003.models.Patient
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class PatientDAOTest {

    private lateinit var database: AppDatabase
    private lateinit var dao: PatientDAO

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.patientDAO()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insert_patient_returns_true() {
        val patient1 = Patient(1L, "José", 20, "Masculino")
        val patient2 = Patient(2L, "Olga", 50, "Feminino")
        val patient3 = Patient(3L, "Maria", 30, "Feminino")

        dao.insertPatient(patient1)
        dao.insertPatient(patient2)
        dao.insertPatient(patient3)

        val listOfPatients = mutableListOf(patient1, patient2, patient3)

        val results = dao.getPatients()
        assertThat(results).containsExactlyElementsIn(listOfPatients)
    }

    @Test
    fun update_patient_returns_true() {
        val patient1 = Patient(1L, "José", 20, "Masculino")
        val patientUpdated = Patient(patient1.id, "Rogério", 32, "Masculino")

        dao.insertPatient(patient1)
        dao.updatePatient(patientUpdated)

        val result = dao.getPatient(patient1.id)
        assertThat(result).isEqualTo(patientUpdated)
    }

    @Test
    fun delete_patient_returns_true() {
        val patient1 = Patient(1L, "José", 20, "Masculino")
        val patient2 = Patient(2L, "Olga", 50, "Feminino")
        val patient3 = Patient(3L, "Maria", 30, "Feminino")

        dao.insertPatient(patient1)
        dao.insertPatient(patient2)
        dao.insertPatient(patient3)

        dao.deletePatient(patient3)

        val listOfPatients = listOf(patient1, patient2)

        val results = dao.getPatients()
        assertThat(results).containsExactlyElementsIn(listOfPatients)
    }

}