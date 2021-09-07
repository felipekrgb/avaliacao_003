package com.example.avaliacao_003

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.avaliacao_003.database.AppDatabase
import com.example.avaliacao_003.database.dao.MedicDAO
import com.example.avaliacao_003.database.dao.PatientDAO
import com.example.avaliacao_003.database.dao.ScheduleDAO
import com.example.avaliacao_003.database.dao.SpecialityDAO
import com.example.avaliacao_003.models.*
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class ScheduleDAOTest {

    private lateinit var database: AppDatabase
    private lateinit var dao: ScheduleDAO
    private lateinit var daoPatient: PatientDAO
    private lateinit var daoSpeciality: SpecialityDAO
    private lateinit var daoMedic: MedicDAO

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.scheduleDAO()
        daoPatient = database.patientDAO()
        daoSpeciality = database.specialityDAO()
        daoMedic = database.medicDAO()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insert_schedule_returns_true() {
        val patient1 = Patient(1L, "Paciente1", 23, Gender.MALE)
        val patient2 = Patient(2L, "Paciente2", 23, Gender.FEMALE)
        daoPatient.insertPatient(patient1)
        daoPatient.insertPatient(patient2)

        val speciality1 = Speciality(1L, "Especialidade1")
        val speciality2 = Speciality(2L, "Especialidade2")
        daoSpeciality.insertSpeciality(speciality1)
        daoSpeciality.insertSpeciality(speciality2)

        val medic1 = Medic(1L, "Medico1", speciality1.id)
        val medic2 = Medic(2L, "Medico2", speciality2.id)
        daoMedic.insertMedic(medic1)
        daoMedic.insertMedic(medic2)

        val schedule1 = Schedule(1L, patient1.id, medic1.id)
        val schedule2 = Schedule(2L, patient2.id, medic2.id)
        dao.insertSchedule(schedule1)
        dao.insertSchedule(schedule2)

        val results = dao.getSchedules()
        assertThat(results).hasSize(2)
    }

    @Test
    fun get_by_medic_name() {
        val patient1 = Patient(1L, "Paciente1", 23, Gender.MALE)
        val patient2 = Patient(2L, "Paciente2", 23, Gender.FEMALE)
        daoPatient.insertPatient(patient1)
        daoPatient.insertPatient(patient2)

        val speciality1 = Speciality(1L, "Especialidade1")
        val speciality2 = Speciality(2L, "Especialidade2")
        daoSpeciality.insertSpeciality(speciality1)
        daoSpeciality.insertSpeciality(speciality2)

        val medic1 = Medic(1L, "Medico1", speciality1.id)
        val medic2 = Medic(2L, "Medico2", speciality2.id)
        daoMedic.insertMedic(medic1)
        daoMedic.insertMedic(medic2)

        val schedule1 = Schedule(1L, patient1.id, medic1.id)
        val schedule2 = Schedule(2L, patient2.id, medic2.id)
        dao.insertSchedule(schedule1)
        dao.insertSchedule(schedule2)

        val results = dao.getSchedulesByMedicName("Medico")
        assertThat(results).hasSize(2)
    }

    @Test
    fun get_by_speciality() {
        val patient1 = Patient(1L, "Paciente1", 23, Gender.MALE)
        val patient2 = Patient(2L, "Paciente2", 23, Gender.FEMALE)
        daoPatient.insertPatient(patient1)
        daoPatient.insertPatient(patient2)

        val speciality1 = Speciality(1L, "Especialidade1")
        daoSpeciality.insertSpeciality(speciality1)

        val medic1 = Medic(1L, "Medico1", speciality1.id)
        val medic2 = Medic(2L, "Medico2", speciality1.id)
        daoMedic.insertMedic(medic1)
        daoMedic.insertMedic(medic2)

        val schedule1 = Schedule(1L, patient1.id, medic1.id)
        val schedule2 = Schedule(2L, patient2.id, medic2.id)
        dao.insertSchedule(schedule1)
        dao.insertSchedule(schedule2)

        val results = dao.getSchedulesBySpeciality(1L)
        assertThat(results).hasSize(2)
    }

    @Test
    fun get_by_patient_name() {
        val patient1 = Patient(1L, "Paciente1", 23, Gender.MALE)
        val patient2 = Patient(2L, "Paciente2", 23, Gender.FEMALE)
        daoPatient.insertPatient(patient1)
        daoPatient.insertPatient(patient2)

        val speciality1 = Speciality(1L, "Especialidade1")
        val speciality2 = Speciality(2L, "Especialidade2")
        daoSpeciality.insertSpeciality(speciality1)
        daoSpeciality.insertSpeciality(speciality2)

        val medic1 = Medic(1L, "Medico1", speciality1.id)
        val medic2 = Medic(2L, "Medico2", speciality2.id)
        daoMedic.insertMedic(medic1)
        daoMedic.insertMedic(medic2)

        val schedule1 = Schedule(1L, patient1.id, medic1.id)
        val schedule2 = Schedule(2L, patient2.id, medic2.id)
        dao.insertSchedule(schedule1)
        dao.insertSchedule(schedule2)

        val results = dao.getSchedulesByPatient("Paciente1")
        assertThat(results).hasSize(1)
    }

    @Test
    fun get_by_gender() {
        val patient1 = Patient(1L, "Paciente1", 23, Gender.MALE)
        val patient2 = Patient(2L, "Paciente2", 23, Gender.FEMALE)
        daoPatient.insertPatient(patient1)
        daoPatient.insertPatient(patient2)

        val speciality1 = Speciality(1L, "Especialidade1")
        val speciality2 = Speciality(2L, "Especialidade2")
        daoSpeciality.insertSpeciality(speciality1)
        daoSpeciality.insertSpeciality(speciality2)

        val medic1 = Medic(1L, "Medico1", speciality1.id)
        val medic2 = Medic(2L, "Medico2", speciality2.id)
        daoMedic.insertMedic(medic1)
        daoMedic.insertMedic(medic2)

        val schedule1 = Schedule(1L, patient1.id, medic1.id)
        val schedule2 = Schedule(2L, patient2.id, medic2.id)
        dao.insertSchedule(schedule1)
        dao.insertSchedule(schedule2)

        val results = dao.getSchedulesByGender(Gender.MALE)
        assertThat(results).hasSize(1)
    }

    @Test
    fun get_by_id() {
        val patient1 = Patient(1L, "Paciente1", 23, Gender.MALE)
        val patient2 = Patient(2L, "Paciente2", 23, Gender.FEMALE)
        daoPatient.insertPatient(patient1)
        daoPatient.insertPatient(patient2)

        val speciality1 = Speciality(1L, "Especialidade1")
        val speciality2 = Speciality(2L, "Especialidade2")
        daoSpeciality.insertSpeciality(speciality1)
        daoSpeciality.insertSpeciality(speciality2)

        val medic1 = Medic(1L, "Medico1", speciality1.id)
        val medic2 = Medic(2L, "Medico2", speciality2.id)
        daoMedic.insertMedic(medic1)
        daoMedic.insertMedic(medic2)

        val schedule1 = Schedule(1L, patient1.id, medic1.id)
        val schedule2 = Schedule(2L, patient2.id, medic2.id)
        dao.insertSchedule(schedule1)
        dao.insertSchedule(schedule2)

        val result = dao.getScheduleByID(1L)
        assertThat(result.medic.name).isEqualTo(medic1.name)
        assertThat(result.patient.name).isEqualTo(patient1.name)
    }

    @Test
    fun schedule_update() {
        val patient1 = Patient(1L, "Paciente1", 23, Gender.MALE)
        val patient2 = Patient(2L, "Paciente2", 23, Gender.FEMALE)
        daoPatient.insertPatient(patient1)
        daoPatient.insertPatient(patient2)

        val speciality1 = Speciality(1L, "Especialidade1")
        val speciality2 = Speciality(2L, "Especialidade2")
        daoSpeciality.insertSpeciality(speciality1)
        daoSpeciality.insertSpeciality(speciality2)

        val medic1 = Medic(1L, "Medico1", speciality1.id)
        val medic2 = Medic(2L, "Medico2", speciality2.id)
        daoMedic.insertMedic(medic1)
        daoMedic.insertMedic(medic2)

        val schedule1 = Schedule(1L, patient1.id, medic1.id)
        val schedule2 = Schedule(2L, patient2.id, medic2.id)
        dao.insertSchedule(schedule1)
        dao.insertSchedule(schedule2)

        val scheduleForUpdate = Schedule(schedule1.id, patient2.id, medic2.id)
        dao.updateSchedule(scheduleForUpdate)

        val result = dao.getScheduleByID(schedule1.id)
        assertThat(result.medic.name).isEqualTo(medic2.name)
        assertThat(result.patient.name).isEqualTo(patient2.name)
    }

    @Test
    fun schedule_delete() {
        val patient1 = Patient(1L, "Paciente1", 23, Gender.MALE)
        val patient2 = Patient(2L, "Paciente2", 23, Gender.FEMALE)
        daoPatient.insertPatient(patient1)
        daoPatient.insertPatient(patient2)

        val speciality1 = Speciality(1L, "Especialidade1")
        val speciality2 = Speciality(2L, "Especialidade2")
        daoSpeciality.insertSpeciality(speciality1)
        daoSpeciality.insertSpeciality(speciality2)

        val medic1 = Medic(1L, "Medico1", speciality1.id)
        val medic2 = Medic(2L, "Medico2", speciality2.id)
        daoMedic.insertMedic(medic1)
        daoMedic.insertMedic(medic2)

        val schedule1 = Schedule(1L, patient1.id, medic1.id)
        val schedule2 = Schedule(2L, patient2.id, medic2.id)
        dao.insertSchedule(schedule1)
        dao.insertSchedule(schedule2)

        dao.deleteSchedule(schedule1)

        val result = dao.getSchedules()
        assertThat(result).hasSize(1)
    }
}