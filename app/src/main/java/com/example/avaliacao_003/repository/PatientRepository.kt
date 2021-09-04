package com.example.avaliacao_003.repository

import com.example.avaliacao_003.database.dao.PatientDAO
import com.example.avaliacao_003.models.Patient
import javax.inject.Inject

class PatientRepository @Inject constructor(
    private val patientDAO: PatientDAO
) {

    fun getPatients(): List<Patient> {
        return patientDAO.getPatients()
    }

    fun getPatient(id: Long): Patient {
        return patientDAO.getPatient(id)
    }

    fun addPatient(patient: Patient) {
        patientDAO.insertPatient(patient)
    }

    fun updatePatient(patient: Patient) {
        patientDAO.updatePatient(patient)
    }

    fun deletePatient(patient: Patient) {
        patientDAO.deletePatient(patient)
    }

}