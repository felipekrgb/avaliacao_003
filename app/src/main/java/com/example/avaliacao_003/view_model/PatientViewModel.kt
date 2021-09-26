package com.example.avaliacao_003.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.avaliacao_003.models.Patient
import com.example.avaliacao_003.repository.PatientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PatientViewModel @Inject constructor(
    private val repository: PatientRepository
) : ViewModel() {

    private val _patients = MutableLiveData<List<Patient>>()
    val patients: LiveData<List<Patient>> = _patients

    fun getPatients() {
        _patients.value = repository.getPatients()
    }

    fun addPatient(patient: Patient) {
        repository.addPatient(patient)
        getPatients()
    }

}