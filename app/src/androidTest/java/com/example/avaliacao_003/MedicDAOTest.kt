package com.example.avaliacao_003

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.avaliacao_003.database.AppDatabase
import com.example.avaliacao_003.database.dao.MedicDAO
import com.example.avaliacao_003.database.dao.SpecialityDAO
import com.example.avaliacao_003.models.Medic
import com.example.avaliacao_003.models.MedicWithSpeciality
import com.example.avaliacao_003.models.Speciality
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class MedicDAOTest {

    private lateinit var database: AppDatabase
    private lateinit var dao: MedicDAO
    private lateinit var daoSpeciality: SpecialityDAO

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.medicDAO()
        daoSpeciality = database.specialityDAO()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insert_medics_returns_true() {
        val speciality1 = Speciality(1L, "Cirurgião")
        val speciality2 = Speciality(2L, "Enfermeiro")
        val speciality3 = Speciality(3L, "Anestesista")

        daoSpeciality.insertSpeciality(speciality1)
        daoSpeciality.insertSpeciality(speciality2)
        daoSpeciality.insertSpeciality(speciality3)

        val medic1 = Medic(1L, "Roberto", speciality1.id)
        val medic2 = Medic(2L, "Augusta", speciality2.id)
        val medic3 = Medic(3L, "Maria", speciality3.id)

        dao.insertMedic(medic1)
        dao.insertMedic(medic2)
        dao.insertMedic(medic3)

        val medicWithSpeciality1 = MedicWithSpeciality(medic = medic1, speciality = speciality1)
        val medicWithSpeciality2 = MedicWithSpeciality(medic = medic2, speciality = speciality2)
        val medicWithSpeciality3 = MedicWithSpeciality(medic = medic3, speciality = speciality3)

        val listOfMedicsWithSpeciality =
            listOf(medicWithSpeciality1, medicWithSpeciality2, medicWithSpeciality3)

        val results = dao.getMedics()
        assertThat(results).containsExactlyElementsIn(listOfMedicsWithSpeciality)
    }

    @Test
    fun updated_medic_returns_true() {
        val speciality1 = Speciality(1L, "Cirurgião")
        daoSpeciality.insertSpeciality(speciality1)

        val medic1 = Medic(1L, "Roberto", speciality1.id)
        dao.insertMedic(medic1)

        val specialityUpdated = Speciality(2L, "Anestesista")
        daoSpeciality.insertSpeciality(specialityUpdated)

        val medicUpdated = Medic(medic1.id, "Maria", specialityUpdated.id)

        dao.updateMedic(medicUpdated)

        val result = dao.getMedic(medic1.id)
        assertThat(result.medic).isEqualTo(medicUpdated)
    }

    @Test
    fun delete_medic_returns_true() {
        val speciality1 = Speciality(1L, "Cirurgião")
        val speciality2 = Speciality(2L, "Enfermeiro")
        val speciality3 = Speciality(3L, "Anestesista")

        daoSpeciality.insertSpeciality(speciality1)
        daoSpeciality.insertSpeciality(speciality2)
        daoSpeciality.insertSpeciality(speciality3)

        val medic1 = Medic(1L, "Roberto", speciality1.id)
        val medic2 = Medic(2L, "Augusta", speciality2.id)
        val medic3 = Medic(3L, "Maria", speciality3.id)

        dao.insertMedic(medic1)
        dao.insertMedic(medic2)
        dao.insertMedic(medic3)

        val medic1FromDB = dao.getMedic(medic1.id)
        val medic2FromDB = dao.getMedic(medic2.id)
        val medic3FromDB = dao.getMedic(medic3.id)

        dao.deleteMedic(medic3)

        val results = dao.getMedics()
        assertThat(results).contains(medic1FromDB)
        assertThat(results).contains(medic2FromDB)
        assertThat(results).doesNotContain(medic3FromDB)
    }

}