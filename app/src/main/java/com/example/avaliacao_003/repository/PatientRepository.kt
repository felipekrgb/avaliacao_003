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

    fun addPatient(patient: Patient) {
        return patientDAO.insertPatient(patient)
    }

}