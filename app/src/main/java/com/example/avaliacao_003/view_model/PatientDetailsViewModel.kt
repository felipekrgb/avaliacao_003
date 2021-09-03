package com.example.avaliacao_003.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.avaliacao_003.models.Patient
import com.example.avaliacao_003.repository.PatientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PatientDetailsViewModel @Inject constructor(
    private val repository: PatientRepository
) : ViewModel() {

    private val _patient = MutableLiveData<Patient>()
    val patient: LiveData<Patient> = _patient

    private val _didUpdateOrDelete = MutableLiveData(false)
    val didUpdateOrDelete: LiveData<Boolean> = _didUpdateOrDelete

    fun getPatient(id: Long) {
        _patient.value = repository.getPatient(id)
    }

    fun updatePatient(patient: Patient) {
        repository.updatePatient(patient)
        _didUpdateOrDelete.value = true
    }

    fun deletePatient(patient: Patient) {
        repository.deletePatient(patient)
        _didUpdateOrDelete.value = true
    }

}