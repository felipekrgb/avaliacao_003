package com.example.avaliacao_003.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.avaliacao_003.models.Schedule
import com.example.avaliacao_003.models.ScheduleWithMedicAndPatient
import com.example.avaliacao_003.repository.ScheduleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScheduleDetailsViewModel @Inject constructor(
    val repository: ScheduleRepository
) : ViewModel() {

    private val _schedule = MutableLiveData<ScheduleWithMedicAndPatient>()
    val schedule: LiveData<ScheduleWithMedicAndPatient> = _schedule

    private val _didUpdateOrDelete = MutableLiveData(false)
    val didUpdateOrDelete: LiveData<Boolean> = _didUpdateOrDelete

    fun getSchedule(id: Long) {
        _schedule.value = repository.getScheduleByID(id)
    }

    fun updateSchedule(schedule: Schedule) {
        repository.updateSchedule(schedule)
        _didUpdateOrDelete.value = true
    }

    fun deleteSchedule(schedule: Schedule) {
        repository.deleteSchedule(schedule)
        _didUpdateOrDelete.value = true
    }

}