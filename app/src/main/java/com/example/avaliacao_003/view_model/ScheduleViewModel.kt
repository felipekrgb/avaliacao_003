package com.example.avaliacao_003.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.avaliacao_003.models.Gender
import com.example.avaliacao_003.models.Schedule
import com.example.avaliacao_003.models.ScheduleWithMedicAndPatient
import com.example.avaliacao_003.repository.ScheduleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val repository: ScheduleRepository
) : ViewModel() {

    private val _schedules = MutableLiveData<List<ScheduleWithMedicAndPatient>>()
    val schedules: LiveData<List<ScheduleWithMedicAndPatient>> = _schedules

    fun getSchedules() {
        _schedules.value = repository.getSchedules()
    }

    fun getSchedulesByGender(gender: Gender) {
        _schedules.value = repository.getSchedulesByGender(gender)
    }

    fun getSchedulesBySpeciality(specialityId: Long) {
        _schedules.value = repository.getSchedulesBySpeciality(specialityId)
    }

    fun addSchedule(schedule: Schedule) {
        repository.addSchedule(schedule)
        getSchedules()
    }

}