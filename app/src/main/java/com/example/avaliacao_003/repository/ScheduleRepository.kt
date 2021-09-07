package com.example.avaliacao_003.repository

import com.example.avaliacao_003.database.dao.ScheduleDAO
import com.example.avaliacao_003.models.Gender
import com.example.avaliacao_003.models.Schedule
import com.example.avaliacao_003.models.ScheduleWithMedicAndPatient
import javax.inject.Inject

class ScheduleRepository @Inject constructor(
    private val scheduleDAO: ScheduleDAO
) {

    fun getSchedules(): List<ScheduleWithMedicAndPatient> {
        return scheduleDAO.getSchedules()
    }

    fun getSchedulesByGender(gender: Gender): List<ScheduleWithMedicAndPatient> {
        return scheduleDAO.getSchedulesByGender(gender)
    }

    fun getSchedulesBySpeciality(specialityId: Long): List<ScheduleWithMedicAndPatient> {
        return scheduleDAO.getSchedulesBySpeciality(specialityId)
    }

    fun getScheduleByID(id: Long): ScheduleWithMedicAndPatient {
        return scheduleDAO.getScheduleByID(id)
    }

    fun addSchedule(schedule: Schedule) {
        scheduleDAO.insertSchedule(schedule)
    }

    fun updateSchedule(schedule: Schedule) {
        scheduleDAO.updateSchedule(schedule)
    }

    fun deleteSchedule(schedule: Schedule) {
        scheduleDAO.deleteSchedule(schedule)
    }

}