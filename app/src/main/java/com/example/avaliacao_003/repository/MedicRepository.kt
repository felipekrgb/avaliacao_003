package com.example.avaliacao_003.repository

import com.example.avaliacao_003.database.dao.MedicDAO
import com.example.avaliacao_003.models.Medic
import com.example.avaliacao_003.models.MedicWithSpeciality
import javax.inject.Inject

class MedicRepository @Inject constructor(
    private val medicDAO: MedicDAO
) {

    fun getMedics(): List<MedicWithSpeciality> {
        return medicDAO.getMedics()
    }

    fun getMedic(id: Long): MedicWithSpeciality {
        return medicDAO.getMedic(id)
    }

    fun addMedic(medic: Medic) {
        medicDAO.insertMedic(medic)
    }

    fun updateMedic(medic: Medic) {
        medicDAO.updateMedic(medic)
    }

    fun deleteMedic(medic: Medic) {
        medicDAO.deleteMedic(medic)
    }

}