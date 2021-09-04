package com.example.avaliacao_003

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.avaliacao_003.database.AppDatabase
import com.example.avaliacao_003.database.dao.SpecialityDAO
import com.example.avaliacao_003.models.Speciality
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class SpecialityDAOTest {

    private lateinit var database: AppDatabase
    private lateinit var dao: SpecialityDAO


    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.specialityDAO()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insert_specialities_returns_true() {
        val speciality1 = Speciality(1L, "Cirurgião")
        val speciality2 = Speciality(2L, "Enfermeiro")
        val speciality3 = Speciality(3L, "Anestesista")

        dao.insertSpeciality(speciality1)
        dao.insertSpeciality(speciality2)
        dao.insertSpeciality(speciality3)

        val listOfSpecialities = mutableListOf(speciality1, speciality2, speciality3)

        val results = dao.getSpecialities()
        assertThat(results).containsExactlyElementsIn(listOfSpecialities)
    }

    @Test
    fun update_speciality_returns_true() {
        val speciality1 = Speciality(1L, "Cirurgião")
        val specialityUpdated = Speciality(speciality1.id, "Enfermeiro")

        dao.insertSpeciality(speciality1)
        dao.updateSpeciality(specialityUpdated)

        val result = dao.getSpeciality(speciality1.id)
        assertThat(result).isEqualTo(specialityUpdated)
    }

    @Test
    fun delete_speciality_returns_true() {
        val speciality1 = Speciality(1L, "Cirurgião")
        val speciality2 = Speciality(2L, "Enfermeiro")
        val speciality3 = Speciality(3L, "Anestesista")

        dao.insertSpeciality(speciality1)
        dao.insertSpeciality(speciality2)
        dao.insertSpeciality(speciality3)

        dao.deleteSpeciality(speciality3)

        val listOfSpecialities = listOf(speciality1, speciality2)

        val results = dao.getSpecialities()
        assertThat(results).containsExactlyElementsIn(listOfSpecialities)
    }

}