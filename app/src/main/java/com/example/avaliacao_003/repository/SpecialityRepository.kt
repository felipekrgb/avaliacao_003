package com.example.avaliacao_003.repository

import com.example.avaliacao_003.database.dao.SpecialityDAO
import com.example.avaliacao_003.models.Speciality
import javax.inject.Inject

class SpecialityRepository @Inject constructor(
    private val specialityDAO: SpecialityDAO
) {

    fun getSpecialities(): List<Speciality> {
        return specialityDAO.getSpecialities()
    }

    fun getSpeciality(id: Long): Speciality {
        return specialityDAO.getSpeciality(id)
    }

    fun addSpeciality(speciality: Speciality) {
        specialityDAO.insertSpeciality(speciality)
    }

    fun updateSpeciality(speciality: Speciality) {
        specialityDAO.updateSpeciality(speciality)
    }

    fun deleteSpeciality(speciality: Speciality) {
        specialityDAO.deleteSpeciality(speciality)
    }

}